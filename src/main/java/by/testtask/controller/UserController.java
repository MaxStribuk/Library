package by.testtask.controller;

import by.testtask.model.Literature;
import by.testtask.service.LiteratureService;
import by.testtask.service.LiteratureServiceImpl;
import by.testtask.util.Constants;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class UserController {

    private final LiteratureService literatureService = new LiteratureServiceImpl();
//            3 - для удаления литературы из каталога
//            4 - для внесения изменений в литературу из каталога
//            5 - для создания выборки из каталога
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
            System.out.println(Constants.SUCCESSFUL_OPERATION);
        } else {
            System.out.println(Constants.FAILED_CREATE_LITERATURE);
        }
    }

    public void removeLiterature() throws SQLException {
        int id;
        try {
            id = Integer.parseInt(
                    literatureService.inputData("ID", false));
        } catch (InputMismatchException e) {
            return;
        }
        boolean isValidID = literatureService.checkLiterature(id);
        if (isValidID) {
            literatureService.removeLiterature(id);
            System.out.println(Constants.SUCCESSFUL_OPERATION);
        } else {
            System.out.println(Constants.FAILED_REMOVE_LITERATURE);
        }
    }

    public void updateLiterature() {
    }

    public void searchLiterature() {
    }

    public void initializeTable() {
        try {
            literatureService.initializeTable();
        } catch (SQLException e) {
            throw new RuntimeException(Constants.FAILED_CONNECTION_DATABASE);
        }
    }
}