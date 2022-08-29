package ru.otus.exam.service;

import org.springframework.lang.Nullable;

public interface PrinterService {

    void print(String code);

    void print(String code, @Nullable Object... args);
}
