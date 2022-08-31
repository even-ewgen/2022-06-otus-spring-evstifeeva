package ru.otus.lib.dao;

import ru.otus.lib.domain.Book;

import java.util.List;

public interface BookDao {

    Long count();

    long insert(Book book);

    List<Book> getAll();

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);
}
