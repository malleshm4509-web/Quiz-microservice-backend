package com.mallesh.question_service.Dao;

import com.mallesh.question_service.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Questions,Integer> {

    List<Questions> findByCategory(String category);

    @Query(
            value = "SELECT q.id FROM questions q WHERE q.category = :category ORDER BY RAND() LIMIT :numQ",
            nativeQuery = true
    )
    List<Integer> findRandomQuestionsByCategory(
         String category, int numQ);

}


