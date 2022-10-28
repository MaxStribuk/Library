package by.testtask.model;

public enum Types {

    BOOK ("Книга"),
    JOURNAL ("Журнал"),
    NEWSPAPER ("Газета"),
    OTHER ("Другое");

    private final String type;

    Types(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}