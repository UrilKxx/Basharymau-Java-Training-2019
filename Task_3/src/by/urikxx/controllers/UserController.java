package by.urikxx.controllers;

import by.urikxx.models.User;

import java.util.ArrayList;
import java.util.Collection;

public final class UserController {

    private static volatile UserController instance;
    private Collection<User> users;

    private UserController(){
        users = new ArrayList<>();
        users.add(new User("admin","admin@admin.ru","admin"));
        users.add(new User("urikxx","zxasv4402@gmail.com","urikxx"));
    }

    public static UserController getInstance(){
        UserController userController = instance;
        if (userController != null){
            return userController;
        }
        synchronized (UserController.class){
            if (instance == null){
                instance = new UserController();
            }
            return instance;
        }
        /*if (instance == null) {
            instance = new UserController();
        }
        return instance;*/
    }

    public User authorization(String login, String password){
        return users.stream().filter(user -> user.getLogin().equals(login)
                                             && user.getPassword().equals(password)).findFirst().orElse(null);
    }

    public User registration(String login, String email, String password){
        User regUser = null;
        for(User user : users){
            if (user.getLogin().equals(login) || user.getEmail().equals(email))
                regUser = user;
        }
        if (regUser == null) {
            User newUser = new User(login, email, password);
            users.add(newUser);
            return newUser;
        } else {
            return null;
        }
    }


}
