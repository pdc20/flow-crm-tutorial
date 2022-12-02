package com.example.application.views.articleSource;

import com.example.application.data.entity.ArticleSource;
import com.example.application.data.service.CrmService;
import com.example.application.views.MainLayout;
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
@Route(value="", layout = MainLayout.class)
@PageTitle("Contacts | Vaadin CRM")
@PermitAll
public class ArticleSourceView extends VerticalLayout {
    Grid<ArticleSource> grid = new Grid<>(ArticleSource.class);
    TextField filterText = new TextField();
    ArticleSourceForm form;
    CrmService service;

    public ArticleSourceView(CrmService service) {

        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new ArticleSourceForm();
        form.setWidth("25em");
        form.addListener(ArticleSourceForm.SaveEvent.class, this::saveArticleSource);
        form.addListener(ArticleSourceForm.DeleteEvent.class, this::deleteArticleSource);
        form.addListener(ArticleSourceForm.CloseEvent.class, e -> closeEditor());

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
            editArticleSource(event.getValue()));
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
        addContactButton.addClickListener(click -> addArticleSource());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addContactButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void saveArticleSource(ArticleSourceForm.SaveEvent event) {
        service.saveArticleSource(event.getArticleSource());
        updateList();
        closeEditor();
    }

    private void deleteArticleSource(ArticleSourceForm.DeleteEvent event) {
        service.deleteArticleSource(event.getArticleSource());
        updateList();
        closeEditor();
    }

    public void editArticleSource(ArticleSource articleSource) {
        if (articleSource == null) {
            closeEditor();
        } else {
            form.setArticleSource(articleSource);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    void addArticleSource() {
        grid.asSingleSelect().clear();
        editArticleSource(new ArticleSource());
    }

    private void closeEditor() {
        form.setArticleSource(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void updateList() {
        grid.setItems(service.findAllSources(filterText.getValue()));
    }


}
