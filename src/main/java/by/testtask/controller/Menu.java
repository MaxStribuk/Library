package by.testtask.controller;

import by.testtask.util.Constants;

import java.sql.SQLException;

public class Menu {

    private final UserController userController = new UserController();

    public void start() {
        System.out.println(Constants.GREETING);
        userController.initializeTable();
        printMenu();
    }

    private void printMenu() {
        String input;
        while (true) {
            System.out.print(Constants.MENU_MAIN);
            input = Constants.INPUT.nextLine();
            try {
                switch (input) {
                    case "1" -> userController.showLiterature();
                    case "2" -> userController.addLiterature();
                    case "3" -> userController.updateLiterature("remove");
                    case "4" -> userController.updateLiterature("update");
                    case "5" -> userController.searchLiterature();
                    case "0" -> {
                        System.out.println(Constants.FAREWELL);
                        return;
                    }
                    default -> System.out.println(Constants.INVALID_INPUT);
                }
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }
}