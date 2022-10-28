package by.testtask.controller;

import by.testtask.model.Literature;
import by.testtask.service.LiteratureService;
import by.testtask.service.LiteratureServiceImpl;
import by.testtask.util.Constants;

import java.sql.SQLException;
import java.util.InputMismatchException;

public class UserController {

    private final LiteratureService literatureService = new LiteratureServiceImpl();
//            1 - для просмотра каталога
//            2 - для добавления в каталог новой литературы
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
        } else {
            System.out.println(Constants.FAILED_CREATE_LITERATURE);
        }
    }

    public void removeLiterature() {
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