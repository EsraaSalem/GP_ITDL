package recomm_reranking_algorithm_logic_classes;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Vector;

import javax.ws.rs.FormParam;

import org.apache.tools.ant.types.resources.Last;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.sun.org.apache.regexp.internal.recompile;

import Model.NoteModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.DeadlineNoteEntity;
import dataEntities.InputType;
import dataEntities.MeetingNoteEntity;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;
import dataEntities.ShoppingNoteEntity;
import dataEntities.UserEntity;
import note_crud_operation_logic_classes.NoteCRUDOperations;

public class NoteComponent {

	private NoteModel noteModel_DB;
	UpdatePreferenceLastDate lastDate;

	public NoteComponent() {
		noteModel_DB = new NoteModel();
		lastDate = new UpdatePreferenceLastDate();
	}

	public Vector<NoteEntity> getAllNotesServiceTEST(@FormParam("userID") String userID) throws ParseException {

		NoteCRUDOperations noteManager = new NoteCRUDOperations();
		Vector<NoteEntity> allNotes = noteManager.getAllNotesForRerankning(userID);
		return allNotes;
	}

	public Vector<InputType> getAllNotesWithinRange(Vector<NoteEntity> allNotes, Timestamp lastUpdateDate) {
		java.util.Date date = new java.util.Date();
		Timestamp todayDate = new Timestamp(date.getTime());
		Vector<InputType> allInputs = new Vector<InputType>();
		for (int i = 0; i < allNotes.size(); i++) {
			InputType input = new InputType();

			Timestamp noteCreationDate = allNotes.get(i).getCreationDate();
			if (lastDate.isWithinRange(todayDate, lastUpdateDate, noteCreationDate)) {
				try {
					input = lastDate.buildNoteInputType(allNotes.get(i));
				} catch (JSONException | ParseException e) {
					// TODO Auto-generated catch block

				}
				allInputs.add(input);

			}
		}
		return allInputs;

	}

	public Vector<InputType> getAllNotesWithoutRange(Vector<NoteEntity> allNotes) throws JSONException, ParseException {
		Vector<InputType> allInputs = new Vector<InputType>();
		for (int i = 0; i < allNotes.size(); i++) {
			InputType input = new InputType();
			input = lastDate.buildNoteInputType(allNotes.get(i));
			allInputs.add(input);
		}
		return allInputs;
	}

	public Vector<InputType> extractNotes(String userID, Timestamp lastUpdateDate)
			throws ParseException, JSONException {

		// Timestamp lastUpdateDate =lastDate.getLastUpdatePreferneceDate();

		Vector<NoteEntity> allNotes = getAllNotesServiceTEST(userID);

		Vector<InputType> allInputs = new Vector<InputType>();
		if (allNotes.size() > 0) {
			if (lastUpdateDate == null) {
				allInputs = getAllNotesWithoutRange(allNotes);
			} else {
				allInputs = getAllNotesWithinRange(allNotes, lastUpdateDate);
			}

			if (allInputs.size() == 0) {
				System.out.println("There is NO Notes");
			}
		}

		return allInputs;
	}

}
