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

import CRUD_oprations_Serivces.RankingInputs;
import HTTPConnection.Connection;
import Model.DefinedCategories;
import Model.RankingInputsModel;
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

	public Test() {
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
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;
	}

	@POST
	@Path("/showAllNotes")
	@Produces("text/html")
	public String getAllNotes111(@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {

		String serviceUrl = "http://localhost:8888/restNotes/getAllNotesService";
		String urlParameters = "userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

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
	public String addShoppingNote(@FormParam("productToBuy") String productToBuy,
			@FormParam("productCategory") String productCategory, @FormParam("userID") String userID)
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
	public String addDeadLineNote(@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, @FormParam("deadLineDate") String deadLineDate,
			@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		// String serviceUrl =
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addDeadLineNoteService";
		String serviceUrl = "http://localhost:8888/restNotes/addDeadLineNoteService";
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
	public String addMeetingNote(@FormParam("meetingTitle") String meetingTitle,
			@FormParam("meetingAgenda") String meetingAgenda, @FormParam("meetingPlace") String meetingPlace,
			@FormParam("meetingNoteDate") String meetingNoteDate,
			@FormParam("estimatedTransportTime") String estimatedTransportTime, @FormParam("userID") String userID)
					throws org.json.simple.parser.ParseException {
		// String serviceUrl =
		// "http://2-dot-secondhelloworld-1221.appspot.com/rest/addMeetingNoteService";
		String serviceUrl = "http://localhost:8888/restNotes/addMeetingNoteService";
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
	public String addOrdinaryNote(@FormParam("ordinaryNoteContent") String ordinaryNoteContent,
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
	public String Index(@FormParam("text") String text) throws org.json.simple.parser.ParseException {
		return "Text Categorization Controller ";
	}

	@POST
	@Path("/categorizeTextController")

	public String getTextCategory(@FormParam("text") String text) throws org.json.simple.parser.ParseException {

		String serviceUrl = "http://localhost:8888/rest/categorizeText";
		String urlParameters = "text=" + text;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;

	}

	// /**
	// * Test adding predefined categories
	// *
	// * @param categoryID
	// * the unique Identifier to the category name
	// * @author Esraa Salem
	// * @since Friday 10-06-2016
	// */
	// @POST
	// @Path("/add")
	//
	// public String add()
	// throws org.json.simple.parser.ParseException {
	// DefinedCategories dc = new DefinedCategories();
	// dc.readFile();
	//
	// return "added";
	//
	// }
	//

	/**
	 * Test adding predefined categories
	 * 
	 * @param categoryID
	 *            the unique Identifier to the category name
	 * @author Esraa Salem
	 * @since Friday 10-06-2016
	 */
	@POST
	@Path("/add")

	public String selectCatName(@FormParam("ID") String ID) throws org.json.simple.parser.ParseException {
		DefinedCategories dc = new DefinedCategories();
		String result = dc.getCategoryNameByID(ID);
		return result;

	}

	/**
	 * Test get category ID given its name
	 * 
	 * @param category
	 *            name
	 * @author Esraa Salem
	 * @since Friday 10-06-2016
	 */
	@POST
	@Path("/getID")

	public String selectCatID(@FormParam("name") String name) throws org.json.simple.parser.ParseException {
		DefinedCategories dc = new DefinedCategories();
		String result = dc.getCategoryIDByName(name);

		return result;

	}

	@POST
	@Path("/addInputSourcesSignificance")

	public String selectCatName() throws org.json.simple.parser.ParseException {
		DefinedCategories dc = new DefinedCategories();
		dc.readFile();
		// RankingInputsModel iss = new RankingInputsModel();
		// iss.readFile();
		// String serviceUrl
		// ="http://1-dot-secondhelloworld-1221.appspot.com/rest/addInputSourcesSignificanceService";
		// //String serviceUrl =
		// "http://localhost:8888/restNotes/addInputSourcesSignificanceService";
		// String urlParameters = "";
		// String retJson = Connection.connect(serviceUrl, urlParameters,
		// "POST", "application/x-www-form-urlencoded;charset=UTF-8");
		// return retJson;
		return "AAAAA";

	}

	@POST
	@Path("/getInputSourcesSignificance")

	public String getSignificanRatios() throws org.json.simple.parser.ParseException {

		String serviceUrl = "http://1-dot-secondhelloworld-1221.appspot.com/restNotes/getInputSourcesSignificanceService";
		String urlParameters = "";
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;

	}

	@POST
	@Path("/enterInitialWeights1User")
	@Produces(MediaType.TEXT_PLAIN)
	public String enterInitialWeights1User(@FormParam("catname1") String catname1,
			@FormParam("catratio1") String catratio1, @FormParam("catname2") String catname2,
			@FormParam("catratio2") String catratio2, @FormParam("catname3") String catname3,
			@FormParam("catratio3") String catratio3, @FormParam("catname4") String catname4,
			@FormParam("catratio4") String catratio4, @FormParam("catname5") String catname5,
			@FormParam("catratio5") String catratio5, @FormParam("catname6") String catname6,
			@FormParam("catratio6") String catratio6, @FormParam("catname7") String catname7,
			@FormParam("catratio7") String catratio7, @FormParam("catname8") String catname8,
			@FormParam("catratio8") String catratio8, @FormParam("catname9") String catname9,
			@FormParam("catratio9") String catratio9, @FormParam("catname10") String catname10,
			@FormParam("catratio10") String catratio10,

			
			@FormParam("catname11") String catname11,
			@FormParam("catratio11") String catratio11,
			
			@FormParam("catname12") String catname12,
			@FormParam("catratio12") String catratio12,
			
			@FormParam("userID") String userID
	) throws org.json.simple.parser.ParseException {

		
		JSONArray userInialWeights = new JSONArray();
		JSONObject jsonObj1 = new JSONObject();	jsonObj1.put("categoryName", catname1);	jsonObj1.put("initialWeight", catratio1);	userInialWeights.add(jsonObj1);
		JSONObject jsonObj2 = new JSONObject();	jsonObj2.put("categoryName", catname2);	jsonObj2.put("initialWeight", catratio2);	userInialWeights.add(jsonObj2);
		JSONObject jsonObj3 = new JSONObject();	jsonObj3.put("categoryName", catname3);	jsonObj3.put("initialWeight", catratio3);	userInialWeights.add(jsonObj3);
		JSONObject jsonObj4 = new JSONObject();	jsonObj4.put("categoryName", catname4);	jsonObj4.put("initialWeight", catratio4);	userInialWeights.add(jsonObj4);
		JSONObject jsonObj5 = new JSONObject();	jsonObj5.put("categoryName", catname5);	jsonObj5.put("initialWeight", catratio5);	userInialWeights.add(jsonObj5);
		JSONObject jsonObj6 = new JSONObject();	jsonObj6.put("categoryName", catname6);	jsonObj6.put("initialWeight", catratio6);	userInialWeights.add(jsonObj6);
		JSONObject jsonObj7 = new JSONObject();	jsonObj7.put("categoryName", catname7);	jsonObj7.put("initialWeight", catratio7);	userInialWeights.add(jsonObj7);
		JSONObject jsonObj8 = new JSONObject();	jsonObj8.put("categoryName", catname8);	jsonObj8.put("initialWeight", catratio8);	userInialWeights.add(jsonObj8);
		JSONObject jsonObj9 = new JSONObject();	jsonObj9.put("categoryName", catname9);	jsonObj9.put("initialWeight", catratio9);	userInialWeights.add(jsonObj9);
		JSONObject jsonObj10 = new JSONObject();jsonObj10.put("categoryName", catname10);jsonObj10.put("initialWeight", catratio10);userInialWeights.add(jsonObj10);
		JSONObject jsonObj11 = new JSONObject();jsonObj11.put("categoryName", catname11);jsonObj11.put("initialWeight", catratio11);userInialWeights.add(jsonObj11);
		JSONObject jsonObj12 = new JSONObject();jsonObj12.put("categoryName", catname12);jsonObj12.put("initialWeight", catratio12);userInialWeights.add(jsonObj12);
		
		String userInitialWeightsSTR = userInialWeights.toString();
		//String serviceUrl = "http://localhost:8888/restNotes/enterInitialWeightsForOneUserService";
		
		String serviceUrl = "http://1-dot-secondhelloworld-1221.appspot.com/restNotes/enterInitialWeightsForOneUserService";

		

		String urlParameters = "userID=" + userID + "&userInitialWeightsSTR="+ userInitialWeightsSTR;
		
		
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		return retJson;
	}

	
	
	@POST
	@Path("/getUserIinitailWeights")

	public String getUserIinitailWeights(@FormParam("ID") String ID) throws org.json.simple.parser.ParseException {
		RankingInputsModel r = new RankingInputsModel();
		String result = r.getUserInitialWeightsByUserID(ID).toString();
		return result;

	}
}
