package ru.otus.exam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PrinterServiceImpl implements PrinterService {

    private final MessageSource messageSource;

    private final String locale;

    public PrinterServiceImpl(MessageSource messageSource, @Value("${exam.locale}") String locale) {
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public void print(String code) {
        System.out.println(messageSource.getMessage(code, null, Locale.forLanguageTag(locale)));
    }

    @Override
    public void print(String code, Object... args) {
        System.out.printf(messageSource.getMessage(code, null, Locale.forLanguageTag(locale)), args);
    }
}
