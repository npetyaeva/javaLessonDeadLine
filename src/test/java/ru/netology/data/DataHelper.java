package ru.netology.data;

import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor
public class DataHelper {

    @Value
    public static class User {
        String id;
        String login;
        String password;
        String status;
    }

    public static User getUserInfo() {
        return new User("c9eb4a68-6215-4a5b-a092-0bd53898cd19", "vasya", "qwerty123", "active");
    }
}
