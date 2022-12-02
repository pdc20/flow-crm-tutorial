package com.example.application.views.articleSource;

import com.example.application.data.entity.ArticleSource;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class ArticleSourceForm extends FormLayout {

  private ArticleSource articleSource;

  TextField name = new TextField("Source name");
  TextField url = new TextField("Source URL");
  TextField isbn = new TextField("ISBN (if provided)");

  Binder<ArticleSource> binder = new BeanValidationBinder<>(ArticleSource.class);

  Button save = new Button("Save");
  Button delete = new Button("Delete");
  Button close = new Button("Cancel");

  public ArticleSourceForm() {
    addClassName("source-form");
    binder.bindInstanceFields(this);

    add(name,
        url,
        isbn,
        createButtonsLayout()); 
  }

  private HorizontalLayout createButtonsLayout() {
    save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
    close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

    save.addClickShortcut(Key.ENTER);
    close.addClickShortcut(Key.ESCAPE);

    save.addClickListener(event -> validateAndSave());
    delete.addClickListener(event -> fireEvent(new DeleteEvent(this, articleSource)));
    close.addClickListener(event -> fireEvent(new CloseEvent(this)));


    binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

    return new HorizontalLayout(save, delete, close); 
  }

  public void setArticleSource(ArticleSource articleSource) {
    this.articleSource = articleSource;
    binder.readBean(articleSource);
  }

  private void validateAndSave() {
    try {
      binder.writeBean(articleSource);
      fireEvent(new SaveEvent(this, articleSource));
    } catch (ValidationException e) {
      e.printStackTrace();
    }
  }

  // Events
  public static abstract class SourceFormEvent extends ComponentEvent<ArticleSourceForm> {
    private ArticleSource articleSource;

    protected SourceFormEvent(ArticleSourceForm articleSourceForm, ArticleSource articleSource) {
      super(articleSourceForm, false);
      this.articleSource = articleSource;
    }

    public ArticleSource getArticleSource() {
      return articleSource;
    }
  }

  public static class SaveEvent extends SourceFormEvent {
    SaveEvent(ArticleSourceForm articleSourceForm, ArticleSource articleSource) {
      super(articleSourceForm, articleSource);
    }
  }

  public static class DeleteEvent extends SourceFormEvent {
    DeleteEvent(ArticleSourceForm articleSourceForm, ArticleSource articleSource) {
      super(articleSourceForm, articleSource);
    }

  }

  public static class CloseEvent extends SourceFormEvent {
    CloseEvent(ArticleSourceForm articleSourceForm) {
      super(articleSourceForm, null);
    }
  }

  public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                ComponentEventListener<T> listener) {
    return getEventBus().addListener(eventType, listener);
  }
}