package com.greenie.dashboard.service;

import com.greenie.dashboard.model.User;
import com.greenie.dashboard.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers()
    {
        List<User> userList=new ArrayList<>();
        userList=userRepository.findAll();
        return userList;
    }

    public User createUser(String name, String email, String phone) {
        User user=new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        LocalDate localDate = LocalDate.now();
        user.setDate(localDate);
        return userRepository.save(user);
    }

    public User finduser(Integer id) {
        User user=userRepository.findById(id).get();
        return user;
    }

    public List<User> findByName(String searchText) {
        List<User> userList=new ArrayList<>();
        userList=userRepository.findByName(searchText);
        return userList;

    }

    public List<User> findByEmail(String searchText) {
        List<User> userList=new ArrayList<>();
        userList=userRepository.findByEmail(searchText);
        return userList;

    }
    public List<User> findByPhone(String searchText) {
        List<User> userList=new ArrayList<>();
        userList=userRepository.findByPhone(searchText);
        return userList;

    }
}
