package by.testtask.service;

import by.testtask.model.Literature;

import java.sql.SQLException;

public interface LiteratureService {

    void showLiterature() throws SQLException;

    void showLiterature(String searchWord) throws SQLException;

    void initializeTable() throws SQLException;

    Literature inputLiterature();

    boolean checkLiterature(Literature literature) throws SQLException;

    boolean checkLiterature(int id) throws SQLException;
    void addLiterature(Literature literature) throws SQLException;

    String inputData(String column, boolean isReturnString);

    void removeLiterature(int id) throws SQLException;
}
