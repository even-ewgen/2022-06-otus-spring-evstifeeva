package ru.otus.exam.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StudentCard {

    private String firstName;
    private String lastName;
    private int score;
    private boolean isPass;

}
