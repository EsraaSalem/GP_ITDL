package dataEntities;

import java.sql.Timestamp;

public abstract class NoteEntity {

	protected String servernoteID;
	protected String localnoteID;
	protected String userID;
	protected Timestamp creationDate;
	protected boolean isDone;
	protected boolean isdeleted;
	protected boolean isTextCategorized;
	protected String noteType;
	protected String Prority;
	protected boolean isAdded;
	protected boolean isUpdated;
	protected boolean isActive;
	/**
	 * @return the isActive
	 */
	public boolean isActive() {
		return isActive;
	}

	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the isdeleted
	 */
	public boolean isIsdeleted() {
		return isdeleted;
	}

	public NoteEntity() {
		super();
	}
	
	public NoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone, boolean isTextCategorized,
			String noteType ) {
		super();
		
		this.servernoteID = noteID;
		this.userID = userID;
		this.creationDate = creationDate;
		this.isDone = isDone;
		this.isTextCategorized = isTextCategorized;
		this.noteType = noteType;
	}
	
	/**
	 * @return the prority
	 */
	public String getPrority() {
		return Prority;
	}

	/**
	 * @param prority the prority to set
	 */
	public void setPrority(String prority) {
		Prority = prority;
	}

	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public Timestamp getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isDone() {
		return isDone;
	}
	

	/**
	 * @return the isdeleted
	 */
	public boolean Isdeleted() {
		return isdeleted;
	}

	/**
	 * @param isdeleted the isdeleted to set
	 */
	public void setIsdeleted(boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	/**
	 * @return the isAdded
	 */
	public boolean isAdded() {
		return isAdded;
	}

	/**
	 * @param isAdded the isAdded to set
	 */
	public void setAdded(boolean isAdded) {
		this.isAdded = isAdded;
	}

	/**
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * @return the servernoteID
	 */
	public String getServernoteID() {
		return servernoteID;
	}

	/**
	 * @param servernoteID the servernoteID to set
	 */
	public void setServernoteID(String servernoteID) {
		this.servernoteID = servernoteID;
	}

	/**
	 * @return the localnoteID
	 */
	public String getLocalnoteID() {
		return localnoteID;
	}

	/**
	 * @param localnoteID the localnoteID to set
	 */
	public void setLocalnoteID(String localnoteID) {
		this.localnoteID = localnoteID;
	}

	/**
	 * @param isUpdated the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	public boolean isTextCategorized() {
		return isTextCategorized;
	}
	public void setTextCategorized(boolean isTextCategorized) {
		this.isTextCategorized = isTextCategorized;
	}
	public String getNoteType() {
		return noteType;
	}
	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}
	

	

}
