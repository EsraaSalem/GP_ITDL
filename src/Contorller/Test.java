package Contorller;


import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
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

import com.sun.org.apache.regexp.internal.recompile;

import HTTPConnection.Connection;
import Model.NoteParser;

import dataEntities.NoteEntity;;
@Path("/")
@Produces("text/html")
public class Test {


	public static Vector<NoteEntity> notes;
	public static JSONObject object;
	final private String shopping = "Shopping";
	final private String ordinary = "Ordinary";
	final private String deadline = "DeadLine";
	final private String meeting = "Meeting";
	NoteParser noteParser;
	public Test()
	{
		noteParser = new NoteParser();
	}
	

	
	@POST
	@Path("/runRankingAlgorithm")
	@Produces("text/html")
	public String runRankingAlgorithm() throws org.json.simple.parser.ParseException {
		String serviceUrl = "http://localhost:8888/restRanking/applyAlgorithmService";
		
		String retJson = Connection.connect(serviceUrl, "", "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;		
	}
	
	
	@POST
	@Path("/getAllNotesServiceTEST")
	@Produces("text/html")
	public String getAllNotes1(@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		String serviceUrl = "http://localhost:8888/restNotes/getAllNotesServiceTEST";
		String urlParameters = "userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;		
	}

	
	
	
	
	@POST
	@Path("/showAllNotes")
	@Produces("text/html")
	public String getAllNotes111(@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		
		
		String serviceUrl = "http://localhost:8888/restNotes/getAllNotesService";
		String urlParameters = "userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST", "application/x-www-form-urlencoded;charset=UTF-8");

		
		notes = new Vector<NoteEntity>();

		JSONParser parser = new JSONParser();

		JSONArray array = (JSONArray) parser.parse(retJson.toString());
		for (int i = 0; i < array.size(); i++) {
			JSONObject object;
			object = (JSONObject) array.get(i);
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
	
		return notes.toString();
	}

	@POST
	@Path("/addShoppingNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addShoppingNote(
			@FormParam("productToBuy") String productToBuy,
			@FormParam("productCategory") String productCategory, 
			@FormParam("userID") String userID)
					throws org.json.simple.parser.ParseException {
		String serviceUrl = "http://localhost:8888/restNotes/addShoppingNoteService";
		// String serviceUrl =
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addShoppingNoteService";
		java.util.Date date = new java.util.Date();
		boolean isDone = false;
		boolean isTextCategorized = false;
		String noteType = "Shopping";
		String urlParameters = "productToBuy=" + productToBuy + "&creationDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
				+ isTextCategorized + "&noteType=" + noteType + "&userID=" + userID + "&productCategory="
				+ productCategory;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Shopping Note is added Successfully";
		return "Failed";
	}

	@POST
	@Path("/addDeadLineNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addDeadLineNote(
			@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, 
			@FormParam("deadLineDate") String deadLineDate,
			@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		//String serviceUrl = "http://2-dot-secondhelloworld-1221.appspot.com/rest/addDeadLineNoteService";
		 String serviceUrl ="http://localhost:8888/restNotes/addDeadLineNoteService";
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addDeadLineNoteService";
		java.util.Date date = new java.util.Date();
		boolean isDone = false;
		boolean isTextCategorized = false;
		String noteType = "DeadLine";

		String urlParameters = "progressPercentage=" + progressPercentage + "&creationDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
				+ isTextCategorized + "&noteType=" + noteType + "&userID=" + userID + "&deadLineTitle=" + deadLineTitle
				+ "&deadLineDate=" + deadLineDate;// String.valueOf(new
													// Timestamp(date.getTime()))
													// ;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "deadline Note is added Successfully";
		return "Failed";
		// return retJson;
	}

	@POST
	@Path("/addMeetingNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addMeetingNote(
			@FormParam("meetingTitle") String meetingTitle,
			@FormParam("meetingAgenda") String meetingAgenda, 
			@FormParam("meetingPlace") String meetingPlace,
			@FormParam("meetingNoteDate") String meetingNoteDate,
			@FormParam("estimatedTransportTime") String estimatedTransportTime, 
			@FormParam("userID") String userID)
					throws org.json.simple.parser.ParseException {
		//String serviceUrl = "http://2-dot-secondhelloworld-1221.appspot.com/rest/addMeetingNoteService";
		 String serviceUrl ="http://localhost:8888/restNotes/addMeetingNoteService";
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addMeetingNoteService";
		java.util.Date date = new java.util.Date();

		boolean isDone = false;
		boolean isTextCategorized = false;
		String noteType = "Meeting";

		String urlParameters = "meetingAgenda=" + meetingAgenda + "&meetingPlace=" + meetingPlace + "&meetingNoteDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&estimatedTransportTime="
				+ String.valueOf(new Time(date.getTime())) + "&userID=" + userID + "&creationDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
				+ isTextCategorized + "&noteType=" + noteType + "&meetingTitle=" + meetingTitle;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Meeting Note is added Successfully";

		return "Failed";

	}

	@POST
	@Path("/addOrdinaryNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addOrdinaryNote(
			@FormParam("ordinaryNoteContent") String ordinaryNoteContent,
			@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		String serviceUrl = "http://localhost:8888/restNotes/addOrdinaryNoteService";
		// String serviceUrl =
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addOrdinaryNoteService";
		java.util.Date date = new java.util.Date();
		boolean isDone = false;
		boolean isTextCategorized = false;
		String noteType = "Ordinary";
		String urlParameters = "ordinaryNoteContent=" + ordinaryNoteContent + "&creationDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
				+ isTextCategorized + "&noteType=" + noteType + "&userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Ordinary Note is added Successfully";
		return "Failed";
	}

	@POST
	@Path("/test")
	public String Index(@FormParam("text") String text)
			throws org.json.simple.parser.ParseException {
		return "Text Categorization Controller ";
	}

	@POST
	@Path("/categorizeTextController")

	public String getTextCategory(@FormParam("text") String text)
			throws org.json.simple.parser.ParseException {
																						
		String serviceUrl = "http://localhost:8888/rest/categorizeText";
		String urlParameters = "text=" + text;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;

	}
	

	


}
