package ru.otus.exam.dao;

import ru.otus.exam.domain.Question;

import java.util.List;

public interface QuestionDao {

    List<Question> findAllQuestions();
}
