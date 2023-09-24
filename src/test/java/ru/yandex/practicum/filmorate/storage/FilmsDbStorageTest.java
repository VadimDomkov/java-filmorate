package ru.yandex.practicum.filmorate.storage;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exceptions.ElementNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.film.FilmDbStorage;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmsDbStorageTest {
    private final FilmDbStorage filmDbStorage;

    private Film testFilm1 = Film.builder()
            .name("filmname")
            .description("description")
            .duration(120)
            .releaseDate(LocalDate.of(2010, 12, 12))
            .mpa(MPA.builder().id(1).build())
            .build();

    private Film testFilm2 =  Film.builder()
            .name("filmname2")
            .description("description2")
            .duration(150)
            .mpa(MPA.builder().id(3).build())
            .releaseDate(LocalDate.of(2020, 8, 18))
            .build();

    @Test
    public void findFilmById() {
        filmDbStorage.create(testFilm1);
        testFilm1.setId(1);
        Assertions.assertEquals(testFilm1.getName(), filmDbStorage.returnById(1).getName());
    }

    @Test
    public void shouldThrowExceptionWhenInvalidId() {
        ElementNotFoundException ex = Assertions.assertThrows(
                ElementNotFoundException.class,
                () -> filmDbStorage.returnById(10)
        );
        Assertions.assertEquals("Фильм с id 10 не найден", ex.getMessage());
    }
}
