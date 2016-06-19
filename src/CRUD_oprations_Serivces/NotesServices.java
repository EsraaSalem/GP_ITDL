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
import org.json.simple.parser.JSONParser;
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

	
	

	private OrdinaryNoteEntity convertJsonObjToOrdinaryNoteObj(JSONObject jsonnote) {
		String temp =jsonnote.get("Ordinary").toString();
		JSONObject jsonObj = null;
	    JSONParser parser = new JSONParser();
	    try {
			System.out.println("temp="+temp);

			jsonObj = (JSONObject) parser.parse(temp);
			System.out.println("noteContent=="+jsonObj.get("noteContent"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    OrdinaryNoteEntity note = new OrdinaryNoteEntity(jsonObj.get("serverNoteId").toString(), jsonObj.get("userId").toString(),
				Timestamp.valueOf(jsonObj.get("noteDateCreation").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
				Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString(),
				jsonObj.get("noteContent").toString());
	    note .setAdded(Boolean.valueOf(jsonObj.get("isAdded").toString()));
	    note .setUpdated(Boolean.valueOf(jsonObj.get("isUpdated").toString()));
	    note .setIsdeleted(Boolean.valueOf(jsonObj.get("isDeleted").toString()));
	    note .setLocalnoteID(jsonObj.get("localNoteId").toString());

		return note;

	}
	private ShoppingNoteEntity convertJsonObjToShoppingNoteObj(JSONObject jsonnote) {
		String temp =jsonnote.get("Shopping").toString();
		JSONObject jsonObj = null;
	    JSONParser parser = new JSONParser();
	    try {
			System.out.println("temp="+temp);

			jsonObj = (JSONObject) parser.parse(temp);
			System.out.println("productToBuy=="+jsonObj.get("productToBuy"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ShoppingNoteEntity note =new ShoppingNoteEntity(String.valueOf(jsonObj.get("serverNoteId")), 
				jsonObj.get("userId").toString(),
				java.sql.Timestamp.valueOf(jsonObj.get("noteDateCreation").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
				Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString(),
				jsonObj.get("productToBuy").toString(), jsonObj.get("productCategory").toString());
		note.setPrority(jsonObj.get("notePriority").toString());
		note .setAdded(Boolean.valueOf(jsonObj.get("isAdded").toString()));
	    note .setUpdated(Boolean.valueOf(jsonObj.get("isUpdated").toString()));
	    note .setIsdeleted(Boolean.valueOf(jsonObj.get("isDeleted").toString()));
	    note .setLocalnoteID(jsonObj.get("localNoteId").toString());

		return note;

	}
	
	private MeetingNoteEntity convertJsonObjToMeetingNoteObj(JSONObject jsonnote ) {
		
		String temp =jsonnote.get("Meeting").toString();
		JSONObject jsonObj = null;
	    JSONParser parser = new JSONParser();
	    try {
			System.out.println("temp="+temp);

			 jsonObj = (JSONObject) parser.parse(temp);
			System.out.println("meetingAgenda=="+jsonObj.get("meetingAgenda"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

MeetingNoteEntity note=new MeetingNoteEntity(java.sql.Timestamp.valueOf(jsonObj.get("meetingNoteDate").toString()),
				java.sql.Time.valueOf(jsonObj.get("estimatedTransportTime").toString()),
				jsonObj.get("meetingTitle").toString(), jsonObj.get("meetingPlace").toString(),
				jsonObj.get("meetingAgenda").toString(), jsonObj.get("serverNoteId").toString(),
				jsonObj.get("userId").toString(), java.sql.Timestamp.valueOf(jsonObj.get("noteDateCreation").toString()),
				Boolean.valueOf(jsonObj.get("isDone").toString()),
	    Boolean.valueOf(jsonObj.get("isTextCategorized").toString()), jsonObj.get("noteType").toString());
note.setPrority(jsonObj.get("notePriority").toString());
note .setAdded(Boolean.valueOf(jsonObj.get("isAdded").toString()));
note .setUpdated(Boolean.valueOf(jsonObj.get("isUpdated").toString()));
note .setIsdeleted(Boolean.valueOf(jsonObj.get("isDeleted").toString()));
note .setLocalnoteID(jsonObj.get("localNoteId").toString());

return note;
	}
	
	private  DeadlineNoteEntity convertJsonObjToDeadLineNoteObj(JSONObject jsonObj) {

		String temp =jsonObj.get("Deadline").toString();
		JSONObject object1 = null;
	    JSONParser parser = new JSONParser();
	    try {
			System.out.println("temp="+temp);

			object1 = (JSONObject) parser.parse(temp);
			System.out.println("deadLineTitle=="+object1.get("deadLineTitle"));

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DeadlineNoteEntity deadLineNoteObj = new DeadlineNoteEntity(
				Integer.parseInt(object1.get("progressPercentage").toString()), object1.get("deadLineTitle").toString(),
				java.sql.Timestamp.valueOf(object1.get("deadLineDate").toString()), object1.get("serverNoteId").toString(),
				object1.get("userId").toString(), java.sql.Timestamp.valueOf(object1.get("noteDateCreation").toString()),
				Boolean.valueOf(object1.get("isDone").toString()),
				Boolean.valueOf(object1.get("isTextCategorized").toString()), object1.get("noteType").toString());
			deadLineNoteObj.setPrority(object1.get("notePriority").toString());
			deadLineNoteObj.setAdded(Boolean.valueOf(object1.get("isAdded").toString()));
			deadLineNoteObj.setUpdated(Boolean.valueOf(object1.get("isUpdated").toString()));
			deadLineNoteObj.setIsdeleted(Boolean.valueOf(object1.get("isDeleted").toString()));
			deadLineNoteObj.setLocalnoteID(object1.get("localNoteId").toString());

		return deadLineNoteObj;
	}
	@POST
	@Path("/synchroinzationService")
	public String synchroinzationService(@FormParam("NoteList") String Notes){
		JSONArray resarray = new JSONArray();
		boolean testupdate = false,testDelete=false ; 
		long testAdded;
		 JSONParser parser1 = new JSONParser();
		 JSONArray NotesList = null ;
		NoteModel nm = new NoteModel();
		try {
			 NotesList = (JSONArray) parser1.parse(Notes);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			 String ordinaryNote = "Ordinary";
		     String meetingNote = "Meeting";
		     String deadlineNote = "Deadline";
		     String shoppingNote = "Shopping";
		     
		for(int i=0;i<NotesList.size();i++){
			 JSONParser parser = new JSONParser();
			 JSONObject resobject = new JSONObject();
			 //NoteEntity note = StringToObjectNote(NotesList.get(i)); 
			 JSONObject object1;
			 String noteid = "";
			 String servernoteid = "";
			 String syncType = "";
			try {
				object1 = (JSONObject) parser.parse(NotesList.get(i).toString());
				if (object1.containsKey(ordinaryNote)){
					OrdinaryNoteEntity onote= new OrdinaryNoteEntity();
					onote=convertJsonObjToOrdinaryNoteObj(object1);
					System.out.println("Is add="+onote.isAdded());
					System.out.println("Is deleted="+onote.isIsdeleted());
					System.out.println("Is isUpdated="+onote.isUpdated());
					System.out.println("getServernoteID"+onote.getServernoteID());

					System.out.println("________________");
					if(!onote.isAdded()){
						testAdded =nm.addOrdinaryNote(onote);
						syncType="Added";
						servernoteid=String.valueOf(testAdded);
						noteid=onote.getLocalnoteID();
					 }
					else if (onote.isUpdated()&&onote.isAdded()){
						testupdate =nm.saveOrdinaryNoteUpdate(onote.getServernoteID(), onote.getNoteContent());
						syncType="Updated";
						noteid=onote.getLocalnoteID();
					}
					else if (onote.Isdeleted()&&onote.isAdded()){
						testDelete =nm.noteIsDone(onote.getServernoteID());
						syncType="Delete";
						noteid=onote.getLocalnoteID();
					}
				}
				else if (object1.containsKey(deadlineNote)){
					DeadlineNoteEntity Dnote =new DeadlineNoteEntity();
					Dnote=convertJsonObjToDeadLineNoteObj(object1);
					if(!Dnote.isAdded()){
						testAdded =nm.adddeadLineNote(Dnote);
						syncType="Added";
						servernoteid=String.valueOf(testAdded);
						noteid=Dnote.getLocalnoteID();					 }
					else if (Dnote.isUpdated()&&Dnote.isAdded()){
						nm.saveDeadlineNoteUpdate(Dnote.getServernoteID(), Dnote.getDeadLineDate().toString(), Dnote.getDeadLineTitle(), String .valueOf(Dnote.getProgressPercentage()));
						syncType="Updated";
						noteid=Dnote.getLocalnoteID();
					}
					else if (Dnote.isUpdated()&&Dnote.isAdded()){
						testDelete =nm.noteIsDone(Dnote.getServernoteID());
						syncType="Delete";
						noteid=Dnote.getLocalnoteID();
					}
					
				}
				else if (object1.containsKey(shoppingNote)){
					ShoppingNoteEntity Shnote =new ShoppingNoteEntity() ;
					Shnote=convertJsonObjToShoppingNoteObj(object1);
					if(!Shnote.isAdded()){
						testAdded =nm.addShoppingNote(Shnote);
						syncType="Added";
						servernoteid=String.valueOf(testAdded);
						noteid=Shnote.getLocalnoteID();					 }
					else if (Shnote.isUpdated()&&Shnote.isAdded()){
						nm.saveShoppingNoteUpdate(Shnote.getServernoteID(),Shnote.getProductToBuy(), Shnote.getProductCategory());
						syncType="Updated";
						noteid=Shnote.getLocalnoteID();
					}
					else if (Shnote.isUpdated()&&Shnote.isAdded()){
						testDelete =nm.noteIsDone(Shnote.getServernoteID());
						syncType="Delete";
						noteid=Shnote.getLocalnoteID();
					}
					
					}
				else if (object1.containsKey(meetingNote)){
					MeetingNoteEntity Mnote= new MeetingNoteEntity();
					Mnote =convertJsonObjToMeetingNoteObj(object1);
					if(!Mnote.isAdded()){
						testAdded =nm.addMeetingNote(Mnote);
						syncType="Added";
						servernoteid=String.valueOf(testAdded);
						noteid=Mnote.getLocalnoteID();					 }
					else if (Mnote.isUpdated()&&Mnote.isAdded()){
						nm.saveMeetingNoteUpdate(Mnote.getServernoteID(),Mnote.getMeetingAgenda(),Mnote.getCreationDate().toString(), 
								Mnote.getMeetingPlace(), Mnote.getMeetingTitle(),String .valueOf(Mnote.getEstimatedTransportTime()));
						syncType="Updated";
						noteid=Mnote.getLocalnoteID();
					}
					else if (Mnote.isUpdated()&&Mnote.isAdded()){
						testDelete =nm.noteIsDone(Mnote.getServernoteID());
						syncType="Delete";
						noteid=Mnote.getLocalnoteID();
					}

				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resobject.put("syncType", syncType);
			resobject.put("noteid", noteid);
			resobject.put("servernoteid", servernoteid);

		  resarray.add(resobject);
		}
	return resarray.toString();
		//return "";
	}
	@POST
	@Path("/changeToNoteIsDoneService")
	public String changeNoteToIsDone(@FormParam("noteID") String noteID) {
		NoteModel nm = new NoteModel();
		boolean result = nm.noteIsDone(noteID);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");

		return object.toString();
	}
	
	
	@POST
	@Path("/changeToNoteIsTextCategorizedService")
	public String changeToNoteIsTextCategorized(@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		boolean result = nm.noteIsTextCategorized(noteID);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");

		return object.toString();
	}
	
	
	
	
	
	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/saveUpdateShoppingNoteService")
	public String saveShoppingNoteUpdates(
			@FormParam("noteID") String noteID,
			@FormParam("productToBuy") String productToBuy, 
			@FormParam("productCategory") String productCategory)
					throws org.json.simple.parser.ParseException {
		NoteModel nm = new NoteModel();
		boolean result = nm.saveShoppingNoteUpdate(noteID, productToBuy, productCategory);
		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");
		return object.toString();
	}

	
	
	
	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/updateCreationDateAndIsDoneService")
	public String updateCreationDateAndIsDone(
			@FormParam("noteID") String noteID)
					throws org.json.simple.parser.ParseException {
		NoteModel nm = new NoteModel();
		boolean result = nm.updateCreationDateAndIsDone(noteID);
		JSONObject obj = new JSONObject();
		if (result)
		{
			obj.put("Status","OK" );
			obj.put("noteID", noteID);
			return obj.toString();
		}
		else
		{
			obj.put("Status","Failed" );
			obj.put("noteID", noteID);
			return obj.toString();
		}
			
	}
	
	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/saveUpdateDeadlineNoteService")
	public String saveDeadlineNoteUpdates(
			@FormParam("noteID") String noteID,
			@FormParam("deadLineDate") String deadLineDate, 
			@FormParam("deadLineTitle") String deadLineTitle,
			@FormParam("progressPercentage") String progressPercentage) throws org.json.simple.parser.ParseException {

		NoteModel nm = new NoteModel();

		boolean result = nm.saveDeadlineNoteUpdate(noteID, deadLineDate, deadLineTitle, progressPercentage);
		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");

		return object.toString();

	}

	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/saveUpdateMeetingNoteService")
	public String saveMeetingNoteUpdates(
			@FormParam("noteID") String noteID,
			@FormParam("meetingTitle") String meetingTitle, 
			@FormParam("meetingAgenda") String meetingAgenda,
			@FormParam("meetingPlace") String meetingPlace, 
			@FormParam("meetingNoteDate") String meetingNoteDate,
			@FormParam("estimatedTransportTime") String estimatedTransportTime)
					throws org.json.simple.parser.ParseException {

		System.out.println("Services");

		NoteModel nm = new NoteModel();

		boolean result = nm.saveMeetingNoteUpdate(noteID, meetingAgenda, meetingNoteDate, meetingPlace, meetingTitle,
				estimatedTransportTime);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");

		return object.toString();

	}

	/////////////////////////////////////////////////////

	@SuppressWarnings({ "unchecked" })
	@POST
	@Path("/saveUpdateOrdinaryNoteService")
	public String saveOrdinaryNoteUpdates(
			@FormParam("noteID") String noteID,
			@FormParam("noteContent") String noteContent
			) throws org.json.simple.parser.ParseException {

		System.out.println("Services");

		NoteModel nm = new NoteModel();
		
	//	entity.getKey().getId()
		
		boolean result = nm.saveOrdinaryNoteUpdate(noteID, noteContent);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		
		else
			object.put("Status", "Fail");
		 	
		return object.toString();

	}

	/////////////////////////////////////////////////////
	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/getShoppingNoteToUpdate")
	public String getShoppingNoteToUpdate(
			@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		JSONObject object = new JSONObject();
		object = nm.getShoppingNoteToUpdate(noteID);
		if (object != null) {
			object.put("Status", "OK");
		} else
			object.put("Status", "Fail");

		return object.toString();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/getDeadlineNoteToUpdate")
	public String getDeadlineNoteToUpdate(@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		JSONObject object = new JSONObject();
		object = nm.geDeadlineNoteToUpdate(noteID);
		if (object != null) {
			object.put("Status", "OK");
		} else
			object.put("Status", "Fail");

		return object.toString();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/getMeetingNoteToUpdate")
	public String getMeetingNoteToUpdate(@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		JSONObject object = new JSONObject();
		object = nm.getMeetingNoteToUpdate(noteID);
		if (object != null) {
			object.put("Status", "OK");
		} else
			object.put("Status", "Fail");

		return object.toString();
	}

	@SuppressWarnings({ "unchecked", "null" })
	@POST
	@Path("/getOrdinaryNoteToUpdate")
	public String getOrdinaryNoteToUpdate(@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		JSONObject object = new JSONObject();
		object = nm.getOrdinaryNoteToUpdate(noteID);
		if (object != null) {
			object.put("Status", "OK");
		} else
			object.put("Status", "Fail");

		return object.toString();
	}

	@POST
	@Path("/deleteAnyTypeOfNotes")
	public String deleteNote(@FormParam("noteID") String noteID) {

		NoteModel nm = new NoteModel();
		boolean result = nm.deleteAnyTypeOfNotes(noteID);

		JSONObject object = new JSONObject();
		if (result)
			object.put("Status", "OK");
		else
			object.put("Status", "Fail");

		return object.toString();
	}

	@POST
	@Path("/addDeadLineNoteService")
	public String addDeadLineNote(@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, @FormParam("deadLineDate") String deadLineDate,
			@FormParam("creationDate") String creationDate, @FormParam("isDone") String isDone,
			@FormParam("isTextCategorized") String isTextCategorized, @FormParam("noteType") String noteType,
			@FormParam("userID") String userID) {

		DeadlineNoteEntity deadLineNoteEntityObj = new DeadlineNoteEntity(Integer.parseInt(progressPercentage),
				deadLineTitle, Timestamp.valueOf(deadLineDate), "", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType);
		NoteModel nm = new NoteModel();
		long result = nm.adddeadLineNote(deadLineNoteEntityObj);
		JSONObject object = new JSONObject();
		if (result!=0){
			object.put("Status", "OK");
			object.put("noteid", result);
		}
		else{
			object.put("Status", "Fail");
		 	}
		return object.toString();
		// return deadLineNoteEntityObj.toString();
	}

	@POST
	@Path("/addShoppingNoteService")
	public String addShoppingNote(@FormParam("productToBuy") String productToBuy,
			@FormParam("productCategory") String productCategory, @FormParam("creationDate") String creationDate,
			@FormParam("isDone") String isDone, @FormParam("isTextCategorized") String isTextCategorized,
			@FormParam("noteType") String noteType, @FormParam("userID") String userID) {

		ShoppingNoteEntity shoppingNoteEntityObj = new ShoppingNoteEntity("", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType, productToBuy, productCategory);

		NoteModel nm = new NoteModel();
		long result = nm.addShoppingNote(shoppingNoteEntityObj);

		JSONObject object = new JSONObject();
		if (result!=0){
			object.put("Status", "OK");
			object.put("noteid", result);
		}
		else{
			object.put("Status", "Fail");
		 	}
		return object.toString();
	}

	@POST
	@Path("/addMeetingNoteService")
	public String addMeetingNote(@FormParam("creationDate") String creationDate, @FormParam("isDone") String isDone,
			@FormParam("meetingTitle") String meetingTitle, @FormParam("isTextCategorized") String isTextCategorized,
			@FormParam("noteType") String noteType, @FormParam("userID") String userID,
			@FormParam("meetingAgenda") String meetingAgenda, @FormParam("meetingPlace") String meetingPlace,
			@FormParam("meetingNoteDate") String meetingNoteDate,
			@FormParam("estimatedTransportTime") String estimatedTransportTime) {

		MeetingNoteEntity meetingNoteEntityObj = new MeetingNoteEntity(java.sql.Timestamp.valueOf(meetingNoteDate),
				java.sql.Time.valueOf(estimatedTransportTime), meetingTitle, meetingPlace, meetingAgenda, "", userID,
				java.sql.Timestamp.valueOf(creationDate), Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized),
				noteType);

		NoteModel nm = new NoteModel();
		long result = nm.addMeetingNote(meetingNoteEntityObj);

		JSONObject object = new JSONObject();
		if (result!=0){
			object.put("Status", "OK");
			object.put("noteid", result);
		}
		else{
			object.put("Status", "Fail");
		 	}
		return object.toString();
	}

	@POST
	@Path("/addOrdinaryNoteService")
	public String addOrdinaryNote(@FormParam("ordinaryNoteContent") String ordinaryNoteContent,
			@FormParam("creationDate") String creationDate, @FormParam("isDone") String isDone,
			@FormParam("isTextCategorized") String isTextCategorized, @FormParam("noteType") String noteType,
			@FormParam("userID") String userID) {

		OrdinaryNoteEntity ordinaryNoteEntityObj = new OrdinaryNoteEntity("", userID, Timestamp.valueOf(creationDate),
				Boolean.valueOf(isDone), Boolean.valueOf(isTextCategorized), noteType, ordinaryNoteContent);
		NoteModel nm = new NoteModel();
		long result = nm.addOrdinaryNote(ordinaryNoteEntityObj);
		JSONObject object = new JSONObject();
		if (result!=0){
			object.put("Status", "OK");
			object.put("noteid", String .valueOf(result));
		}
		else{
			object.put("Status", "Fail");
		 	}
		return object.toString();
	}


//	@POST
//	@Path("/getAllNotesService")
//	public String getAllNotes(@FormParam("userID") String userID) {
////		
//	
//		NoteModel nm = new NoteModel();
//		JSONArray allNotes = nm.getAllNotes(userID);
//		
//		
//		JSONObject resultObj = new JSONObject();
//		
//
//		resultObj.put("Status", "OK");
//		resultObj.put("AllUserNotes", allNotes);
//		
//		return resultObj.toString();
//	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getAllNotesService")
	public String getAllNotes(@FormParam("userID") String userID) {
		
		NoteModel nm = new NoteModel();
		JSONArray allNotes = nm.getAllNotes(userID);
		
		JSONObject resultObj = new JSONObject();
		System.out.println("AAanskjhdskjh   "+allNotes.size());

		resultObj.put("Status", "OK");
		resultObj.put("resultSize", allNotes.size());
		resultObj.put("AllUserNotes", allNotes);
		resultObj.put("userID",userID);
		return resultObj.toString();
	}
	
	


}
