package by.testtask.repository;

import by.testtask.model.Literature;
import by.testtask.util.ConnectionManager;
import by.testtask.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;

public class LiteratureRepositoryImpl implements LiteratureRepository {

    @Override
    public void initializeTable() throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS LITERATURE( " +
                            "LITERATURE_ID INT PRIMARY KEY AUTO_INCREMENT, " +
                            "TYPE VARCHAR(50) NOT NULL, " +
                            "AUTHOR VARCHAR(255) NOT NULL, " +
                            "TITLE VARCHAR(255) NOT NULL, " +
                            "PUBLISHING_HOUSE VARCHAR(255) NOT NULL, " +
                            "DATE_OF_PUBLICATION DATE NOT NULL, " +
                            "NUMBER_OF_PAGES INT NOT NULL)"
            );
            stmt.execute();
        }
    }

    @Override
    public void print() throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM LITERATURE");
            ResultSet literatures = stmt.executeQuery();
            print(literatures);
        }
    }

    @Override
    public void print(String searchWord) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * " +
                            "FROM LITERATURE " +
                            "WHERE TYPE LIKE ? " +
                            "OR AUTHOR LIKE ? " +
                            "OR TITLE LIKE ? " +
                            "OR PUBLISHING_HOUSE LIKE ?"
            );
            for (int i = 1; i <= 4; i++) {
                stmt.setString(i, "%" + searchWord + "%");
            }
            ResultSet literatures = stmt.executeQuery();
            print(literatures);
        }
    }

    @Override
    public void create(Literature literature) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO LITERATURE " +
                            "(TYPE, AUTHOR, TITLE, PUBLISHING_HOUSE, " +
                            "DATE_OF_PUBLICATION, NUMBER_OF_PAGES) " +
                            "VALUES (?, ?, ?, ?, ?, ?)"
            );
            setPreparedStatementParameters(literature, stmt);
            stmt.execute();
        }
    }

    @Override
    public boolean check(Literature literature) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM LITERATURE " +
                            "WHERE TYPE = ? " +
                            "AND AUTHOR = ? " +
                            "AND TITLE = ? " +
                            "AND PUBLISHING_HOUSE = ?" +
                            "AND DATE_OF_PUBLICATION = ? " +
                            "AND NUMBER_OF_PAGES = ?"
            );
            setPreparedStatementParameters(literature, stmt);
            return stmt.executeQuery().first();
        }
    }

    @Override
    public boolean check(int id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM LITERATURE WHERE LITERATURE_ID = ?");
            stmt.setInt(1, id);
            return stmt.executeQuery().first();
        }
    }

    @Override
    public void remove(int id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM LITERATURE WHERE LITERATURE_ID = ?");
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void update(String column, Object newValue, int id)
            throws SQLException, IllegalArgumentException, UnsupportedOperationException {
        Literature newLiterature = getLiterature(id);
        updateLiterature(newLiterature, column, newValue);
        boolean isValidLiterature = !check(newLiterature);
        if (isValidLiterature) {
            try (Connection connection = ConnectionManager.open()) {
                PreparedStatement stmt = connection.prepareStatement(
                        "UPDATE LITERATURE " +
                                "SET TYPE = ?, " +
                                "AUTHOR = ?, " +
                                "TITLE = ?, " +
                                "PUBLISHING_HOUSE = ?, " +
                                "DATE_OF_PUBLICATION = ?, " +
                                "NUMBER_OF_PAGES = ? " +
                                "WHERE LITERATURE_ID = ?"
                );
                setPreparedStatementParameters(newLiterature, stmt);
                stmt.setInt(7, id);
                stmt.execute();
            }
        } else {
            throw new UnsupportedOperationException();
        }

    }

    private Literature getLiterature(int id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM LITERATURE WHERE LITERATURE_ID = ?");
            stmt.setInt(1, id);
            ResultSet literature = stmt.executeQuery();
            literature.first();
            return Literature.builder()
                    .id(literature.getInt(1))
                    .type(literature.getString(2))
                    .author(literature.getString(3))
                    .title(literature.getString(4))
                    .publishingHouse(literature.getString(5))
                    .dateOfPublication(literature.getDate(6).toLocalDate())
                    .numberOfPages(literature.getInt(7))
                    .build();
        }
    }

    private void print(ResultSet literatures) throws SQLException {
        if (literatures.first()) {
            do {
                System.out.println(
                        Literature.builder()
                                .id(literatures.getInt(1))
                                .type(literatures.getString(2))
                                .author(literatures.getString(3))
                                .title(literatures.getString(4))
                                .publishingHouse(literatures.getString(5))
                                .dateOfPublication(literatures.getDate(6).toLocalDate())
                                .numberOfPages(literatures.getInt(7))
                                .build()
                );
            } while (literatures.next());
        } else {
            System.out.println(Constants.LITERATURE_IS_MISSING);
        }
    }

    private void setPreparedStatementParameters(Literature literature,
                                                PreparedStatement stmt) throws SQLException {
        stmt.setString(1, literature.getType());
        stmt.setString(2, literature.getAuthor());
        stmt.setString(3, literature.getTitle());
        stmt.setString(4, literature.getPublishingHouse());
        stmt.setDate(5, Date.valueOf(literature.getDateOfPublication()));
        stmt.setInt(6, literature.getNumberOfPages());
    }

    private void updateLiterature(Literature literature, String column, Object newValue)
            throws IllegalArgumentException {
        switch (column) {
            case "type" -> literature.setType((String) newValue);
            case "author" -> literature.setAuthor((String) newValue);
            case "title" -> literature.setTitle((String) newValue);
            case "publishingHouse" -> literature.setPublishingHouse((String) newValue);
            case "dateOfPublication" -> literature.setDateOfPublication(
                    (LocalDate) newValue);
            case "numberOfPages" -> literature.setNumberOfPages((Integer) newValue);
            default -> throw new IllegalArgumentException();
        }
    }
}