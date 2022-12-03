package com.example.application.data.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final SourceRepository sourceRepository;
    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;

    public CrmService(SourceRepository sourceRepository,
                      TagRepository tagRepository,
                      QuestionRepository questionRepository) {
        this.sourceRepository = sourceRepository;
        this.tagRepository = tagRepository;
        this.questionRepository = questionRepository;
    }

    public List<Tag> findAllTags(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return tagRepository.findAll();
        else
            return tagRepository.search(stringFilter);
    }

    public void saveTag(Tag tag) {
        if (tag == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        tagRepository.save(tag);
    }

    public List<Question> findAllQuestions(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return questionRepository.findAll();
        else
            return questionRepository.searchByTag(stringFilter);
    }

    public List<ArticleSource> findAllSources(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty())
            return sourceRepository.findAll();
        else
            return sourceRepository.search(stringFilter);
    }

    public void saveArticleSource(ArticleSource articleSource) {
        if (articleSource == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        sourceRepository.save(articleSource);
    }

    public void deleteArticleSource(ArticleSource articleSource) {
        sourceRepository.delete(articleSource);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    public void saveQuestion(Question question) {
        if (question == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        questionRepository.save(question);
    }

    public void deleteQuestion(Question question) {
        questionRepository.delete(question);
    }
}
