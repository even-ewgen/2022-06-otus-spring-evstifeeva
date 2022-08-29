package ru.otus.exam.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Сервис запуска тестирования")
class ExamServiceTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private ExamService examineService;

    private MessageSource messageSourceMock;

    private ControlService controlServiceMock;
    private ReaderService readerService;

    @BeforeEach
    private void setUp() {
        System.setOut(new PrintStream(outContent));

        readerService = mock(ReaderService.class);

        controlServiceMock = mock(ControlService.class);

        messageSourceMock = mock(MessageSource.class);
        when(messageSourceMock.getMessage("exam.congrats.string", null, Locale.forLanguageTag("en-EN")))
            .thenReturn("Congrats, you pass!");
        when(messageSourceMock.getMessage("exam.fail.string", null, Locale.forLanguageTag("en-EN")))
            .thenReturn("You need to study more.");
        when(messageSourceMock.getMessage("exam.score.string", null, Locale.forLanguageTag("en-EN")))
            .thenReturn("1");

        PrinterService printerService = new PrinterServiceImpl(messageSourceMock, "en-EN");

        examineService = new ExamServiceImpl(controlServiceMock, readerService, printerService);
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