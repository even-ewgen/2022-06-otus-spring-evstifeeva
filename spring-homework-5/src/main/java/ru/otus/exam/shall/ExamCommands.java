package ru.otus.exam.shall;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.exam.domain.StudentCard;
import ru.otus.exam.service.ExamService;
import ru.otus.exam.service.PrinterService;

@ShellComponent
@RequiredArgsConstructor
public class ExamCommands {

    private final ExamService examService;
    private final PrinterService printerService;

    @ShellMethod(value = "Start exam", key = {"s", "start"})
    public void startExam() {
        examService.exam();
    }

    @ShellMethod(value = "Get student info", key = {"std", "student"})
    public void getStudentInfo() {
        StudentCard studentCard = examService.getStudentCard();

        if (studentCard == null) {
            printerService.print("student.no.data.string");
            return;
        }

        printerService.print("student.firstname.string", studentCard.getFirstName());
        printerService.print("student.lastname.string", studentCard.getLastName());
        printerService.print("student.score.string", studentCard.getScore());

        if (studentCard.isPass())
            printerService.print("student.passed.string");
        else
            printerService.print("student.not.passed.string");
    }
}
