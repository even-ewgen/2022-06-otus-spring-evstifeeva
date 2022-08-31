package ru.otus.lib.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.lib.domain.Genre;
import ru.otus.lib.domain.GenreMapper;

import java.util.List;
import java.util.Objects;

@Repository
public class GenreDaoImpl implements GenreDao {

    private final NamedParameterJdbcOperations jdbc;

    public GenreDaoImpl(NamedParameterJdbcOperations jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Long count() {
        return jdbc.getJdbcOperations().queryForObject("select count(*) from genre", Long.class);
    }

    @Override
    public long insert(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", genre.getId());
        params.addValue("name", genre.getName());

        StringBuilder sb = new StringBuilder("insert into ");
        sb.append("genre(name");
        if (genre.getId() != null)
            sb.append(",id");
        sb.append(") values (:name");
        if (genre.getId() != null)
            sb.append(",:id");
        sb.append(")");

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sb.toString(), params, keyHolder, new String[]{"id"});

        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<Genre> getAll() {
        return jdbc.query(
            "select \n" +
            " g.id as id \n" +
            ",g.name as name \n" +
            "from genre g"
            , new GenreMapper());
    }

    @Override
    public Genre getById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        return jdbc.queryForObject(
            "select \n" +
            " g.id as id \n" +
            ",g.name as name \n" +
            "from genre g \n" +
            "where g.id = :id"
            , params, new GenreMapper());
    }

    @Override
    public void update(Genre genre) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", genre.getId());

        StringBuilder sb = new StringBuilder("update genre set ");
        if (genre.getName() != null) {
            sb.append("name=:name");
            params.addValue("name", genre.getName());
        }

        sb.append(" where id = :id");

        jdbc.update(sb.toString(), params);
    }

    @Override
    public void deleteById(long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        jdbc.update("delete from genre g where g.id = :id", params);
    }
}
