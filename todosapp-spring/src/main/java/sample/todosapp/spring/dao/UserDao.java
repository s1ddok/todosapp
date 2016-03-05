package sample.todosapp.spring.dao;

import sample.todosapp.spring.domain.User;

public interface UserDao {

	User findById(int id);
	
	User findBySSO(String sso);
	
}

