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
                            "ID INT PRIMARY KEY AUTO_INCREMENT, " +
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
                            "OR PUBLISHING_HOUSE LIKE ?");
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
                            "VALUES (?, ?, ?, ?, ?, ?)");
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
                    "SELECT * FROM LITERATURE WHERE ID = ?"
            );
            stmt.setInt(1, id);
            return stmt.executeQuery().first();
        }
    }

    @Override
    public void remove(int id) throws SQLException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = connection.prepareStatement(
                    "DELETE FROM LITERATURE WHERE ID = ?");
            stmt.setInt(1, id);
            stmt.execute();
        }
    }

    @Override
    public void update(String column, Object newValue, int id)
            throws SQLException, IllegalArgumentException {
        try (Connection connection = ConnectionManager.open()) {
            PreparedStatement stmt = selectPreparedStatement(connection,
                    column, newValue, id);
            stmt.execute();
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

    private PreparedStatement selectPreparedStatement(Connection connection,
                                                      String column, Object newValue, int id)
            throws SQLException, IllegalArgumentException {
        PreparedStatement stmt;
        switch (column) {
            case "type" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET TYPE = ? WHERE ID = ?");
                stmt.setString(1, (String) newValue);
            }
            case "author" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET AUTHOR = ? WHERE ID = ?");
                stmt.setString(1, (String) newValue);
            }
            case "title" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET TITLE = ? WHERE ID = ?");
                stmt.setString(1, (String) newValue);
            }
            case "publishingHouse" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET PUBLISHING_HOUSE = ? WHERE ID = ?");
                stmt.setString(1, (String) newValue);
            }
            case "dateOfPublication" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET DATE_OF_PUBLICATION = ? WHERE ID = ?");
                stmt.setDate(1, Date.valueOf((LocalDate) newValue));
            }
            case "numberOfPages" -> {
                stmt = connection.prepareStatement(
                        "UPDATE LITERATURE SET NUMBER_OF_PAGES = ? WHERE ID = ?");
                stmt.setInt(1, (Integer) newValue);
            }
            default -> throw new IllegalArgumentException();
        }
        stmt.setInt(2, id);
        return stmt;
    }
}