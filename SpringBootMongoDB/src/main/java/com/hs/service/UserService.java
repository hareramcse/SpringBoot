package com.hs.service;

import java.util.List;

import com.hs.model.User;

public interface UserService {

	List<User> getAllUsers();

	User getUserById(String userId);

	User addNewUser(User user);
}
