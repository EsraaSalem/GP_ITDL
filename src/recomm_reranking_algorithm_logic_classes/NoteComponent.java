package recomm_reranking_algorithm_logic_classes;

import java.util.Date;
import java.util.Vector;

import javax.ws.rs.FormParam;

import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

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

public class NoteComponent extends InputSources {

	private NoteModel noteModel_DB;
	final private String shopping = "Shopping";
	final private String ordinary = "Ordinary";
	final private String deadline = "DeadLine";
	final private String meeting = "Meeting";
	public NoteComponent()
	{
		noteModel_DB = new NoteModel();
	}
	public Vector<NoteEntity> getAllNotesServiceTEST(@FormParam("userID") String userID) throws ParseException {

		NoteCRUDOperations noteManager = new NoteCRUDOperations();
		Vector<NoteEntity> allNotes = noteManager.getAllNotesForRerankning(userID);
		return allNotes;
	}

	@Override
	public void extractInput() {
		// TODO Auto-generated method stub

		try {
			Vector<NoteEntity> allNotes = getAllNotesServiceTEST(UserEntity.getUserInstance().getUserDB_ID());
			
			for (int i = 0; i < allNotes.size(); i++) {
				
				TextCategorization textCategorization = new TextCategorization();				
				String noteText = "";
				
				Date noteCreationDate = new Date(allNotes.get(i).getCreationDate().getTime());;
				if(allNotes.get(i).getNoteType().equals(ordinary))
				{
					OrdinaryNoteEntity obj = new OrdinaryNoteEntity();
					obj = (OrdinaryNoteEntity)allNotes.get(i);
					noteText = obj.getNoteContent();
				}
				else if(allNotes.get(i).getNoteType().equals(meeting))
				{
					MeetingNoteEntity obj = new MeetingNoteEntity();
					obj = (MeetingNoteEntity) allNotes.get(i);
					noteText = obj.getMeetingAgenda()+" "+obj.getMeetingTitle();
				}
				else if(allNotes.get(i).getNoteType().equals(deadline))
				{
					DeadlineNoteEntity obj = new DeadlineNoteEntity();
					obj = (DeadlineNoteEntity) allNotes.get(i);
					noteText = obj.getDeadLineTitle();
				}
				else if(allNotes.get(i).getNoteType().equals(shopping))
				{
					ShoppingNoteEntity obj = new ShoppingNoteEntity();
					obj = (ShoppingNoteEntity)allNotes.get(i);
					noteText = obj.getProductCategory() + " "+obj.getProductToBuy();
				}
				
				String textCategory = textCategorization.callTextCategoryAPI(noteText);
				
				noteModel_DB.noteIsTextCategorized(allNotes.get(i).getNoteID());
				
				allInputs.add(new InputType(noteText, "note", noteCreationDate,textCategory));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
