package by.testtask.util;

import java.util.Scanner;

public final class Constants {

    public static final Scanner INPUT = new Scanner(System.in);
    public static final String GREETING = "Добро пожаловать в приложение Моя библиотека!!!";
    public static final String FAREWELL = "Спасибо, что воспользовались приложением, до свидания!!!";
    public static final String FAILED_LOAD_DRIVER = "Не удалось загрузить jdbc драйвер.";
    public static final String FAILED_CONNECTION_DATABASE = "Не удалось установить " +
            "соединение с базой данных.";
    public static final String FAILED_READ_FILE = "Не удалось прочитать файл application.properties.";
    public static final String FAILED_CREATE_LITERATURE = "Добавить литературу в базу данных не удалось. " +
            "Возможно она уже находится в базе данных.";
    public static final String MENU_MAIN = """
            Пожалуйста, введите:
            1 - для просмотра каталога
            2 - для добавления в каталог новой литературы
            3 - для удаления литературы из каталога
            4 - для внесения изменений в литературу из каталога
            5 - для создания выборки из каталога
            0 - для выхода из приложения
            """;
    public static final String INVALID_INPUT = "Некорректный ввод.";
    public static final String LITERATURE_IS_MISSING = "К сожалению, " +
            "запрошенная Вами литература в каталоге библиотеки отсутствует";
    public static final String CREATING_TYPE = """
            Пожалуйста, введите:
            1 - чтобы установить тип литературы - книга
            2 - чтобы установить тип литературы - журнал
            3 - чтобы установить тип литературы - газета
            4 - чтобы установить тип литературы - другое
            0 - для возврата в главное меню
            """;
    public static final String CREATING_AUTHOR = "Пожалуйста, задайте автора(ов) " +
            "или введите 0 для возврата в главное меню";
    public static final String CREATING_TITLE = "Пожалуйста, задайте название " +
            "или введите 0 для возврата в главное меню";
    public static final String CREATING_PUBLISHING_HOUSE = "Пожалуйста, задайте издательство " +
            "или введите 0 для возврата в главное меню";
    public static final String INVALID_INPUT_LITERATURE = "Поле не может быть пустым";
    public static final String CREATING_DATE_OF_PUBLICATION = "Пожалуйста, задайте " +
            "дату издания в формате yyyy mm dd или введите 0 для возврата в главное меню";
    public static final String CREATING_NUMBER_OF_PAGES = "Пожалуйста, задайте " +
            "количество страниц (положительное целое число) " +
            "или введите 0 для возврата в главное меню";

    private Constants() {
    }
}
