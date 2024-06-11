package com.meusprojetos.Game.List.tests;

import com.meusprojetos.Game.List.entities.Role;
import com.meusprojetos.Game.List.entities.User;

public class UserFactory {

    public static User createClientUser() {

        User user = new User(1L, "Ana", "ana@gmail.com", "$2a$10$HQrFaAoFOfNNL44l7MF02uNz3A75EI8JpV03pHpA7pO8KPzYuFF.q");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createAdminUser() {

        User user = new User(2L, "Andrea", "andrea@gmail.com", "$2a$10$HQrFaAoFOfNNL44l7MF02uNz3A75EI8JpV03pHpA7pO8KPzYuFF.q");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomAdminUser(Long id, String username) {

        User user = new User(id, "Andrea", username, "$2a$10$HQrFaAoFOfNNL44l7MF02uNz3A75EI8JpV03pHpA7pO8KPzYuFF.q");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomClientUser(Long id, String username) {

        User user = new User(id, "Ana", username, "$2a$10$HQrFaAoFOfNNL44l7MF02uNz3A75EI8JpV03pHpA7pO8KPzYuFF.q");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }
}
