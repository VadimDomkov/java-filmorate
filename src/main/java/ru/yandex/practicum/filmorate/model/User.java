package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User extends Entity {

    @NotNull
    @Email
    private String email;
    @NotNull
    @Pattern(regexp = "^[A-Za-z0-9]+([A-Za-z0-9]*|[._-]?[A-Za-z0-9]+)*$")
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
    Set<Long> friends = new HashSet<>();

    public boolean isNameNull() {
        return name == null || name.isEmpty();
    }

    public void addFriend(Long id) {
        friends.add(id);
    }

    public void removeFriend(Long id) {
        friends.remove(id);
    }
}
