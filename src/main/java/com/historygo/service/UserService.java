package com.historygo.service;

import com.historygo.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
