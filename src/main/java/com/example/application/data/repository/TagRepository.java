package com.example.application.data.repository;

import com.example.application.data.entity.ArticleSource;
import com.example.application.data.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    @Query("select c from Tag c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%'))")
    List<Tag> search(@Param("searchTerm") String stringFilter);
}
