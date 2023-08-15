package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validator.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
public class Film extends Entity {
 //   private int id;
    @NotBlank
    private String name;
    @Pattern(regexp = "^.{0,200}$")
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
}
