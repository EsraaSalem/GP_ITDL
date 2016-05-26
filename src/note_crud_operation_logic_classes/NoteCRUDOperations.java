package note_crud_operation_logic_classes;

import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import Model.NoteModel;
import Model.NoteParser;
import dataEntities.NoteEntity;

public class NoteCRUDOperations {
	final private String shopping = "Shopping";
	final private String ordinary = "Ordinary";
	final private String deadline = "DeadLine";
	final private String meeting = "Meeting";
	private NoteParser noteParser;
	
	public NoteCRUDOperations()
	{
		noteParser = new NoteParser();
	}
	
	public JSONArray getAllNotes(String userID) {

		NoteModel nm = new NoteModel();
		JSONArray allNotes = nm.getAllNotes(userID);
		return allNotes;
	}
	
	public Vector<NoteEntity> getAllNotesForRerankning(String userID) throws ParseException {

		
		NoteModel nm = new NoteModel();
		JSONArray allNotes = nm.getAllNotes(userID);
		
		Vector<NoteEntity> notes = new Vector<NoteEntity>();

		JSONParser parser = new JSONParser();

		for (int i = 0; i < allNotes.size(); i++) {
			JSONObject object;
			object = (JSONObject) allNotes.get(i);
			System.out.println(object.toJSONString());
			if (object.containsKey(meeting)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(meeting).toString());
				notes.add(noteParser.convertJsonObjToMeetingNoteObj(object1));

			} else if (object.containsKey(ordinary)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(ordinary).toString());
				notes.add(noteParser.convertJsonObjToOrdinaryNoteObj(object1));

			} else if (object.containsKey(shopping)) {
				System.out.println(shopping);
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(shopping).toString());
				notes.add(noteParser.convertJsonObjToShoppingNoteObj(object1));

			} else if (object.containsKey(deadline)) {
				System.out.println(deadline);
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(deadline).toString());
				notes.add(noteParser.convertJsonObjToDeadLineNoteObj(object1));
			}
		}

		System.out.println("NOTES "+notes.toString());
		
		return notes;
	}
	
	

}
