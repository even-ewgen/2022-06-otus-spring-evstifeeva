package ru.otus.exam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;
import ru.otus.exam.domain.StudentCard;

import java.io.IOException;
import java.util.List;

@Service
public class ControlServiceImpl implements ControlService {

    private final QuestionDao dao;
    private final Double passPercent;
    private final ReaderService readerService;

    public ControlServiceImpl(QuestionDao dao, ReaderService readerService, @Value("${pass.percent}") Double passPercent) {
        this.dao = dao;
        this.passPercent = passPercent;
        this.readerService = readerService;
    }

    @Override
    public boolean control(StudentCard studentCard) {
        List<Question> questions = dao.findAllQuestions();
        for (Question question : questions) {
            System.out.println(question.getQuestion());
            List<Answer> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                Answer answer = answers.get(i);
                System.out.printf("\t %d - %s%n", i+1, answer.getAnswer());
            }
            int answerNumber = getStudentAnswer();
            if (answerNumber > 0 && answerNumber == question.getRightAnswer())
                studentCard.setScore(studentCard.getScore()+1);
        }
        if (studentCard.getScore()*100d/questions.size() >= passPercent)
            studentCard.setPass(true);

        return studentCard.isPass();
    }

    private int getStudentAnswer() {
        System.out.println("Please, chose your answer: ");
        try {
            int answer = Integer.parseInt(readerService.read());
            System.out.printf("Your answer is %d%n", answer);
            return answer;
        } catch (NumberFormatException e) {
            System.out.println("You should use only numbers. Please answer again: ");
            try {
                int answer = Integer.parseInt(readerService.read());
                System.out.printf("Your answer is %d%n", answer);
                return answer;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
