package ru.otus.lib.service;

import ru.otus.lib.domain.Book;
import ru.otus.lib.exception.NotFoundException;

import java.util.List;

public interface BookService {

    long createBook(String title, long authorID, long genreID) throws NotFoundException;

    List<Book> getAll();

    Book getById(long id);

    void updateBook(Book book) throws NotFoundException;

    void deleteById(long id);
}
