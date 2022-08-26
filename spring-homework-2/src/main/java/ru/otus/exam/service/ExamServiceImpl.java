package ru.otus.exam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.exam.domain.StudentCard;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ControlService controlService;
    private final ReaderService readerService;

    @Override
    public void exam() {
        StudentCard studentCard = new StudentCard();
        getStudentData(studentCard);

        if (controlService.control(studentCard)) {
            System.out.print("Congrats, you pass! ");
        } else
            System.out.print("You need to study more. ");

        System.out.printf("Your score is %d%n", studentCard.getScore());
    }

    private void getStudentData(StudentCard studentCard) {
        try {
            System.out.println("Hello! Please, tape your first name: ");
            studentCard.setFirstName(readerService.read());
            System.out.println("And your last name: ");
            studentCard.setLastName(readerService.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
