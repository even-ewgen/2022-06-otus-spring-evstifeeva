package ru.otus.lib.domain;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;

    @Override
    public String toString() {
        return id + " - " + fullName;
    }
}
