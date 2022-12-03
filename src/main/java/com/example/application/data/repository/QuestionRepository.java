package com.example.application.data.repository;

import com.example.application.data.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("select question from Question question join question.tags tag")
    List<Question> searchByTag(@Param("searchTerm") String stringFilter);
}
