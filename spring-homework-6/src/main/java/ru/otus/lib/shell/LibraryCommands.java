package ru.otus.lib.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.lib.dao.AuthorDao;
import ru.otus.lib.dao.GenreDao;
import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.exception.NotFoundException;
import ru.otus.lib.service.BookService;

import static org.springframework.shell.standard.ShellOption.NULL;

@ShellComponent
public class LibraryCommands {

    private final BookService bookService;

    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    public LibraryCommands(BookService bookService, AuthorDao authorDao, GenreDao genreDao) {
        this.bookService = bookService;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show books", key = {"showBooks", "shb"})
    public void showBooks()  {
        bookService.getAll().forEach(book -> System.out.println(book.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show authors", key = {"showAuthors", "sha"})
    public void showAuthors()  {
        authorDao.getAll().forEach(book -> System.out.println(book.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Show genres", key = {"showGenres", "shg"})
    public void showGenres()  {
        genreDao.getAll().forEach(book -> System.out.println(book.toString()));
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Add new book", key = {"addBook", "adb"})
    public void addNewBook(@ShellOption({"-t", "--title"}) String title,
                           @ShellOption({"-a", "--author"}) long authorId,
                           @ShellOption({"-g", "--genre"}) long genreId) {
        try {
            long bookID = bookService.createBook(title, authorId, genreId);
            System.out.printf("Book with id %d added%n", bookID);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Add new author", key = {"addAuthor", "ada"})
    public void addNewAuthor(@ShellOption(value = {"-f", "--first"}, defaultValue = "unknown") String firstName,
                             @ShellOption(value = {"-l", "--last"}, defaultValue = "") String lastName,
                             @ShellOption(value = {"-m", "--middle"}, defaultValue = "") String middleName) {
        StringBuilder fullName = new StringBuilder(firstName);
        if (!lastName.equals(""))
            fullName.append(" ").append(lastName);
        if (!middleName.equals(""))
            fullName.append(" ").append(middleName);

        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        author.setMiddleName(middleName);
        author.setFullName(fullName.toString());

        long id = authorDao.insert(author);
        System.out.printf("Author with id %d added%n", id);
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Add new genre", key = {"addGenre", "adg"})
    public void addNewGenre(String genreName) {
        Genre genre = new Genre();
        genre.setName(genreName);

        long id = genreDao.insert(genre);
        System.out.printf("Genre with id %d added%n", id);
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Delete book", key = {"delBook", "del"})
    public void deleteBook(@ShellOption long id) {
        bookService.deleteById(id);
        System.out.printf("Book with id %d deleted%n", id);
    }

    @SuppressWarnings("unused")
    @ShellMethod(value = "Update book", key = {"updateBook", "up"})
    public void updateBook(@ShellOption long id,
                           @ShellOption(value = {"-t", "--title"}, defaultValue = NULL) String title,
                           @ShellOption(value = {"-a", "--author"}, defaultValue = NULL) Long authorID,
                           @ShellOption(value = {"-g", "--genre"}, defaultValue = NULL) Long genreID) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);

        if (authorID != null)
            book.setAuthor(authorDao.getById(authorID));

        if (genreID != null)
            book.setGenre(genreDao.getById(genreID));

        try {
            bookService.updateBook(book);
            System.out.printf("Book with id %d updated%n", id);
        } catch (NotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

/*    @SuppressWarnings("unused")
    @ShellMethod(value = "Find books by author, genre or title", key = {"findBook", "f"})
    public void findBooks() {

    }*/
}
