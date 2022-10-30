package by.testtask.repository;

import by.testtask.model.Literature;

import java.sql.SQLException;

public interface LiteratureRepository {

    void initializeTable() throws SQLException;

    void print() throws SQLException;

    void print(String searchWord) throws SQLException;

    void create(Literature literature) throws SQLException;

    boolean check(Literature literature) throws SQLException;

    boolean check(int id) throws SQLException;

    void remove(int id) throws SQLException;

    void update(String column, Object newValue, int id)
            throws SQLException, IllegalArgumentException, UnsupportedOperationException;
}