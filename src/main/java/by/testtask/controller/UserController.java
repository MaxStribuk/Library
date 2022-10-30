package by.testtask.controller;

import by.testtask.model.Literature;
import by.testtask.service.LiteratureService;
import by.testtask.service.LiteratureServiceImpl;
import by.testtask.util.Constants;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.function.Consumer;

public class UserController {

    private final LiteratureService literatureService = new LiteratureServiceImpl();

    public void initializeTable() {
        try {
            literatureService.initializeTable();
        } catch (SQLException e) {
            throw new RuntimeException(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    public void showLiterature() throws SQLException {
        literatureService.showLiterature();
    }

    public void addLiterature() throws SQLException {
        Literature literature;
        try {
            literature = literatureService.inputLiterature();
        } catch (InputMismatchException e) {
            return;
        }
        boolean isNotBusyLiterature = !literatureService.checkLiterature(literature);
        if (isNotBusyLiterature) {
            literatureService.addLiterature(literature);
        }
        System.out.println(isNotBusyLiterature
                ? Constants.SUCCESSFUL_OPERATION
                : Constants.FAILED_ADD_LITERATURE
        );
    }


    public void updateLiterature(Consumer<Integer> task, int id) throws SQLException {
        boolean isValidID = literatureService.checkLiterature(id);
        if (isValidID) {
            try {
                task.accept(id);
            } catch (InputMismatchException e) {
                return;
            }
        }
        System.out.println(isValidID
                ? Constants.SUCCESSFUL_OPERATION
                : Constants.FAILED_OPERATION
        );
    }

    public void updateLiterature(String task) throws SQLException {
        Integer id = inputID();
        if (id == null) {
            return;
        }
        try {
            switch (task) {
                case "remove" -> updateLiterature(literatureService::removeLiterature, id);
                case "update" -> updateLiterature(literatureService::updateLiterature, id);
                default -> throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.println(Constants.INVALID_OPERATION);
        }
    }

    public void searchLiterature() throws SQLException {
        String searchWord;
        try {
            searchWord = literatureService.inputData("search", true);
        } catch (InputMismatchException e) {
            return;
        }
        literatureService.showLiterature(searchWord);
    }

    private Integer inputID() {
        try {
            String id = literatureService.inputData("ID", false);
            return Integer.parseInt(id);
        } catch (InputMismatchException e) {
            return null;
        }
    }
}