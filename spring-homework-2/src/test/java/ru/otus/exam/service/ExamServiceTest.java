package ru.otus.exam.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис запуска тестирования")
class ExamServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ExamService examineService;

    private ControlService controlServiceMock;
    private ReaderService readerService;

    @BeforeEach
    private void setUp() {
        System.setOut(new PrintStream(outContent));

        readerService = mock(ReaderService.class);

        controlServiceMock = mock(ControlService.class);

        examineService = new ExamServiceImpl(controlServiceMock, readerService);
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    @DisplayName("Сообщение верное при прохождении порога верных ответов")
    void examPass() throws IOException {
        when(readerService.read()).thenReturn("Ivan","Ivanov");
        when(controlServiceMock.control(any())).thenReturn(true);

        examineService.exam();

        assertTrue(outContent.toString().contains("Congrats, you pass!"));
    }

    @Test
    @DisplayName("Сообщение верное при недостижении порога верных ответов")
    void examNotPass() throws IOException {
        when(readerService.read()).thenReturn("Ivan","Ivanov");
        when(controlServiceMock.control(any())).thenReturn(false);

        examineService.exam();

        assertTrue(outContent.toString().contains("You need to study more."));
    }
}