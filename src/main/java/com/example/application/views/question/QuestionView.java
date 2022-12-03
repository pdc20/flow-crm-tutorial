package com.example.application.views.question;

import com.example.application.data.entity.Question;
import com.example.application.data.entity.Tag;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.tag.TagForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.security.PermitAll;

@Component
@Scope("prototype")
@Route(value="question", layout = MainLayout.class)
@PageTitle("Question | Vaadin CRM")
@PermitAll
public class QuestionView extends VerticalLayout {
    Grid<Question> grid = new Grid<>(Question.class);
    TextField filterText = new TextField();

    QuestionForm form;
    CrmService service;

    public QuestionView(CrmService service) {

        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new QuestionForm(service.findAllTags(null));
        form.setWidth("25em");
        form.addListener(QuestionForm.SaveEvent.class, this::saveQuestion);
        form.addListener(QuestionForm.DeleteEvent.class, this::deleteQuestion);
        form.addListener(QuestionForm.CloseEvent.class, e -> closeEditor());

        FlexLayout content = new FlexLayout(grid, form);
        content.setFlexGrow(2, grid);
        content.setFlexGrow(1, form);
        content.setFlexShrink(0, form);
        content.addClassNames("content", "gap-m");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
        grid.asSingleSelect().addValueChangeListener(event ->
                editQuestion(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("data-grid");
        grid.setSizeFull();
        grid.setColumns("id", "content");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by tag...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add record");
        addContactButton.addClickListener(click -> addQuestion());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveQuestion(QuestionForm.SaveEvent event) {
        service.saveQuestion(event.getQuestion());
        updateList();
        closeEditor();
    }

    private void deleteQuestion(QuestionForm.DeleteEvent event) {
        service.deleteQuestion(event.getQuestion());
        updateList();
        closeEditor();
    }

    public void editQuestion(Question question) {
        if (question == null) {
            closeEditor();
        } else {
            form.setQuestion(question);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addQuestion() {
        grid.asSingleSelect().clear();
        editQuestion(new Question());
    }

    private void closeEditor() {
        form.setQuestion(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllQuestions(filterText.getValue()));
    }
}
