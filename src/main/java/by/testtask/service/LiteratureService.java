package by.testtask.service;

import by.testtask.model.Literature;

import java.sql.SQLException;
import java.util.InputMismatchException;

public interface LiteratureService {

    void initializeTable() throws SQLException;

    void showLiterature() throws SQLException;

    void showLiterature(String searchWord) throws SQLException;

    Literature inputLiterature();

    String inputData(String column, boolean isReturnString) throws InputMismatchException;

    boolean checkLiterature(Literature literature) throws SQLException;

    boolean checkLiterature(int id) throws SQLException;

    void addLiterature(Literature literature) throws SQLException;

    void removeLiterature(int id);

    void updateLiterature(int id) throws InputMismatchException;
}