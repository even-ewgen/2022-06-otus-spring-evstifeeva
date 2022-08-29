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
    private final PrinterService printerService;

    private StudentCard studentCard;

    @Override
    public void exam() {
        studentCard = new StudentCard();
        getStudentData();

        if (controlService.control(studentCard)) {
            printerService.print("exam.congrats.string");
        } else
            printerService.print("exam.fail.string");

        printerService.print("exam.score.string", studentCard.getScore());
    }

    public StudentCard getStudentCard() {
        return studentCard;
    }

    private void getStudentData() {
        try {
            printerService.print("exam.firstname.string");
            studentCard.setFirstName(readerService.read());
            printerService.print("exam.lastname.string");
            studentCard.setLastName(readerService.read());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
