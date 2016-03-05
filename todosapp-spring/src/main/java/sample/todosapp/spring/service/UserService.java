package sample.todosapp.spring.service;

import sample.todosapp.spring.domain.User;

public interface UserService {

	User findById(int id);
	
	User findBySso(String sso);
	
}