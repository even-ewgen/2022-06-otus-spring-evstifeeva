package ru.otus.exam.service;

import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    public List<Question> getAllQuestions() {
        return dao.findAllQuestions();
    }
}
