package by.testtask.model;

public enum Types {

    BOOK ("book"),
    JOURNAL ("journal"),
    NEWSPAPER ("newspaper"),
    OTHER ("other");

    private final String type;

    Types(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}