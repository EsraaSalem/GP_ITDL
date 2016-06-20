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

	/***
	 * Test getNearestStoresToUser web service
	 ***/
	@POST
	@Path("/getNearestStoresToUser")
	public String getNearestStoresToUser(@FormParam("userID") String ID) throws org.json.simple.parser.ParseException {

		// String serviceUrl = "http://localhost:8888/restOffer/getNearestStoresToUserService";
		String serviceUrl = "http://5-dot-secondhelloworld-1221.appspot.com/restOffer/getNearestStoresToUserService";

		String urlParameters = "userID=" + ID;
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST","application/x-www-form-urlencoded;charset=UTF-8");

		Vector<NearestStore> vec = new Vector<NearestStore>();
		vec = getParsesNearestStores(retJson);
		for (int i = 0; i < vec.size(); i++) {
			System.out.println(vec.get(i).toString());
		}
		return retJson;
	}

	public Vector<NearestStore> getParsesNearestStores(String nearestStoresSTR) throws ParseException {
		Vector<NearestStore> result = new Vector<NearestStore>();
		JSONParser parser = new JSONParser();

		JSONObject jsonObjAllNearstStore = new JSONObject();
		jsonObjAllNearstStore = (JSONObject) parser.parse(nearestStoresSTR.toString());
		int resultSize = Integer.parseInt(jsonObjAllNearstStore.get("resultSize").toString());
		if (resultSize > 0) {
			JSONArray nearStoreArr = new JSONArray();
			nearStoreArr = (JSONArray) jsonObjAllNearstStore.get("result");
			for (int i = 0; i < nearStoreArr.size(); i++) {
				JSONObject nStoreObj = new JSONObject();
				nStoreObj = (JSONObject) nearStoreArr.get(i);
				NearestStore n = new NearestStore();
				n.setLat(Double.parseDouble(nStoreObj.get("lat").toString()));
				n.setLat(Double.parseDouble(nStoreObj.get("longt").toString()));
				n.setStoreName(nStoreObj.get("storeName").toString());
				n.setStoreAddress(nStoreObj.get("storeAddress").toString());
				n.setStoreEmail(nStoreObj.get("storeEmail").toString());
				JSONArray listOfcat = new JSONArray();
				listOfcat = (JSONArray) nStoreObj.get("listOfStoreCategories");
				Vector<String> v1 = new Vector<String>();
				for (int j = 0; j < listOfcat.size(); j++) {
					v1.add(listOfcat.get(j).toString());
				}
				JSONArray noteList = new JSONArray();
				noteList = (JSONArray) nStoreObj.get("listOfShoppingNote");
				Vector<String> v2 = new Vector<String>();
				for (int j = 0; j < noteList.size(); j++) {
					v2.add(noteList.get(j).toString());
				}
				n.setListOfUserShoppingNotes(v2);
				n.setStoreCategories(v1);
				result.add(n);
			}

		} else {

		}

		return result;
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@POST
	@Path("/cat")
	@Produces("text/html")
	public String cat(@FormParam("word") String word) throws org.json.simple.parser.ParseException, JSONException {

		TextCategorization t = new TextCategorization();
		String x = t.callTextCategoryAPI(word);
		return x;

	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
