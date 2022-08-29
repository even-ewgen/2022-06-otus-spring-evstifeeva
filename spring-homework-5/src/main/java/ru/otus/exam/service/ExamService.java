package ru.otus.exam.service;

import ru.otus.exam.domain.StudentCard;

public interface ExamService {

    void exam();

    StudentCard getStudentCard();
}
