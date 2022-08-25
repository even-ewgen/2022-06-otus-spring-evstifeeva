package ru.otus.exam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;
import ru.otus.exam.domain.StudentCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ControlServiceImpl implements ControlService {

    private final QuestionDao dao;
    private final Integer passPercent;

    public ControlServiceImpl(QuestionDao dao, @Value("${pass.percent}") Integer passPercent) {
        this.dao = dao;
        this.passPercent = passPercent;
    }

    @Override
    public void control(StudentCard studentCard) {
        List<Question> questions = dao.findAllQuestions();
        for (Question question : questions) {
            System.out.println(question.getQuestion() + " " + question.getRightAnswer());
            List<Answer> answers = question.getAnswers();
            for (int i = 0; i < answers.size(); i++) {
                Answer answer = answers.get(i);
                System.out.printf("\t %d - %s%n", i+1, answer.getAnswer());
            }
            int answerNumber = getStudentAnswer();
            if (answerNumber > 0 && answerNumber == question.getRightAnswer())
                studentCard.setScore(studentCard.getScore()+1);
        }
        if (studentCard.getScore()*100/questions.size() >= passPercent) {
            System.out.printf("Congrats, you pass! Your score is %d%n", studentCard.getScore());
        } else
            System.out.printf("You need to study more. Your score is %d%n", studentCard.getScore());
    }

    private int getStudentAnswer() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Please, chose your answer: ");
        try {
            int answer = Integer.parseInt(bufferedReader.readLine());
            System.out.printf("Your answer is %d%n", answer);
            return answer;
        } catch (NumberFormatException e) {
            System.out.println("You should use only numbers. Please answer again: ");
            try {
                int answer = Integer.parseInt(bufferedReader.readLine());
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
