package by.testtask.service;

import by.testtask.model.Literature;
import by.testtask.model.Types;
import by.testtask.repository.LiteratureRepository;
import by.testtask.repository.LiteratureRepositoryImpl;
import by.testtask.util.Constants;

import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.InputMismatchException;

public class LiteratureServiceImpl implements LiteratureService {

    LiteratureRepository literatureRepository = new LiteratureRepositoryImpl();

    @Override
    public void initializeTable() throws SQLException {
        literatureRepository.initializeTable();
    }

    @Override
    public void showLiterature() throws SQLException {
        literatureRepository.print();
    }

    @Override
    public void showLiterature(String searchWord) throws SQLException {
        literatureRepository.print(searchWord);
    }

    @Override
    public Literature inputLiterature() throws InputMismatchException {
        return Literature.builder()
                .type(inputType())
                .author(inputData("author", true))
                .title(inputData("title", true))
                .publishingHouse(inputData("publishingHouse", true))
                .dateOfPublication(inputDateOfPublication())
                .numberOfPages(Integer.parseInt(
                        inputData("numberOfPages", false)))
                .build();
    }

    @Override
    public String inputData(String column, boolean isReturnString)
            throws InputMismatchException {
        String message = selectMessage(column) + Constants.CANCELLATION_OPERATION;
        String input;
        while (true) {
            System.out.print(message);
            input = Constants.INPUT.nextLine().trim();
            if (input.equals("0")) {
                throw new InputMismatchException();
            }
            boolean isCorrectInput = checkCorrectInput(input, isReturnString);
            if (isCorrectInput) {
                return input.toLowerCase();
            } else {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    @Override
    public boolean checkLiterature(Literature literature) throws SQLException {
        return literatureRepository.check(literature);
    }

    @Override
    public boolean checkLiterature(int id) throws SQLException {
        return literatureRepository.check(id);
    }

    @Override
    public void addLiterature(Literature literature) throws SQLException {
        literatureRepository.create(literature);
    }

    @Override
    public void removeLiterature(int id) {
        try {
            literatureRepository.remove(id);
        } catch (SQLException e) {
            System.out.println(Constants.FAILED_CONNECTION_DATABASE);
        }
    }

    @Override
    public void updateLiterature(int id) throws InputMismatchException {
        String input;
        while (true) {
            try {
                System.out.print(Constants.MENU_UPDATE);
                input = Constants.INPUT.nextLine();
                switch (input) {
                    case "0" -> throw new InputMismatchException();
                    case "1" -> {
                        String newType = inputType();
                        literatureRepository.update("type", newType, id);
                        return;
                    }
                    case "2" -> {
                        String newAuthor = inputData("author", true);
                        literatureRepository.update("author", newAuthor, id);
                        return;
                    }
                    case "3" -> {
                        String newTitle = inputData("title", true);
                        literatureRepository.update("title", newTitle, id);
                        return;
                    }
                    case "4" -> {
                        String newPublishingHouse = inputData("publishingHouse", true);
                        literatureRepository.update("publishingHouse", newPublishingHouse, id);
                        return;
                    }
                    case "5" -> {
                        LocalDate newDateOfPublication = inputDateOfPublication();
                        literatureRepository.update("dateOfPublication", newDateOfPublication, id);
                        return;
                    }
                    case "6" -> {
                        Integer newNumberOfPages = Integer.parseInt(
                                inputData("numberOfPages", false));
                        literatureRepository.update("numberOfPages", newNumberOfPages, id);
                        return;
                    }
                    default -> throw new UnsupportedOperationException();
                }
            } catch (IllegalArgumentException e) {
                System.out.println(Constants.INVALID_OPERATION);
            } catch (UnsupportedOperationException e) {
                System.out.println(Constants.FAILED_ADD_LITERATURE);
            } catch (SQLException e) {
                System.out.println(Constants.FAILED_CONNECTION_DATABASE);
            }
        }
    }

    private String inputType() throws InputMismatchException {
        String type;
        while (true) {
            try {
                System.out.print(Constants.INPUT_TYPE);
                type = Constants.INPUT.nextLine();
                return switch (type) {
                    case "0" -> throw new InputMismatchException();
                    case "1" -> Types.BOOK.toString();
                    case "2" -> Types.JOURNAL.toString();
                    case "3" -> Types.NEWSPAPER.toString();
                    case "4" -> Types.OTHER.toString();
                    default -> throw new NumberFormatException();
                };
            } catch (NumberFormatException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private LocalDate inputDateOfPublication() throws InputMismatchException {
        String input;
        while (true) {
            try {
                System.out.println(Constants.INPUT_DATE_OF_PUBLICATION);
                input = Constants.INPUT.nextLine();
                if (input.equals("0")) {
                    throw new InputMismatchException();
                }
                String[] date = input.split(" ");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);
                LocalDate dateOfPublication = LocalDate.of(year, month, day);
                if (dateOfPublication.isBefore(LocalDate.now())) {
                    return dateOfPublication;
                } else {
                    throw new DateTimeException("");
                }
            } catch (NumberFormatException
                     | ArrayIndexOutOfBoundsException
                     | DateTimeException e) {
                System.out.println(Constants.INVALID_INPUT);
            }
        }
    }

    private String selectMessage(String column) throws InputMismatchException {
        return switch (column) {
            case "author" -> Constants.INPUT_AUTHOR;
            case "title" -> Constants.INPUT_TITLE;
            case "publishingHouse" -> Constants.INPUT_PUBLISHING_HOUSE;
            case "numberOfPages" -> Constants.INPUT_NUMBER_OF_PAGES;
            case "ID" -> Constants.INPUT_ID;
            case "search" -> Constants.INPUT_REQUEST;
            default -> throw new InputMismatchException();
        };
    }

    private boolean checkCorrectInput(String input, boolean isReturnString) {
        try {
            return isReturnString
                    ? input.length() > 0
                    && input.matches("[a-zA-Z0-9 .,-:\"]+")
                    && input.matches(".*[a-zA-Z]+.*")
                    : Integer.parseInt(input) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}