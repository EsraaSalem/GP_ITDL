package CRUD_oprations_Serivces;

import java.sql.Timestamp;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import Model.RankingInputsModel;
import Model.NoteModel;
import dataEntities.DeadlineNoteEntity;
import dataEntities.MeetingNoteEntity;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;
import dataEntities.ShoppingNoteEntity;
import note_crud_operation_logic_classes.NoteCRUDOperations;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)

public class NotesServices {

	
	
	@POST
	@Path("/getAllNotesService")
	public String getAllNotes(@FormParam("userID") String userID) {
		
		NoteCRUDOperations noteManager = new NoteCRUDOperations();
		
		JSONArray allNotes =noteManager.getAllNotes(userID);
		return allNotes.toString();
	}
	
	
	

	@POST
	@Path("/addDeadLineNoteService")
	public String addDeadLineNote(
			@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, 
			@FormParam("deadLineDate") String deadLineDate,
			@FormParam("creationDate") String creationDate, 
			@FormParam("isDone") String isDone,
			@FormParam("isTextCategorized") String isTextCategorized, 
			@FormParam("noteType") String noteType,
			@FormParam("userID") String userID) {

		DeadlineNoteEntity deadLineNoteEntityObj = new DeadlineNoteEntity(Integer.parseInt(progressPercentage),
				deadLineTitle, Timestamp.valueOf(deadLineDate), "", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType);
		NoteModel nm = new NoteModel();
		boolean result = nm.adddeadLineNote(deadLineNoteEntityObj);
		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");
		return object.toString();
		// return deadLineNoteEntityObj.toString();
	}

	@POST
	@Path("/addShoppingNoteService")
	public String addShoppingNote(@FormParam("productToBuy") String productToBuy,
			@FormParam("productCategory") String productCategory, 
			@FormParam("creationDate") String creationDate,
			@FormParam("isDone") String isDone, 
			@FormParam("isTextCategorized") String isTextCategorized,
			@FormParam("noteType") String noteType, 
			@FormParam("userID") String userID) {

		ShoppingNoteEntity shoppingNoteEntityObj = new ShoppingNoteEntity("", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType, productToBuy, productCategory);

		NoteModel nm = new NoteModel();
		boolean result = nm.addShoppingNote(shoppingNoteEntityObj);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");
		return object.toString();
	}

	@POST
	@Path("/addMeetingNoteService")
	public String addMeetingNote(
			@FormParam("creationDate") String creationDate, 
			@FormParam("isDone") String isDone,
			@FormParam("meetingTitle") String meetingTitle, 
			@FormParam("isTextCategorized") String isTextCategorized,
			@FormParam("noteType") String noteType, 
			@FormParam("userID") String userID,
			@FormParam("meetingAgenda") String meetingAgenda, 
			@FormParam("meetingPlace") String meetingPlace,
			@FormParam("meetingNoteDate") String meetingNoteDate,
			@FormParam("estimatedTransportTime") String estimatedTransportTime) {

		MeetingNoteEntity meetingNoteEntityObj = new
				MeetingNoteEntity(java.sql.Timestamp.valueOf(meetingNoteDate),
				java.sql.Time.valueOf(estimatedTransportTime), meetingTitle, meetingPlace,
				meetingAgenda, "", userID,
				java.sql.Timestamp.valueOf(creationDate), Boolean.valueOf(isDone),
				Boolean.valueOf(isTextCategorized),
				noteType);

		NoteModel nm = new NoteModel();
		boolean result = nm.addMeetingNote(meetingNoteEntityObj);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");
		
		

		return object.toString();
	}

	@POST
	@Path("/addOrdinaryNoteService")
	public String addOrdinaryNote(
			@FormParam("ordinaryNoteContent") String ordinaryNoteContent,
			@FormParam("creationDate") String creationDate, 
			@FormParam("isDone") String isDone,
			@FormParam("isTextCategorized") String isTextCategorized, 
			@FormParam("noteType") String noteType,
			@FormParam("userID") String userID) {

		OrdinaryNoteEntity ordinaryNoteEntityObj = new OrdinaryNoteEntity("", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType, ordinaryNoteContent);
		NoteModel nm = new NoteModel();
		boolean result = nm.addOrdinaryNote(ordinaryNoteEntityObj);
		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");
		return object.toString();
	}


}
