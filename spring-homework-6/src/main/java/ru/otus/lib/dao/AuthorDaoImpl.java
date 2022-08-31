package ru.otus.lib.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.lib.domain.Author;
import ru.otus.lib.domain.AuthorMapper;

import java.util.List;
import java.util.Objects;

@Repository
public class AuthorDaoImpl implements AuthorDao{

    private final NamedParameterJdbcOperations jdbc;

    public AuthorDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from author", Long.class);
    }

    @Override
    public long insert(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());
        params.addValue("first", author.getFirstName());
        params.addValue("last", author.getLastName());
        params.addValue("middle", author.getMiddleName());
        params.addValue("full", author.getFullName());

        StringBuilder sb = new StringBuilder("insert into ");
        sb.append("author(first_name, last_name, middle_name, full_name");
        if (author.getId() != null)
            sb.append(",id");
        sb.append(") values (:first, :last, :middle, :full");
        if (author.getId() != null)
            sb.append(",:id");
        sb.append(")");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sb.toString(), params, keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Author> getAll() {
        return jdbc.query(
            "select \n" +
            " a.id as id \n" +
            ",a.first_name as firstName \n" +
            ",a.last_name as lastName \n" +
            ",a.middle_name as middleName \n" +
            ",a.full_name as fullName \n" +
            "from author a"
            , new AuthorMapper());
    }

    @Override
    public Author getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.queryForObject(
            "select \n" +
            " a.id as id \n" +
            ",a.first_name as firstName \n" +
            ",a.last_name as lastName \n" +
            ",a.middle_name as middleName \n" +
            ",a.full_name as fullName \n" +
            "from author a \n" +
            "where a.id = :id"
            , params, new AuthorMapper());
    }

    @Override
    public void update(Author author) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", author.getId());

        StringBuilder sb = new StringBuilder("update author set ");
        if (author.getFirstName() != null) {
            sb.append("first_name=:first,");
            params.addValue("first", author.getFirstName());
        }
        if (author.getLastName() != null) {
            sb.append("last_name=:last,");
            params.addValue("last", author.getLastName());
        }
        if (author.getMiddleName() != null) {
            sb.append("middle_name=:middle");
            params.addValue("middle", author.getMiddleName());
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

        jdbc.update("delete from author a where a.id = :id", params);
    }
}
