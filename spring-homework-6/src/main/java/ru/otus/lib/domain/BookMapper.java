package ru.otus.lib.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BookMapper implements RowMapper<Book> {

    @Override
    public Book mapRow(ResultSet resultSet, int i) throws SQLException {
        long id = resultSet.getLong("id");
        String title = resultSet.getString("title");

        long authorID = resultSet.getLong("authorID");
        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String middleName = resultSet.getString("middleName");
        String fullName = resultSet.getString("fullName");
        Author author = new Author(authorID, firstName, lastName, middleName, fullName);

        long genreID = resultSet.getLong("genreID");
        String name = resultSet.getString("genreName");
        Genre genre = new Genre(genreID, name);

        return new Book(id, title, author, genre);
    }
}