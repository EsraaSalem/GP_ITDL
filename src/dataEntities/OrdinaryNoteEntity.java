package dataEntities;

import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class OrdinaryNoteEntity extends NoteEntity {
	
	@Override
	public String toString() {
		return "OrdinaryNoteEntity [noteContent=" + noteContent + ", servernoteID=" + servernoteID + ", userID="
				+ userID + ", creationDate=" + creationDate + ", isDone=" + isDone + ", isTextCategorized="
				+ isTextCategorized + ", noteType=" + noteType + "]";
	}

	private String noteContent;

	public OrdinaryNoteEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrdinaryNoteEntity(String noteID, String userID, Timestamp creationDate, boolean isDone,
			boolean isTextCategorized, String noteType, String noteContent) {
		
		super(noteID, userID, creationDate, isDone, isTextCategorized, noteType);
		this.noteContent = noteContent;
	}

	

	public String getNoteContent() {
		return noteContent;
	}

	public void setNoteContent(String noteContent) {
		this.noteContent = noteContent;
	}
	 
	
	
	
}
