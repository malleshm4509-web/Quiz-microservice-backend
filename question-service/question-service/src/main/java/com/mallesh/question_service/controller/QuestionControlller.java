package com.mallesh.question_service.controller;


import com.mallesh.question_service.model.QuestionWrapper;
import com.mallesh.question_service.model.Questions;
import com.mallesh.question_service.model.Response;
import com.mallesh.question_service.service.QuestionService;
import org.springframework.core.env.Environment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionControlller {

    @Autowired
    QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestion")
    public ResponseEntity<List<Questions>> getAllQuestions(){
        return questionService.getAllQuestion();
    }


    @GetMapping("/category/{category}")
    public ResponseEntity<List<Questions>> getQuestionByCategory(@PathVariable String category){

        return questionService.getQuestionByCategory(category);

    }
    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Questions questions){
       return questionService.addQuestion(questions);
    }

    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @RequestParam String categoryName, @RequestParam Integer numQuestions){
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }


    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds){
    System.out.println(environment.getProperty("local.server.port"));

        return questionService.getQuestionsFromId(questionIds);
    }

    @PostMapping("/getScore")
    public  ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){

        return questionService.getScore(responses);

    }



}
