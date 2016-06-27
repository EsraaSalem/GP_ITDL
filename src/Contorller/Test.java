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
import com.sun.org.apache.bcel.internal.generic.RET;

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
	public static Vector<NoteEntity> notes;
	public static JSONObject object;
	final private String shopping = "Shopping";
	final private String ordinary = "Ordinary";
	final private String deadline = "DeadLine";
	final private String meeting = "Meeting";
	NoteParser noteParser = new NoteParser();

	@POST
	@Path("/addDeadLineNote")
	@Produces(MediaType.TEXT_PLAIN)
	public String addDeadLineNote(@FormParam("progressPercentage") String progressPercentage,
			@FormParam("deadLineTitle") String deadLineTitle, @FormParam("deadLineDate") String deadLineDate,
			@FormParam("userID") String userID) throws org.json.simple.parser.ParseException {
		// String serviceUrl =
		// "http://localhost:8888/restNotes/addDeadLineNoteService";
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addDeadLineNoteService";
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
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addMeetingNoteService";
		// String serviceUrl =
		// "http://localhost:8888/restNotes/addMeetingNoteService";//"http://localhost:8888/restNotes/addMeetingNoteService";
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
		// String serviceUrl =
		// "http://localhost:8888/restNotes/addOrdinaryNoteService";
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addOrdinaryNoteService";
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
		// String serviceUrl
		// ="http://5-dot-secondhelloworld-1221.appspot.com/restNotes/addShoppingNoteService";
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

		//String serviceUrl = "http://localhost:8888/restNotes/getAllNotesService";
		 String serviceUrl =
		 "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/getAllNotesService";

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
		System.out.println("Sizreeee == " + notes.size());
		for (int i = 0; i < notes.size(); i++) {
			System.out.println(notes.get(i).toString());

		}
		// return notes.toString();
		return retJson;
	}
	// @POST
	// @Path("/aaaGETUserInitialWeights")
	//
	// public String getUserIinitailWeights(@FormParam("id") String ID) throws
	// org.json.simple.parser.ParseException {
	// RankingInputsModel r = new RankingInputsModel();
	// String result = r.getUserInitialWeightsByUserID(ID).toString();
	// return result;
	//
	// }
	//
	// ////////////////////////////////////////////////////////////////////////////////////////
	// /**
	// *
	// * Test Enter inialWeights service
	// */
	//
	// @POST
	// @Path("/enterInitialWeights1User")
	// @Produces(MediaType.TEXT_PLAIN)
	// public String enterInitialWeights1User(@FormParam("catname1") String
	// catname1,
	// @FormParam("catratio1") String catratio1, @FormParam("catname2") String
	// catname2,
	// @FormParam("catratio2") String catratio2, @FormParam("catname3") String
	// catname3,
	// @FormParam("catratio3") String catratio3, @FormParam("catname4") String
	// catname4,
	// @FormParam("catratio4") String catratio4, @FormParam("catname5") String
	// catname5,
	// @FormParam("catratio5") String catratio5, @FormParam("catname6") String
	// catname6,
	// @FormParam("catratio6") String catratio6, @FormParam("catname7") String
	// catname7,
	// @FormParam("catratio7") String catratio7, @FormParam("catname8") String
	// catname8,
	// @FormParam("catratio8") String catratio8, @FormParam("catname9") String
	// catname9,
	// @FormParam("catratio9") String catratio9, @FormParam("catname10") String
	// catname10,
	// @FormParam("catratio10") String catratio10,
	//
	// @FormParam("catname11") String catname11, @FormParam("catratio11") String
	// catratio11,
	//
	// @FormParam("catname12") String catname12, @FormParam("catratio12") String
	// catratio12,
	//
	// @FormParam("userID") String userID) throws
	// org.json.simple.parser.ParseException {
	//
	// JSONArray userInialWeights = new JSONArray();
	//
	// JSONObject jsonObj1 = new JSONObject();
	// jsonObj1.put("categoryName", catname1);
	// jsonObj1.put("initialWeight", catratio1);
	// userInialWeights.add(jsonObj1);
	//
	//
	//
	// JSONObject jsonObj2 = new JSONObject();
	// jsonObj2.put("categoryName", catname2);
	// jsonObj2.put("initialWeight", catratio2);
	// userInialWeights.add(jsonObj2);
	// JSONObject jsonObj3 = new JSONObject();
	// jsonObj3.put("categoryName", catname3);
	// jsonObj3.put("initialWeight", catratio3);
	// userInialWeights.add(jsonObj3);
	// JSONObject jsonObj4 = new JSONObject();
	// jsonObj4.put("categoryName", catname4);
	// jsonObj4.put("initialWeight", catratio4);
	// userInialWeights.add(jsonObj4);
	// JSONObject jsonObj5 = new JSONObject();
	// jsonObj5.put("categoryName", catname5);
	// jsonObj5.put("initialWeight", catratio5);
	// userInialWeights.add(jsonObj5);
	// JSONObject jsonObj6 = new JSONObject();
	// jsonObj6.put("categoryName", catname6);
	// jsonObj6.put("initialWeight", catratio6);
	// userInialWeights.add(jsonObj6);
	// JSONObject jsonObj7 = new JSONObject();
	// jsonObj7.put("categoryName", catname7);
	// jsonObj7.put("initialWeight", catratio7);
	// userInialWeights.add(jsonObj7);
	// JSONObject jsonObj8 = new JSONObject();
	// jsonObj8.put("categoryName", catname8);
	// jsonObj8.put("initialWeight", catratio8);
	// userInialWeights.add(jsonObj8);
	// JSONObject jsonObj9 = new JSONObject();
	// jsonObj9.put("categoryName", catname9);
	// jsonObj9.put("initialWeight", catratio9);
	// userInialWeights.add(jsonObj9);
	// JSONObject jsonObj10 = new JSONObject();
	// jsonObj10.put("categoryName", catname10);
	// jsonObj10.put("initialWeight", catratio10);
	// userInialWeights.add(jsonObj10);
	// JSONObject jsonObj11 = new JSONObject();
	// jsonObj11.put("categoryName", catname11);
	// jsonObj11.put("initialWeight", catratio11);
	// userInialWeights.add(jsonObj11);
	// JSONObject jsonObj12 = new JSONObject();
	// jsonObj12.put("categoryName", catname12);
	// jsonObj12.put("initialWeight", catratio12);
	// userInialWeights.add(jsonObj12);
	//
	// String userInitialWeightsSTR = userInialWeights.toString();
	//
	//// System.out.println("VVVVVVV "+userID );
	//// System.out.println("KKKKKKKKKKKKKKKKKK ");
	//// System.out.println(userInitialWeightsSTR);
	// String serviceUrl =
	// "http://5-dot-secondhelloworld-1221.appspot.com/restNotes/enterInitialWeightsForOneUserService";
	//
	// //String serviceUrl =
	// "http://4-dot-secondhelloworld-1221.appspot.com/restNotes/enterInitialWeightsForOneUserService";
	//
	// String urlParameters = "userID=" + userID + "&userInitialWeightsSTR=" +
	// userInitialWeightsSTR;
	//
	// String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
	// "application/x-www-form-urlencoded;charset=UTF-8");
	//
	// return retJson;
	// }
	//
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// /***
	// * Test getNearestStoresToUser web service
	// ***/
	// @POST
	// @Path("/getNearestStoresToUser")
	// public String getNearestStoresToUser(@FormParam("userID") String ID)
	// throws org.json.simple.parser.ParseException {
	//
	// // String serviceUrl =
	// "http://localhost:8888/restOffer/getNearestStoresToUserService";
	// String serviceUrl =
	// "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getNearestStoresToUserService";
	//
	// String urlParameters = "userID=" + ID;
	// String retJson = Connection.connect(serviceUrl, urlParameters,
	// "POST","application/x-www-form-urlencoded;charset=UTF-8");
	//
	// Vector<NearestStore> vec = new Vector<NearestStore>();
	// vec = getParsesNearestStores(retJson);
	// for (int i = 0; i < vec.size(); i++) {
	// System.out.println(vec.get(i).toString());
	// }
	// return retJson;
	// }
	//
	// public Vector<NearestStore> getParsesNearestStores(String
	// nearestStoresSTR) throws ParseException {
	// Vector<NearestStore> result = new Vector<NearestStore>();
	// JSONParser parser = new JSONParser();
	//
	// JSONObject jsonObjAllNearstStore = new JSONObject();
	// jsonObjAllNearstStore = (JSONObject)
	// parser.parse(nearestStoresSTR.toString());
	// int resultSize =
	// Integer.parseInt(jsonObjAllNearstStore.get("resultSize").toString());
	// if (resultSize > 0) {
	// JSONArray nearStoreArr = new JSONArray();
	// nearStoreArr = (JSONArray) jsonObjAllNearstStore.get("result");
	// for (int i = 0; i < nearStoreArr.size(); i++) {
	// JSONObject nStoreObj = new JSONObject();
	// nStoreObj = (JSONObject) nearStoreArr.get(i);
	// NearestStore n = new NearestStore();
	// n.setLat(Double.parseDouble(nStoreObj.get("lat").toString()));
	// n.setLat(Double.parseDouble(nStoreObj.get("longt").toString()));
	// n.setStoreName(nStoreObj.get("storeName").toString());
	// n.setStoreAddress(nStoreObj.get("storeAddress").toString());
	// n.setStoreEmail(nStoreObj.get("storeEmail").toString());
	// JSONArray listOfcat = new JSONArray();
	// listOfcat = (JSONArray) nStoreObj.get("listOfStoreCategories");
	// Vector<String> v1 = new Vector<String>();
	// for (int j = 0; j < listOfcat.size(); j++) {
	// v1.add(listOfcat.get(j).toString());
	// }
	// JSONArray noteList = new JSONArray();
	// noteList = (JSONArray) nStoreObj.get("listOfShoppingNote");
	// Vector<String> v2 = new Vector<String>();
	// for (int j = 0; j < noteList.size(); j++) {
	// v2.add(noteList.get(j).toString());
	// }
	// n.setListOfUserShoppingNotes(v2);
	// n.setStoreCategories(v1);
	// result.add(n);
	// }
	//
	// } else {
	//
	// }
	//
	// return result;
	// }
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// @POST
	// @Path("/cat")
	// @Produces("text/html")
	// public String cat(@FormParam("word") String word) throws
	// org.json.simple.parser.ParseException, JSONException {
	//
	// TextCategorization t = new TextCategorization();
	// String x = t.callTextCategoryAPI(word);
	// return x;
	//
	// }
	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//
	// @POST
	// @Path("/getOffers")
	// @Produces("text/html")
	// public String getOffers(@FormParam("userID") String userID)
	// throws org.json.simple.parser.ParseException, JSONException {
	//
	// String serviceUrl =
	// "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getOfferService";
	// // String serviceUrl =
	// // "http://localhost:8888/restOffer/getOfferService";
	// String urlParameters = "userID=" + userID;
	// String res = Connection.connect(serviceUrl, urlParameters, "POST",
	// "application/x-www-form-urlencoded;charset=UTF-8");
	// System.out.println("Result = " + res);
	//
	// Vector<Offer> offers = new Vector<Offer>();
	// offers = getParsedOffers(res);
	// System.out.println("----------Offers-----------------------");
	// for (int i = 0; i < offers.size(); i++) {
	// System.out.println(offers.get(i).toString());
	// }
	//
	// return res;
	//
	// }
	//
	// public Vector<Offer> getParsedOffers(String offersSTR) throws
	// ParseException {
	// JSONParser parser = new JSONParser();
	// Vector<Offer> result = new Vector<Offer>();
	// JSONObject o = (JSONObject) parser.parse(offersSTR.toString());
	// JSONArray jsonArray = (JSONArray) o.get("result");
	// for (int i = 0; i < jsonArray.size(); i++) {
	// JSONObject obj = new JSONObject();
	// obj = (JSONObject) jsonArray.get(i);
	// Offer offer = new Offer();
	//
	// offer.setOfferID(String.valueOf(obj.get("offerID")));
	// offer.setCategory(String.valueOf(obj.get("category")));
	// offer.setContent(String.valueOf(obj.get("content")));
	// offer.setStartDate(String.valueOf(obj.get("startDate")));
	// offer.setEndDate(String.valueOf(obj.get("endDate")));
	// offer.setStoreID(String.valueOf(obj.get("storeID")));
	// offer.setStoreLat(Double.valueOf(obj.get("storeLat").toString()));
	// offer.setStoreLong(Double.valueOf(obj.get("storeLong").toString()));
	// offer.setJsonStoreEmail(String.valueOf(obj.get("jsonStoreEmail")));
	// result.add(offer);
	//
	// }
	// return result;
	// }

}
