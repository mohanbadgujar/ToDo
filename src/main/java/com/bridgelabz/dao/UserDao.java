package com.bridgelabz.dao;

import org.hibernate.HibernateException;

import com.bridgelabz.model.Login;
import com.bridgelabz.model.User;

public interface UserDao {

	User saveUser(User user) throws HibernateException;

	/*boolean authenticateUser(User user);*/

	User getUserByEmailId(String email);

	User getUserById(int id);

	boolean updateUser(User user);

	/*User checkUserAlreadyExits(String email);*/

}
