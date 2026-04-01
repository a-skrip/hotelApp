package ru.skriplex.springhotelapplication.model;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Data
public class HotelModel {

    private Long id;
    private String name;
    private Integer stars;
    private String description;
    private Instant creationDate;
    private Instant updatedDate;
}
