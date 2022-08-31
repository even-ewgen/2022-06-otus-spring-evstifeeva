package ru.otus.lib.dao;

import ru.otus.lib.domain.Author;

import java.util.List;

public interface AuthorDao {

    Long count();

    long insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    void update(Author author);

    void deleteById(long id);
}
