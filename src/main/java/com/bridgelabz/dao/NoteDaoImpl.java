package com.bridgelabz.dao;

import java.util.Set;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

public class NoteDaoImpl implements NoteDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void createNote(Note note) {
		
		Session session = sessionFactory.getCurrentSession();
		session.save(note);
	}

	@Override
	public void updateNote(Note note) {

		Session session = sessionFactory.getCurrentSession();
		session.update(note);
	}

	@Override
	public void deleteNote(Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(note);
	}

	@Override
	public Note getNoteById(int id) {
		Session session = sessionFactory.getCurrentSession();
		return session.get(Note.class, id);
	}

	@Override
	public Set<Note> getNotes(int userId) {
		Session session=sessionFactory.openSession();
		User user = session.get(User.class,userId);
		Set<Note> notes = user.getNotes();
		notes.size();
		session.close();
		return notes;
	}

	@Override
	public void archive(Note note) {
		Session session = sessionFactory.getCurrentSession();
		session.update(note);
	}
}
