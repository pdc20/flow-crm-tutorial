package com.example.application.views.tag;

import com.example.application.data.entity.Tag;
import com.example.application.data.entity.Tag;
import com.example.application.views.tag.TagForm;
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

public class TagForm extends FormLayout {

    private Tag tag;

    TextField name = new TextField("Tag name");
    TextField description = new TextField("Tag description");

    Binder<Tag> binder = new BeanValidationBinder<>(Tag.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public TagForm() {
        addClassName("source-form");
        binder.bindInstanceFields(this);

        add(name,
                description,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new TagForm.DeleteEvent(this, tag)));
        close.addClickListener(event -> fireEvent(new TagForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setTag(Tag tag) {
        this.tag = tag;
        binder.readBean(tag);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(tag);
            fireEvent(new TagForm.SaveEvent(this, tag));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class SourceFormEvent extends ComponentEvent<TagForm> {
        private Tag tag;

        protected SourceFormEvent(TagForm articleSourceForm, Tag tag) {
            super(articleSourceForm, false);
            this.tag = tag;
        }

        public Tag getTag() {
            return tag;
        }
    }

    public static class SaveEvent extends TagForm.SourceFormEvent {
        SaveEvent(TagForm articleSourceForm, Tag tag) {
            super(articleSourceForm, tag);
        }
    }

    public static class DeleteEvent extends TagForm.SourceFormEvent {
        DeleteEvent(TagForm articleSourceForm, Tag tag) {
            super(articleSourceForm, tag);
        }

    }

    public static class CloseEvent extends TagForm.SourceFormEvent {
        CloseEvent(TagForm articleSourceForm) {
            super(articleSourceForm, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
