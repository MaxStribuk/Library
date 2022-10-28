package by.testtask.repository;

import by.testtask.model.Literature;
import by.testtask.util.ConnectionManager;
import by.testtask.util.Constants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class LiteratureRepositoryImpl implements LiteratureRepository {

    @Override
    public void create(Literature literature) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO LITERATURE " +
                            "(TYPE, AUTHOR, TITLE, PUBLISHING_HOUSE, " +
                            "DATE_OF_PUBLICATION, NUMBER_OF_PAGES) " +
                            "VALUES (?, ?, ?, ?, ?, ?)");
            setPreparedStatementParameters(literature, stmt);
            stmt.execute();
        }
    }

    @Override
    public void print() throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM literature");
            ResultSet literatures = stmt.executeQuery();
            print(literatures);
        }
    }

    @Override
    public void initializeTable() throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS LITERATURE( " +
                            "ID INT PRIMARY KEY AUTO_INCREMENT, " +
                            "TYPE VARCHAR(50) NOT NULL, " +
                            "AUTHOR VARCHAR(255) NOT NULL, " +
                            "title VARCHAR(255) NOT NULL, " +
                            "PUBLISHING_HOUSE VARCHAR(255) NOT NULL, " +
                            "DATE_OF_PUBLICATION DATE NOT NULL, " +
                            "NUMBER_OF_PAGES INT NOT NULL)"
            );
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
}