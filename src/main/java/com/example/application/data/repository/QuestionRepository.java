package com.example.application.data.repository;

import com.example.application.data.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select c from question c\n" +
            "inner join question_tag on question.id = question_tag.question_id\n" +
            "inner join tag on question_tag.tag_id = tag.id\n" +
            "where lower(tag.\"name\") like lower(concat('%', :searchTerm, '%'))")
    List<Question> searchByTag(@Param("searchTerm") String stringFilter);
}
