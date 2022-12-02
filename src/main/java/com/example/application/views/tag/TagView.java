package com.example.application.views.tag;

import com.example.application.data.entity.Tag;
import com.example.application.data.entity.Tag;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
import com.example.application.views.tag.TagForm;
import com.vaadin.flow.component.button.Button;
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
@Route(value="tag", layout = MainLayout.class)
@PageTitle("Tags | Vaadin CRM")
@PermitAll
public class TagView extends VerticalLayout {
    Grid<Tag> grid = new Grid<>(Tag.class);
    TextField filterText = new TextField();
    TagForm form;
    CrmService service;

    public TagView(CrmService service) {

        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new TagForm();
        form.setWidth("25em");
        form.addListener(TagForm.SaveEvent.class, this::saveTag);
        form.addListener(TagForm.DeleteEvent.class, this::deleteTag);
        form.addListener(TagForm.CloseEvent.class, e -> closeEditor());

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
                editTag(event.getValue()));
    }

    private void configureGrid() {
        grid.addClassNames("data-grid");
        grid.setSizeFull();
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addContactButton = new Button("Add record");
        addContactButton.addClickListener(click -> addTag());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveTag(TagForm.SaveEvent event) {
        service.saveTag(event.getTag());
        updateList();
        closeEditor();
    }

    private void deleteTag(TagForm.DeleteEvent event) {
        service.deleteTag(event.getTag());
        updateList();
        closeEditor();
    }

    public void editTag(Tag tag) {
        if (tag == null) {
            closeEditor();
        } else {
            form.setTag(tag);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addTag() {
        grid.asSingleSelect().clear();
        editTag(new Tag());
    }

    private void closeEditor() {
        form.setTag(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllTags(filterText.getValue()));
    }


}