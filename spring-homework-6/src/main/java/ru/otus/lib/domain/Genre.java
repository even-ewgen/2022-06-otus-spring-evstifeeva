package ru.otus.lib.domain;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return id + " - " + name;
    }
}
