package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.exceptions.FilmNotFoundException;
import ru.yandex.practicum.filmorate.validator.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film extends Entity {

    @NotBlank
    private String name;
    @Pattern(regexp = "^.{0,200}$")
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @Positive
    private int duration;
    Set<Long> likes = new HashSet<>();

    public void addLike(long id) {
        likes.add(id);
    }

    public void deleteLike(long id) {
        if (likes.contains(id)) {
            likes.remove(id);
            return;
        }
        throw new FilmNotFoundException();
    }

    public int compareTo(Film film) {
        return film.getLikes().size() > this.getLikes().size() ? 1 : -1;
    }
}
