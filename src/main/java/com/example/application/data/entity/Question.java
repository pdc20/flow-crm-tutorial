package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Question extends AbstractEntity {
    @NotNull
    @NotBlank
    @NotEmpty
    private String content;

    @ManyToOne
    @JoinColumn(name = "articleSource_id")
    private ArticleSource articleSource;

    @ManyToMany
    @JoinTable(
            name = "question_tag", // This is the name of the intermediate table.
            joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
