package com.example.application.data.service;

import com.example.application.data.entity.*;
import com.example.application.data.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final ContactRepository contactRepository;
    private final CompanyRepository companyRepository;
    private final StatusRepository statusRepository;
    private final SourceRepository sourceRepository;
    private final TagRepository tagRepository;

    public CrmService(ContactRepository contactRepository,
                      CompanyRepository companyRepository,
                      StatusRepository statusRepository,
                      SourceRepository sourceRepository,
                      TagRepository tagRepository) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
        this.statusRepository = statusRepository;
        this.sourceRepository = sourceRepository;
        this.tagRepository = tagRepository;
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

    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
    }

    public void deleteContact(Contact contact) {
        contactRepository.delete(contact);
    }

    public void saveContact(Contact contact) {
        if (contact == null) {
            System.err.println("Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(contact);
    }

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Status> findAllStatuses(){
        return statusRepository.findAll();
    }

    public void deleteArticleSource(ArticleSource articleSource) {
        sourceRepository.delete(articleSource);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }
}
