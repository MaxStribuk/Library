package by.testtask.util;

import java.util.Scanner;

public final class Constants {

    public static final Scanner INPUT = new Scanner(System.in);
    public static final String GREETING = "Добро пожаловать в приложение Моя библиотека!!!\n" +
            "Каталог библиотеки ведется на английском языке";
    public static final String FAREWELL = "Спасибо, что воспользовались приложением, до свидания!!!";
    public static final String FAILED_LOAD_DRIVER = "Не удалось загрузить jdbc драйвер";
    public static final String FAILED_CONNECTION_DATABASE = "Не удалось установить " +
            "соединение с базой данных";
    public static final String FAILED_READ_FILE = "Не удалось прочитать файл application.properties";
    public static final String FAILED_ADD_LITERATURE = "Выполнить операцию не удалась. " +
            "Литература уже находится в базе данных";
    public static final String FAILED_OPERATION = "Выполнить операцию не удалось. " +
            "Проверьте корректность ID";
    public static final String MENU_MAIN = """
            Пожалуйста, введите:
            1 - для просмотра каталога
            2 - для добавления в каталог новой литературы
            3 - для удаления литературы из каталога
            4 - для внесения изменений в литературу из каталога
            5 - для поиска по каталогу
            0 - для выхода из приложения
            """;
    public static final String MENU_UPDATE = """
            Пожалуйста, введите номер операции изменения литературы:
            1 - для изменения типа
            2 - для изменения автора
            3 - для изменения названия
            4 - для изменения издательства
            5 - для изменения даты публикации
            6 - для изменения количества страниц
            0 - для возврата в главное меню
            """;
    public static final String INVALID_INPUT = "Некорректный ввод";
    public static final String INVALID_OPERATION = "Неподдерживаемая операция";
    public static final String SUCCESSFUL_OPERATION = "Операция завершена успешно";
    public static final String LITERATURE_IS_MISSING = "К сожалению, " +
            "запрошенная Вами литература в каталоге библиотеки отсутствует";
    public static final String INPUT_TYPE = """
            Пожалуйста, введите:
            1 - чтобы установить тип литературы - книга
            2 - чтобы установить тип литературы - журнал
            3 - чтобы установить тип литературы - газета
            4 - чтобы установить тип литературы - другое
            0 - для возврата в главное меню
            """;
    public static final String INPUT_AUTHOR = "Пожалуйста, задайте автора(ов)\n";
    public static final String INPUT_TITLE = "Пожалуйста, задайте название\n";
    public static final String INPUT_PUBLISHING_HOUSE = "Пожалуйста, " +
            "задайте издательство\n";
    public static final String INPUT_DATE_OF_PUBLICATION = "Пожалуйста, " +
            "задайте дату издания в формате yyyy mm dd\n";
    public static final String INPUT_NUMBER_OF_PAGES = "Пожалуйста, " +
            "задайте количество страниц\n";
    public static final String INPUT_ID = """
            Если Вам не известен ID, Вы можете восспользоваться нашим каталогом.
            Пожалуйста, введите ID литературы
            """;
    public static final String INPUT_REQUEST = "Пожалуйста, введите запрос для поиска\n";
    public static final String CANCELLATION_OPERATION = "или введите 0 " +
            "для возврата в главное меню";

    private Constants() {
    }
}