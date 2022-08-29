package ru.otus.exam.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public String read () throws IOException {
        return bufferedReader.readLine();
    }
}
