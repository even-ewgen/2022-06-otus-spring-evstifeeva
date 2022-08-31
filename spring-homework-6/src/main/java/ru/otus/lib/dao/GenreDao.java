package ru.otus.lib.dao;

import ru.otus.lib.domain.Genre;

import java.util.List;

public interface GenreDao {

    Long count();

    long insert(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    void update(Genre genre);

    void deleteById(long id);
}
