package com.example.application.views.question;

import com.example.application.data.entity.ArticleSource;
import com.example.application.data.entity.Question;
import com.example.application.data.entity.Tag;
import com.example.application.views.tag.TagForm;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class QuestionForm extends FormLayout {
    private Question question;

    //MemoryBuffer buffer = new MemoryBuffer();
    //Upload upload_content = new Upload(buffer);
    TextArea content = new TextArea("Content");
    MultiSelectComboBox<Tag> tags = new MultiSelectComboBox<>("Tags");

    ComboBox<ArticleSource> articleSource = new ComboBox<>("Source");

    Binder<Question> binder = new BeanValidationBinder<>(Question.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public QuestionForm(List<Tag> tags, List<ArticleSource> sources) {
        addClassName("question-form");

        this.tags.setItems(tags);
        this.tags.setItemLabelGenerator(Tag::getName);

        this.articleSource.setItems(sources);

        binder.bindInstanceFields(this);
        content.setReadOnly(true);

        /*upload_content.addSucceededListener(event -> {
            InputStream inputStream = buffer.getInputStream();

            try {
                content.setValue(IOUtils.toString(inputStream, StandardCharsets.UTF_8));
            } catch (IOException e) {
                content.setValue("");
            }
        });*/

        add(//upload_content,
                content,
                this.tags,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new QuestionForm.DeleteEvent(this, question)));
        close.addClickListener(event -> fireEvent(new QuestionForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    public void setQuestion(Question question) {
        this.question = question;
        binder.readBean(question);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(question);
            fireEvent(new QuestionForm.SaveEvent(this, question));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    // Events
    public static abstract class QuestionFormEvent extends ComponentEvent<QuestionForm> {
        private Question question;

        protected QuestionFormEvent(QuestionForm articleSourceForm, Question question) {
            super(articleSourceForm, false);
            this.question = question;
        }

        public Question getQuestion() {
            return question;
        }
    }

    public static class SaveEvent extends QuestionForm.QuestionFormEvent {
        SaveEvent(QuestionForm questionForm, Question question) {
            super(questionForm, question);
        }
    }

    public static class DeleteEvent extends QuestionForm.QuestionFormEvent {
        DeleteEvent(QuestionForm questionForm, Question question) {
            super(questionForm, question);
        }

    }

    public static class CloseEvent extends QuestionForm.QuestionFormEvent {
        CloseEvent(QuestionForm questionForm) {
            super(questionForm, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
