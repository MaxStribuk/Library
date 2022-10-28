package by.testtask.repository;

import by.testtask.model.Literature;

import java.sql.SQLException;

public interface LiteratureRepository {

    void create(Literature literature) throws SQLException;

    void print() throws SQLException;

    void initializeTable() throws SQLException;

    boolean check(Literature literature) throws SQLException;
}
