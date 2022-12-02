package com.example.application.data.repository;

import com.example.application.data.entity.ArticleSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SourceRepository extends JpaRepository<ArticleSource, Integer> {
    @Query("select c from ArticleSource c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<ArticleSource> search(@Param("searchTerm") String stringFilter);
}
