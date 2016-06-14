package recomm_reranking_algorithm_logic_classes;

import java.sql.Timestamp;

import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import Model.NoteModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.DeadlineNoteEntity;
import dataEntities.InputType;
import dataEntities.MeetingNoteEntity;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;
import dataEntities.ShoppingNoteEntity;

public class UpdatePreferenceLastDate {
	final private String shopping = "Shopping";
	final private String ordinary = "Ordinary";
	final private String deadline = "DeadLine";
	final private String meeting = "Meeting";
	private NoteModel noteModel_DB;

	public UpdatePreferenceLastDate() {
		noteModel_DB = new NoteModel();
	}

	public InputType buildNoteInputType(NoteEntity note) throws JSONException, ParseException {
		InputType input = new InputType();

		TextCategorization textCategorization = new TextCategorization();
		String noteText = "";

		Timestamp noteCreationDate = note.getCreationDate();

		if (note.getNoteType().equals(ordinary)) {
			OrdinaryNoteEntity obj = new OrdinaryNoteEntity();
			obj = (OrdinaryNoteEntity) note;
			noteText = obj.getNoteContent();
		} else if (note.getNoteType().equals(meeting)) {
			MeetingNoteEntity obj = new MeetingNoteEntity();
			obj = (MeetingNoteEntity) note;
			noteText = obj.getMeetingAgenda() + " " + obj.getMeetingTitle();
		} else if (note.getNoteType().equals(deadline)) {
			DeadlineNoteEntity obj = new DeadlineNoteEntity();
			obj = (DeadlineNoteEntity) note;
			noteText = obj.getDeadLineTitle();
		} else if (note.getNoteType().equals(shopping)) {
			ShoppingNoteEntity obj = new ShoppingNoteEntity();
			obj = (ShoppingNoteEntity) note;
			noteText = obj.getProductCategory() + " " + obj.getProductToBuy();
		}

		String textCategory = textCategorization.callTextCategoryAPI(noteText);

		noteModel_DB.noteIsTextCategorized(note.getNoteID());
		input.setCreationDate(noteCreationDate);
		input.setSourcetype("note");
		input.setTextCategory(textCategory);
		input.setText(noteText);

		return input;
	}

	public Timestamp addLastUpdatePreferencesDate(String userID) {
		resetLastUpdatePrferencesDateTable();
		java.util.Date date = new java.util.Date();
		Timestamp todayDate = new Timestamp(date.getTime());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		try {
			Entity entity = new Entity("lastUpdatePrferencesDate");

			entity.setProperty("lastdate", todayDate.toString());
			entity.setProperty("userID", userID);
			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}

		return todayDate;
	}

	public void resetLastUpdatePrferencesDateTable() {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("lastUpdatePrferencesDate");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			datastore.delete(entity.getKey());
		}

	}

	public Timestamp getLastUpdatePreferneceDate(String userID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Timestamp timeStamp = null;
		Query gaeQuery = new Query("lastUpdatePrferencesDate");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {
			String userIDDB = entity.getProperty("userID").toString().trim();
			if (userIDDB.equals(userID)) {
				System.out.println(entity.toString());
				timeStamp = java.sql.Timestamp.valueOf(entity.getProperty("lastdate").toString());
				break;
			}

		}
		// if(timeStamp == null)
		// {
		// return addLastUpdatePreferencesDate();
		// }
		return timeStamp;
	}

	public boolean isWithinRange(Timestamp todayDate, Timestamp lastUpdate, Timestamp creationDate) {
		int targetAbovelowerBound = creationDate.compareTo(lastUpdate); // should
		int targetbelowUbberBound = creationDate.compareTo(todayDate); // should
		if (targetAbovelowerBound == 1 && targetbelowUbberBound == -1) {
			return true;
		}
		return false;
	}
}
