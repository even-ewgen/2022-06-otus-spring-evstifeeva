package ru.otus.exam.dao;

import ru.otus.exam.domain.Answer;
import ru.otus.exam.domain.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionDaoImpl implements QuestionDao {

    private final String fileName;

    private final List<Question> questions = new ArrayList<>();

    public QuestionDaoImpl(String fileName) {
        this.fileName = fileName;
        if (this.questions.size() == 0)
            fillQuestions();
    }

    public List<Question> findAllQuestions() {
        return questions;
    }

    public void fillQuestions() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                List<Answer> answers = new ArrayList<>();
                for (String value : Arrays.asList(Arrays.copyOfRange(values,1,values.length)))
                    answers.add(new Answer(value));
                questions.add(new Question(values[0],answers));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
