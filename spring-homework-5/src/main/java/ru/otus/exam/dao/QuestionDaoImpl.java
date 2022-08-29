package ru.otus.exam.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class QuestionDaoImpl implements QuestionDao {

    private String fileName;

    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(@Value("${file.name}")String fileName, @Value("${exam.locale}") String locale) {
        this.fileName = "/" + fileName + "_" + locale + ".csv";
        if (this.getClass().getResource(this.fileName) == null) {
            this.fileName = "/" + fileName + ".csv";
        }
        fillQuestions();
    }

    @Override
    public List<Question> findAllQuestions() {
        return questions;
    }

    private void fillQuestions() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                List<Answer> answers = new ArrayList<>();
                int rightNumber = Integer.parseInt(values[values.length-1]);
                String[] stringAnswers = Arrays.copyOfRange(values,1,values.length-1);
                for (String value : stringAnswers) {
                    rightNumber --;
                    answers.add(new Answer(value, rightNumber == 0));
                }
                questions.add(new Question(values[0],answers,rightNumber+stringAnswers.length));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
