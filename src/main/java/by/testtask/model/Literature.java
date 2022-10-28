package by.testtask.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Literature {

    private int id;
    private String type;
    private String author;
    private String title;
    private String publishingHouse;
    private LocalDate dateOfPublication;
    private int numberOfPages;
}