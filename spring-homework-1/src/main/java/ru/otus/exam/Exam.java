package ru.otus.exam;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;
import ru.otus.exam.service.QuestionService;

public class Exam {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService service = context.getBean(QuestionService.class);
        for (Question question : service.getAllQuestions()) {
            System.out.println(question.getQuestion());
            for (Answer answer : question.getAnswers()) {
                System.out.println("\t - " + answer.getAnswer());
            }
        }
    }
}
