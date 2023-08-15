package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class User extends Entity {
//    private int id;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+([A-Za-z0-9]*|[._-]?[A-Za-z0-9]+)*$")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;

    public boolean isNameNull() {
        return name == null;
    }
}
