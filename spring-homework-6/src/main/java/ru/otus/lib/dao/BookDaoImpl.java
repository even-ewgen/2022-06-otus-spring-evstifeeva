package ru.otus.lib.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.lib.domain.Book;
import ru.otus.lib.domain.BookMapper;

import java.util.List;
import java.util.Objects;

@Repository
public class BookDaoImpl implements BookDao {

    private final NamedParameterJdbcOperations jdbc;

    public BookDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from book", Long.class);
    }

    @Override
    public long insert(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());
        params.addValue("id", book.getId());

        StringBuilder sb = new StringBuilder("insert into ");
        sb.append("book(title, author_id, genre_id");
        if (book.getId() != null)
            sb.append(",id");
        sb.append(") values (:title, :author_id, :genre_id");
        if (book.getId() != null)
            sb.append(",:id");
        sb.append(")");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sb.toString(), params, keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Book> getAll() {
        return jdbc.query(
            "select \n" +
            " b.id as id \n" +
            ",b.title as title \n" +
            ",a.id as authorID \n" +
            ",a.first_name as firstName \n" +
            ",a.last_name as lastName \n" +
            ",a.middle_name as middleName \n" +
            ",a.full_name as fullName \n" +
            ",g.id as genreID \n" +
            ",g.name as genreName \n" +
            "from book b  \n" +
            "left join author a on a.id = b.author_id  \n" +
            "left join genre g on g.id = b.genre_id"
            , new BookMapper());
    }

    @Override
    public Book getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.queryForObject(
            "select \n" +
            " b.id as id \n" +
            ",b.title as title \n" +
            ",a.id as authorID \n" +
            ",a.first_name as firstName \n" +
            ",a.last_name as lastName \n" +
            ",a.middle_name as middleName \n" +
            ",a.full_name as fullName \n" +
            ",g.id as genreID \n" +
            ",g.name as genreName \n" +
            "from book b \n" +
            "left join author a on a.id = b.author_id \n" +
            "left join genre g on g.id = b.genre_id \n" +
            "where b.id = :id"
            , params, new BookMapper());
    }

    @Override
    public void update(Book book) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", book.getId());

        StringBuilder sb = new StringBuilder("update book set ");
        if (book.getTitle() != null) {
            sb.append("title=:title,");
            params.addValue("title", book.getTitle());
        }
        if (book.getAuthor() != null) {
            sb.append("author_id=:author_id,");
            params.addValue("author_id", book.getAuthor().getId());
        }
        if (book.getGenre() != null) {
            sb.append("genre_id=:genre_id");
            params.addValue("genre_id", book.getGenre().getId());
        }
        if (sb.length()-1 == sb.lastIndexOf(","))
            sb.replace(sb.lastIndexOf(","), sb.lastIndexOf(",")+1,"");

        sb.append(" where id = :id");

        jdbc.update(sb.toString(), params);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update("delete from book b where b.id = :id", params);
    }
}
