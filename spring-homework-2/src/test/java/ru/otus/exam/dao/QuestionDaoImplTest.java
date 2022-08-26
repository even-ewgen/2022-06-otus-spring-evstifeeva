package ru.otus.exam.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Класс QuestionDaoImpl")
class QuestionDaoImplTest {

    private QuestionDao questionDao;

    @BeforeEach
    protected void setup() {
        questionDao = new QuestionDaoImpl("/questions.csv");
    }

    @Test
    @DisplayName("Файл с вопросами и ответами не пустой, содержит текст вопроса, ответов и метку правильного варианта")
    void checkQuestionsAndAnswers() {
        List<Question> questions = questionDao.findAllQuestions();
        for (Question question : questions) {
            List<Answer> answers = question.getAnswers();

            assertThat(question.getQuestion()).isNotEmpty();

            assertThat(answers).isNotEmpty().hasSizeGreaterThan(1);

            for (int j = 0; j < answers.size(); j++) {
                Answer answer = answers.get(j);
                assertThat(answer.getAnswer()).isNotEmpty();

                if (answer.isRight()) {
                    assertThat(question.getRightAnswer()).isEqualTo(j + 1);
                }
            }
        }
    }
}