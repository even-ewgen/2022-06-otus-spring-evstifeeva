package ru.otus.lib.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.lib.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

@DisplayName("Class GenreDaoImpl")
@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(value = {GenreDaoImpl.class})
class GenreDaoTest {

    private static final int EXPECTED_GENRE_COUNT = 1;

    public static final long EXISTING_GENRE_ID = 1L;
    public static final String EXISTING_GENRE_NAME = "Thriller";

    @Autowired
    private GenreDao genreDao;
    
    @Test
    @DisplayName("Return right amount of rows")
    void countTest() {
        assertThat(genreDao.count())
                .isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    @DisplayName("Add genre correctly")
    void insertTest() {
        long beforeCount = genreDao.count();

        Genre genre = new Genre(2L, "Test genre");

        assertThatCode(() -> genreDao.insert(genre))
                .doesNotThrowAnyException();
        assertThat(genreDao.count())
                .isEqualTo(beforeCount + 1);
        assertThat(genreDao.getById(2L))
                .usingRecursiveComparison()
                .isEqualTo(genre);
    }

    @Test
    @DisplayName("Return expected genre by id")
    void getByIdTest() {
        Genre genre = genreDao.getById(EXISTING_GENRE_ID);

        assertThat(genre)
                .usingRecursiveComparison()
                .isEqualTo(createExpectedGenre());
    }

    @Test
    @DisplayName("Return expected list of genres")
    void getAllTest() {
        Genre expectedGenre = createExpectedGenre();

        List<Genre> genres = genreDao.getAll();

        assertThat(genres)
                .usingFieldByFieldElementComparator()
                .containsExactlyInAnyOrder(expectedGenre);

        assertThat(genres.size())
                .isEqualTo(EXPECTED_GENRE_COUNT);
    }

    @Test
    @DisplayName("Change genre correctly")
    void update() {
        Genre expectedGenre = createExpectedGenre();
        Genre changedGenre = createExpectedGenre();

        changedGenre.setName("Test genre");

        genreDao.update(changedGenre);

        Genre genre = genreDao.getById(EXISTING_GENRE_ID);

        assertThat(genre)
                .isEqualTo(changedGenre)
                .isNotEqualTo(expectedGenre);
    }

    @Test
    @DisplayName("Delete genre by if correctly")
    void deleteById() {
        long beforeCount = genreDao.count();

        genreDao.deleteById(EXISTING_GENRE_ID);

        assertThat(genreDao.count())
                .isEqualTo(beforeCount - 1);
    }

    private Genre createExpectedGenre() {
        return new Genre(EXISTING_GENRE_ID, EXISTING_GENRE_NAME);
    }
}