package ru.otus.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Question {

    private String question;
    private List<Answer> answers;
    private int rightAnswer;
}
