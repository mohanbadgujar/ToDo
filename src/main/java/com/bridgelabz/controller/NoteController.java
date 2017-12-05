package com.bridgelabz.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.jsonResponse.Response;
import com.bridgelabz.model.Note;
import com.bridgelabz.services.NoteService;

@RestController
@RequestMapping(value = "/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	Response response = new Response();

	@RequestMapping(value = "/createnote", method = RequestMethod.POST)
	public ResponseEntity<Response> createNote(@RequestBody Note note, @RequestAttribute int userId) {
		try {
			noteService.createNote(note, userId);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(-1);
			response.setMsg("ERROR : " + e.getMessage() + " CAUSE : " + e.getCause());
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		response.setStatus(1);
		response.setMsg("Note saved");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/updatenote", method = RequestMethod.PUT)
	public ResponseEntity<Response> updateNote(@RequestBody Note note, @RequestAttribute int userId) {
		try {
			noteService.updateNote(note, userId);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(-1);
			response.setMsg("Note could not be updated");
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		response.setStatus(1);
		response.setMsg("Note Updated");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/deletenote/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Response> deleteNote(@PathVariable("id") int id) {
		try {
			noteService.deleteNote(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(-1);
			response.setMsg("Note could not be saved");
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		response.setStatus(1);
		response.setMsg("Note deleted");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/allnotes", method = RequestMethod.GET)
	public ResponseEntity<Set<Note>> getNotes(@RequestAttribute("userId") int userId) {
		Set<Note> notes = noteService.getNotes(userId);
		return ResponseEntity.ok(notes);
	}

	@RequestMapping(value = "/archive/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Response> archive(@PathVariable("id") int id) {
		try {
			noteService.archive(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(-1);
			response.setMsg("Note could not be archive");
			return new ResponseEntity<Response>(response, HttpStatus.CONFLICT);
		}
		response.setStatus(1);
		response.setMsg("Note archived");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
