package ru.otus.exam.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.exam.dao.QuestionDao;
import ru.otus.exam.dao.QuestionDaoImpl;
import ru.otus.exam.domain.StudentCard;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис сбора ответов студента")
class ControlServiceTest {

    private QuestionDao dao;
    private ReaderService readerServiceMock;
    private ControlService controlService;

    @BeforeEach
    private void setUp() {
        dao = new QuestionDaoImpl("/questions.csv");

        readerServiceMock = mock(ReaderService.class);

        controlService = new ControlServiceImpl(dao, readerServiceMock, 75d);
    }

    @Test
    @DisplayName("Проверка начисляемого количества баллов при правильных ответах")
    void givingRightAnswers() throws IOException {
        StudentCard student = new StudentCard("Ivan","Ivanov",0,false);
        when(readerServiceMock.read()).thenReturn("3","2","1","3","1");

        controlService.control(student);

        assertThat(student.getScore()).isEqualTo(5);
    }

    @Test
    @DisplayName("Проверка начисляемого количества баллов при неправильных ответах")
    void givingWrongAnswers() throws IOException {
        StudentCard student = new StudentCard("Ivan","Ivanov",0,false);
        when(readerServiceMock.read()).thenReturn("1","1","3","3","1");

        controlService.control(student);

        assertThat(student.getScore()).isEqualTo(2);
    }

    @Test
    @DisplayName("Проверка начисляемого количества баллов при неправильных ответах")
    void givingWrongAnswerAndGettingSecondChance() throws IOException {
        StudentCard student = new StudentCard("Ivan","Ivanov",0,false);
        when(readerServiceMock.read()).thenReturn("a","3", "2","3","3","1");

        controlService.control(student);

        assertThat(student.getScore()).isEqualTo(4);
    }

    @Test
    @DisplayName("Проверка начисляемого количества баллов при неправильных ответах")
    void givingWrongAnswerAndNotGettingSecondChance() throws IOException {
        StudentCard student = new StudentCard("Ivan","Ivanov",0,false);
        when(readerServiceMock.read()).thenReturn("a","b", "2","3","3","1");

        assertThatThrownBy(() -> controlService.control(student))
            .isInstanceOf(NumberFormatException.class)
            .hasMessage("For input string: \"b\"");
    }
}