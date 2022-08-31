package ru.otus.lib.service;

import org.springframework.stereotype.Service;
import ru.otus.lib.dao.AuthorDao;
import ru.otus.lib.dao.BookDao;
import ru.otus.lib.dao.GenreDao;
import ru.otus.lib.domain.Book;
import ru.otus.lib.exception.NotFoundException;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookDao bookDao;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public BookServiceImpl(BookDao bookDao, AuthorDao authorDao, GenreDao genreDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public long createBook(String title, long authorID, long genreID) throws NotFoundException {
        if (authorDao.getById(authorID) == null)
            throw new NotFoundException("Author with that id not found");

        if (genreDao.getById(genreID) == null)
            throw new NotFoundException("Genre with that id not found");

        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(authorDao.getById(authorID));
        book.setGenre(genreDao.getById(genreID));

        return bookDao.insert(book);
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public Book getById(long id) {
        return bookDao.getById(id);
    }

    @Override
    public void updateBook(Book book) throws NotFoundException {
        if (book.getAuthor() != null && authorDao.getById(book.getAuthor().getId()) == null)
            throw new NotFoundException("Author with that id not found");

        if (book.getGenre() != null && genreDao.getById(book.getGenre().getId()) == null)
            throw new NotFoundException("Genre with that id not found");

        bookDao.update(book);
    }

    @Override
    public void deleteById(long id) {
        bookDao.deleteById(id);
    }
}
