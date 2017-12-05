package com.bridgelabz.dao;

import java.util.Set;
import com.bridgelabz.model.Note;

public interface NoteDao {

	void createNote(Note note);

	void updateNote(Note note);

	void deleteNote(Note note);
	
	Note getNoteById(int id);

	Set<Note> getNotes(int userId);

	void archive(Note note);

}
