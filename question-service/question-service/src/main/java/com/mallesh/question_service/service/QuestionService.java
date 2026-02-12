package com.mallesh.question_service.service;

import com.mallesh.question_service.Dao.QuestionDao;
import com.mallesh.question_service.model.QuestionWrapper;
import com.mallesh.question_service.model.Questions;
import com.mallesh.question_service.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDao questionDao;




    public ResponseEntity<List<Questions>> getAllQuestion() {
        try {
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<List<Questions>> getQuestionByCategory(String category) {
       try{
           return new ResponseEntity<>(questionDao.findByCategory(category),HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

       }
    }

    public ResponseEntity<String> addQuestion(Questions questions) {

        questionDao.save(questions);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {

        List<Integer> questions = questionDao.findRandomQuestionsByCategory(categoryName,numQuestions);
        return new ResponseEntity<>(questions,HttpStatus.OK);

    }

    public  ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<QuestionWrapper> wrappers = new ArrayList<>();
        List<Questions> questions= new ArrayList<>();
        for (Integer id : questionIds) {
            Questions question = questionDao.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found"));
            questions.add(question);
        }

        for(Questions question : questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());
            wrappers.add(wrapper);

        }

        return new ResponseEntity<>(wrappers, HttpStatus.OK);


    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;

        for (Response r : responses) {
            Questions questions = questionDao.findById(r.getId()).get();
            if (r.getResponse().equals(questions.getRight_answer())) {
                right++;
            }
        }

        return new ResponseEntity<>(right, HttpStatus.OK);

    }
}
