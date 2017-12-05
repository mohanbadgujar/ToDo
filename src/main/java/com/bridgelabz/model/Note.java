package com.bridgelabz.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
@DynamicUpdate(value = true)
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "notegen")
	@GenericGenerator(name = "notegen", strategy = "native")
	private int noteId;

	private String title;

	private String description;

	private Date createdAt;

	private Date modifiedAt;
	
	private boolean archive;
	
	private boolean pin;
	
	private boolean trash;
	
	private boolean emptyTrash;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public boolean isArchive() {
		return archive;
	}

	public void setArchive(boolean archive) {
		this.archive = archive;
	}

	public boolean isPin() {
		return pin;
	}

	public void setPin(boolean pin) {
		this.pin = pin;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

	public boolean isEmptyTrash() {
		return emptyTrash;
	}

	public void setEmptyTrash(boolean emptyTrash) {
		this.emptyTrash = emptyTrash;
	}

}
