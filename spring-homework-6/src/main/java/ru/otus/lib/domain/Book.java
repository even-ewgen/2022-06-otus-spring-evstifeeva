package ru.otus.lib.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private Long id;
    private String title;
    private Author author;
    private Genre genre;

    @Override
    public String toString() {
        return id + " - " + title + ", " + author.getFullName() + " (" + genre.getName() + ")";
    }
}
