package dataEntities;

import java.sql.Time;
import java.sql.Timestamp;

import org.json.simple.JSONObject;

public class DeadlineNoteEntity extends NoteEntity {

	
	private int progressPercentage;
	private String deadLineTitle;
	private Timestamp deadLineDate;

	public int getProgressPercentage() {
		return progressPercentage;
	}

	

	public void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}

	public String getDeadLineTitle() {
		return deadLineTitle;
	}

	public void setDeadLineTitle(String deadLineTitle) {
		this.deadLineTitle = deadLineTitle;
	}

	public Timestamp getDeadLineDate() {
		return deadLineDate;
	}

	@Override
	public String toString() {
		return "DeadlineNoteEntity [progressPercentage=" + progressPercentage + ", deadLineTitle=" + deadLineTitle
				+ ", deadLineDate=" + deadLineDate + ", servernoteID=" + servernoteID + ", userID=" + userID
				+ ", creationDate=" + creationDate + ", isDone=" + isDone + ", isTextCategorized=" + isTextCategorized
				+ ", noteType=" + noteType + "]";
	}



	public void setDeadLineDate(Timestamp deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	public DeadlineNoteEntity() {
		super();
	}

	public DeadlineNoteEntity(
			 
			int progressPercentage, 
			String deadLineTitle,
			Timestamp deadLineDate, 
			String noteID, 
			String userID, 
			Timestamp creationDate, 
			boolean isDone,
			boolean isTextCategorized, 
			String noteType,
			String priority) {
		super(noteID, userID, creationDate, isDone, isTextCategorized, noteType,priority);
		
		this.progressPercentage = progressPercentage;
		this.deadLineTitle = deadLineTitle;
		this.deadLineDate = deadLineDate;
	}
	
	

}
