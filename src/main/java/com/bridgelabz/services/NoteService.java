package com.bridgelabz.services;

import java.util.Date;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.Note;

public class NoteService {
	
	@Autowired
	private NoteDao noteDao;
	
	@Autowired
	private UserDao userDao;
	
	Date modified = new Date();

	@Transactional
	public void createNote(Note note, int userId) {
		
		note.setUser(userDao.getUserById(userId));
		Date createDate = new Date();
		note.setCreatedAt(createDate);
		note.setModifiedAt(createDate);
		
		noteDao.createNote(note);
	}

	@Transactional
	public boolean updateNote(Note note, int userId) {
		Note oldNote = noteDao.getNoteById(note.getNoteId());
		
		if (userId == oldNote.getUser().getId()) {
		
			oldNote.setModifiedAt(modified);
			oldNote.setTitle(note.getTitle());
			oldNote.setDescription(note.getDescription());
			
			noteDao.updateNote(oldNote);
			return true;
		} else {
			return false;
		}
	}

	@Transactional
	public void deleteNote(int noteId) {
		Note note = new Note();
		note.setNoteId(noteId);
		noteDao.deleteNote(note);
	}

	public Set<Note> getNotes(int id) {
		
		return noteDao.getNotes(id);
	}
	
	@Transactional
	public void archive(int id) {
		Note note = noteDao.getNoteById(id);
		note.setNoteId(id);
		note.setArchive(true);
		note.setModifiedAt(modified);
		noteDao.archive(note);
	}

}
