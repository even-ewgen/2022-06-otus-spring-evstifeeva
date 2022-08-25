package ru.otus.exam.service;

import org.springframework.stereotype.Service;
import ru.otus.exam.domain.StudentCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ExamServiceImpl implements ExamService {

    private final ControlService controlService;

    public ExamServiceImpl(ControlService controlService) {
        this.controlService = controlService;
    }

    @Override
    public void exam() {
        StudentCard studentCard = new StudentCard();
        getStudentData(studentCard);

        controlService.control(studentCard);
    }

    private void getStudentData(StudentCard studentCard) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Hello! Please, tape your first name: ");
            studentCard.setFirstName(bufferedReader.readLine());
            System.out.println("And your last name: ");
            studentCard.setLastName(bufferedReader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
