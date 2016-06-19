package Contorller;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import HTTPConnection.Connection;
import Model.DefinedCategories;
import Model.NoteParser;
import Model.RankingInputsModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.CategoryCountOfSources;
import dataEntities.NearestStore;
import dataEntities.NoteEntity;
import dataEntities.Offer;
import dataEntities.UserInialWeights;
import recomm_reranking_algorithm_logic_classes.UpdatePreferenceLastDate;;

@Path("/")
@Produces("text/html")
public class Test {
	
	@POST
	@Path("/categorize")
	public String categorize(
			@FormParam("str") String str) throws JSONException, ParseException
	{
		
		TextCategorization t = new TextCategorization();
		return t.callTextCategoryAPI(str);
	}
	
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


	/***
	 * 
	 * Test Sign up web service 
	 * auther :Esraa
	 * Date: Fri 17 /6 /2016
	 * **/
	@POST
	@Path("/signup")
	public String SignUp(
			@FormParam("username") String name, 
			@FormParam("userpassword") String password,
			@FormParam("useremail") String email, 
			@FormParam("gender") String gender,
			@FormParam("city") String city,
			@FormParam("birth_date") String birth_date, 
			@FormParam("Twitter_Account") String Twitter_Account
			)
	{
		String serviceUrl = "http://localhost:8888/restNotes/RegestrationService";
		//String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/RegestrationService";
		String urlParameters = "username=" + name +"&userpassword="+password+"&useremail="+email+"&gender="+gender
				+"&city="+city+"&birth_date="+birth_date+"&Twitter_Account="+Twitter_Account;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		
		
		return retJson;
	}

	
//	
//	@POST
//	@Path("/getNearestStoresToUser")
//	public String getNearestStoresToUser(@FormParam("userID") String userID)
//	{
//	
//		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/getNearestStoresToUserService";
//		String urlParameters = "userID=" + userID;
//		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
//				"application/x-www-form-urlencoded;charset=UTF-8");
//		return retJson;
//	
//	}
	@POST
	@Path("/updateCreationDateAndIsDone")
	public String updateCreationDateAndIsDone(@FormParam("noteID") String noteID)
	{
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/updateCreationDateAndIsDoneService";
		String urlParameters = "noteID=" + noteID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		return retJson;
	
	}
	/***
	 * 
	 * Test Log in web service 
	 * auther :Esraa
	 * Date: Fri 17 /6 /2016
	 * **/
	
	@POST
	@Path("/login")
	public String login(@FormParam("useremail") String useremail, @FormParam("userpassword") String userpassword)
	{
		//String serviceUrl = "http://localhost:8888/restNotes/LoginService";
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/LoginService";
		String urlParameters = "useremail=" + useremail +"&userpassword="+userpassword;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		
		return retJson;
	}
	
	/***
	 * Test getNearestStoresToUser web service
	 * ***/
	@POST
	@Path("/getNearestStoresToUser")
	public String getNearestStoresToUser(@FormParam("userID") String ID) throws org.json.simple.parser.ParseException {

		String serviceUrl =		 "http://localhost:8888/restOffer/getNearestStoresToUserService";
		//String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getNearestStoresToUserService";

		String urlParameters = "userID=" + ID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		return retJson ;
//		JSONParser parser = new JSONParser();
//		Object obj;
//		obj = parser.parse(retJson);
//		JSONObject object = (JSONObject) obj;
//		if (object.get("Status").equals("OK")) {
//			String res = object.get("result").toString();
//
//			Vector<NearestStore> nearStores = new Vector<NearestStore>();
//			// System.out.println("WWWWWWWWWWWWWWWWWWWWWW\n"+retJson);
//			nearStores = getParsesNearestStores(res);
//			System.out.println("---------------near store------------------------");
//			for (int i = 0; i < nearStores.size(); i++) {
//				System.out.println(nearStores.get(i).toString());
//			}
//
//			return res;
//
//		} else {
//			return "Failed";
//
//		}

	}
	/**
	 * DONE final
	 ***/
	@POST
	@Path("/getTop3PreferencesController")
	@Produces("text/html")
	public String getTop3Preferences(@FormParam("userID") String userID)
			throws org.json.simple.parser.ParseException, JSONException {

		// String serviceUrl =
		// "http://4-dot-secondhelloworld-1221.appspot.com/restOffer/getTop3Preferences";
		String serviceUrl = "http://localhost:8888/restOffer/getTop3Preferences";

		String urlParameters = "userID=" + userID;
		String res = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(res);
		JSONObject object = (JSONObject) obj;
		String interests = "";
		if (object.get("status").toString().equals("OK")) {
			interests = object.get("result").toString();
			Vector<UserInialWeights> userInterst = new Vector<UserInialWeights>();
			userInterst = getParsedUserInitialWeights(interests);
			for (int i = 0; i < userInterst.size(); i++) {
				System.out.println(userInterst.get(i).toString());
			}

		} else {
			interests = "Failed";
		}
		return interests;

	}

	/**
	 * DONE final
	 **/
	@POST
	@Path("/getOffers")
	@Produces("text/html")
	public String getOffers(@FormParam("userID") String userID)
			throws org.json.simple.parser.ParseException, JSONException {

		 String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getOfferService";
		//String serviceUrl = "http://localhost:8888/restOffer/getOfferService";
		String urlParameters = "userID=" + userID;
		String res = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		System.out.println("Result = " + res);

		Vector<Offer> offers = new Vector<Offer>();
		offers = getParsedOffers(res);
		System.out.println("----------Offers-----------------------");
		for (int i = 0; i < offers.size(); i++) {
			System.out.println(offers.get(i).toString());
		}

		return res;

	}

	public Vector<Offer> getParsedOffers(String offersSTR) throws ParseException {
		JSONParser parser = new JSONParser();
		Vector<Offer> result = new Vector<Offer>();
		JSONObject o = (JSONObject) parser.parse(offersSTR.toString());
		JSONArray jsonArray = (JSONArray)o.get("result");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj = (JSONObject) jsonArray.get(i);
			Offer offer = new Offer();

			offer.setOfferID(String.valueOf(obj.get("offerID")));
			offer.setCategory(String.valueOf(obj.get("category")));
			offer.setContent(String.valueOf(obj.get("content")));
			offer.setStartDate(String.valueOf(obj.get("startDate")));
			offer.setEndDate(String.valueOf(obj.get("endDate")));
			offer.setStoreID(String.valueOf(obj.get("storeID")));
			offer.setStoreLat(Double.valueOf(obj.get("storeLat").toString()));
			offer.setStoreLong(Double.valueOf(obj.get("storeLong").toString()));
			offer.setJsonStoreEmail(String.valueOf(obj.get("jsonStoreEmail")));
			result.add(offer);

		}
		return result;
	}

	// 6313740437815296

	/****
	 * DONE Final
	 ****/
	@POST
	@Path("/updateUserPreferences")

	public String updateUserPerferences(@FormParam("userID") String ID, @FormParam("twitterID") String twitterID)
			throws org.json.simple.parser.ParseException {

		// String serviceUrl =
		// "http://4-dot-secondhelloworld-1221.appspot.com/restRanking/updateUserPreferenceService";
		String serviceUrl = "http://localhost:8888/restRanking/updateUserPreferenceService";

		String urlParameters = "userID=" + ID + "&twitterID=" + twitterID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		if (!retJson.equals("empty")) {
			Vector<UserInialWeights> userInterst = new Vector<UserInialWeights>();
			userInterst = getParsedUserInitialWeights(retJson);

		}
		//

		return retJson;

	}
	
	public Vector<UserInialWeights> getParsedUserInitialWeights(String str) throws ParseException {
		JSONParser parser = new JSONParser();
		Vector<UserInialWeights> res = new Vector<UserInialWeights>();
		System.out.println("AAAAAAA  " + str);
		JSONArray jsonArray = (JSONArray) parser.parse(str.toString());

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj = (JSONObject) jsonArray.get(i);
			UserInialWeights uiw = new UserInialWeights();
			uiw.setCategoryID(obj.get("categoryID").toString());
			uiw.setCategoryName(obj.get("categoryName").toString());
			uiw.setInialWeight(Double.parseDouble(obj.get("inialWeight").toString()));
			uiw.setUserID(obj.get("userID").toString());
			uiw.setCategoryRecordID(obj.get("categoryRecordID").toString());

			res.add(uiw);
		}

		return res;
	}

	public Vector<NearestStore> getParsesNearestStores(String nearestStoresSTR) throws ParseException {
		JSONParser parser = new JSONParser();
		Vector<NearestStore> result = new Vector<NearestStore>();
		JSONArray jsonArray = (JSONArray) parser.parse(nearestStoresSTR.toString());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject obj = new JSONObject();
			obj = (JSONObject) jsonArray.get(i);
			NearestStore nearStore = new NearestStore();

			nearStore.setStoreName(String.valueOf(obj.get("storeName")));
			nearStore.setUserProductToBuy(String.valueOf(obj.get("userProductToBuy")));
			nearStore.setLat(Double.parseDouble(obj.get("lat").toString()));
			nearStore.setLongt(Double.parseDouble(obj.get("longt").toString()));

			result.add(nearStore);

		}
		return result;
	}

//---------------------------------------------------------------------------------------------------------
	@POST
	@Path("/addDeadLineNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addDeadLineNote(@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, @FormParam("deadLineDate") String deadLineDate,
			@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		// String serviceUrl =		 "http://localhost:8888/restNotes/addDeadLineNoteService";
		String serviceUrl ="http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addDeadLineNoteService";
		// ;
		
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
		 String serviceUrl =		 "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addMeetingNoteService";
		//String serviceUrl = "http://localhost:8888/restNotes/addMeetingNoteService";//"http://localhost:8888/restNotes/addMeetingNoteService";
		// 
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
		//String serviceUrl = "http://localhost:8888/restNotes/addOrdinaryNoteService";
		 String serviceUrl =		 "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addOrdinaryNoteService";
		java.util.Date date = new java.util.Date();
		boolean isDone = false;
		boolean isTextCategorized = false;
		String noteType = "Ordinary";
		String urlParameters = "ordinaryNoteContent=" + ordinaryNoteContent + "&creationDate="
				+ String.valueOf(new Timestamp(date.getTime())) + "&isDone=" + isDone + "&isTextCategorized="
				+ isTextCategorized + "&noteType=" + noteType + "&userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		System.out.println("NNNNNNNNNNNnn   " + retJson);
		JSONParser parser = new JSONParser();
		Object obj;
		obj = parser.parse(retJson);
		JSONObject object = (JSONObject) obj;
		if (object.get("Status").equals("OK"))
			return "Ordinary Note is added Successfully";
		return "Failed";
	}

	
	@POST
	@Path("/addShoppingNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addShoppingNote(@FormParam("productToBuy") String productToBuy,
			@FormParam("productCategory") String productCategory, @FormParam("userID") String userID)
					throws org.json.simple.parser.ParseException {
		String serviceUrl = "http://localhost:8888/restNotes/addShoppingNoteService";
		// String serviceUrl ="http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addShoppingNoteService";
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
	@Path("/getAllNotesService")
	@Produces("text/html")
	public String getAllNotes111(@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {

		String serviceUrl =	 "http://localhost:8888/restNotes/getAllNotesService";
		//String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/getAllNotesService";

		String urlParameters = "userID=" + userID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");

		System.out.println(retJson);
		notes = new Vector<NoteEntity>();

		JSONParser parser = new JSONParser();

		JSONObject JsonObj = (JSONObject) parser.parse(retJson.toString());

		JSONArray array = new JSONArray();
		if (JsonObj.get("Status").toString().equals("OK")) {
			array = (JSONArray) JsonObj.get("AllUserNotes");

		}

		if (array.size() == 0) {
			return "emptyNotes";
		}
		for (int i = 0; i < array.size(); i++) {
			JSONObject object;
			object = (JSONObject) array.get(i);
			if (object.containsKey(meeting)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(meeting).toString());
				notes.add(noteParser.convertJsonObjToMeetingNoteObj(object1));
				

			} else if (object.containsKey(ordinary)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(ordinary).toString());
				notes.add(noteParser.convertJsonObjToOrdinaryNoteObj(object1));

			} else if (object.containsKey(shopping)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(shopping).toString());
				notes.add(noteParser.convertJsonObjToShoppingNoteObj(object1));

			} else if (object.containsKey(deadline)) {
				JSONParser p = new JSONParser();
				JSONObject object1 = (JSONObject) p.parse(object.get(deadline).toString());
				notes.add(noteParser.convertJsonObjToDeadLineNoteObj(object1));
			}
		}
		System.out.println("Sizreeee == "+notes.size());
for (int i = 0; i <notes.size(); i++) {
	System.out.println(notes.get(i).toString());
	
}
		//return notes.toString();
		return retJson;
	}
	
	
		@POST
	@Path("/cat")
	@Produces("text/html")
	public String cat(@FormParam("word") String word) throws org.json.simple.parser.ParseException, JSONException {

		TextCategorization t = new TextCategorization();
		String x = t.callTextCategoryAPI(word);
		return x;

	}


}
