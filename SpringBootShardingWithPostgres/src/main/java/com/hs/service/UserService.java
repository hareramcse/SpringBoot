package com.hs.service;

import com.hs.configuration.DBContextHolder;
import com.hs.configuration.DBTypeEnum;
import com.hs.model.UserRequest;
import com.hs.repository.User;
import com.hs.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> getUser(String name) {
		setContext(name);
		return (List<User>) userRepository.findByFirstName(name);
	}

	public List<User> getAllUsers() {
		DBContextHolder.setCurrentDb(DBTypeEnum.SHARD1);
		List<User> allUsers = new ArrayList<>((Collection<? extends User>) userRepository.findAll());
		DBContextHolder.setCurrentDb(DBTypeEnum.SHARD2);
		allUsers.addAll((Collection<? extends User>) userRepository.findAll());
		return allUsers;
	}

	public void addUser(UserRequest request) {
		setContext(request.getFirstName());
		saveInShard(request);

	}

	private void saveInShard(UserRequest request) {
		User user = User.builder().firstName(request.getFirstName()).lastName(request.getLastName())
				.age(request.getAge()).build();
		userRepository.save(user);
	}

	private void setContext(String name) {
		char firstChar = name.charAt(0);
		if ((firstChar >= 'A' || firstChar >= 'a') && (firstChar <= 'M' || firstChar <= 'm')) {
			DBContextHolder.setCurrentDb(DBTypeEnum.SHARD1);
		} else if ((firstChar >= 'N' || firstChar >= 'm') && (firstChar <= 'S' || firstChar <= 's')) {
			DBContextHolder.setCurrentDb(DBTypeEnum.SHARD2);
		} else {
			DBContextHolder.setCurrentDb(DBTypeEnum.SHARD3);
		}
	}
}