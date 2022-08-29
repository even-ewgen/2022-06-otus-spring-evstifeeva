package ru.otus.exam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.exam.domain.StudentCard;

import java.io.IOException;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ControlService controlService;
    private final ReaderService readerService;
    private final PrinterService printerService;

    @Override
    public void exam() {
        StudentCard studentCard = new StudentCard();
        getStudentData(studentCard);

        if (controlService.control(studentCard)) {
            printerService.print("exam.congrats.string");
        } else
            printerService.print("exam.fail.string");

        printerService.print("exam.score.string", studentCard.getScore());
    }

    private void getStudentData(StudentCard studentCard) {
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
