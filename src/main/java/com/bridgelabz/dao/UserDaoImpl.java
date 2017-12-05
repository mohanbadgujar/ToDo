package com.bridgelabz.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.User;

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

	@Override
	public User getUserByEmailId(String email) {

		Session session = sessionFactory.openSession();
		Query<?> query = session.createQuery("from User where email = :email");
		query.setParameter("email", email);
		User updateuser = (User) query.uniqueResult();
		session.close();
		return updateuser;
	}

	@Override
	public User saveUser(User user) throws HibernateException {

		Session session = sessionFactory.getCurrentSession();
		session.save(user);
		return user;
	}

	@Override
	public User getUserById(int id) {

		Session session = sessionFactory.getCurrentSession();
		// Query query = session.createQuery("from User where id = :id");
		// query.setParameter("id", id);
		User updateuser = (User) session.get(User.class, id);
		// session.close();
		return updateuser;

	}

	@Override
	public boolean updateUser(User user) {
		Session session = sessionFactory.getCurrentSession();

		/*String queryString = "update User set active=:active where id=:id";
		Query query = session.createQuery(queryString);
		query.setParameter("active", true);
		query.setParameter("id", user.getId());
		int res = query.executeUpdate();*/

		session.update(user);

		return true;
	}

}
