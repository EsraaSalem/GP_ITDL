package recomm_offer_preferences_logic_classes;

import java.util.ArrayList;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import HTTPConnection.Connection;
import Model.RankingInputsModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.Offer;
import dataEntities.Store;
import dataEntities.UserInialWeights;

public class OfferRecommender {
	public Vector<Offer> callGetAllOffersWebService() throws JSONException {
		Vector<Offer> storeOffers = new Vector<Offer>();
		//replace the webservice url with newest one to get All offers
		String serviceUrl = "http://8-dot-itdloffers.appspot.com/rest/GetOffersService";
		String urlParameters = "";
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			// String output = "";
			if (object.get("Status").equals("OK")) {
				JSONArray jstores = (JSONArray) parser.parse(object.get("AllStores").toString());
				for (int i = 0; i < jstores.size(); i++) {
					JSONObject jstore;
					jstore = (JSONObject) jstores.get(i);

					String storeLat = jstore.get("latitude").toString();
					String storeLong = jstore.get("longitude").toString();
					String jsonStoreEmail = jstore.get("storeEmail").toString();
					String storeAddress = jstore.get("storeAddress").toString();
					String storeName = jstore.get("storeName").toString();

					JSONArray joffers = (JSONArray) parser.parse(jstore.get("offers").toString());
					for (int j = 0; j < joffers.size(); j++) {
						JSONObject joffer;
						joffer = (JSONObject) joffers.get(j);
						Offer o = new Offer();
						o = convertJsonObjToOfferObj(joffer);
						o.setStoreLat(Double.parseDouble(storeLat));
						o.setStoreLong(Double.parseDouble(storeLong));
						o.setJsonStoreEmail(jsonStoreEmail);
						o.setStoreAddress(storeAddress);
						o.setStoreName(storeName);
						
						storeOffers.add(o);
					}
					// output += storeLat + " " + storeLong + " " +
					// jsonStoreEmail + " " + storeOffers.toString() + "\n";
				}
			}
			// return output;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return storeOffers;
	}

	public Vector<Store> callGetStoreWebService() {

		String serviceUrl = "http://8-dot-itdloffers.appspot.com/rest/GetAllStores";
		Vector<Store> stores = new Vector<Store>();
		// String serviceUrl = webServiceLink + "GetAllStores";
		String urlParameters = "";
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			if (object.get("Status").equals("OK")) {

				JSONArray jstores = (JSONArray) parser.parse(object.get("AllStores").toString());
				for (int i = 0; i < jstores.size(); i++) {
					JSONObject jstore;
					jstore = (JSONObject) jstores.get(i);
					stores.add(convertJsonObjToStoreObj(jstore));
				}
				// return stores.toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// return "failed";
		return stores;
	}

	public Store convertJsonObjToStoreObj(JSONObject jsonObj) {
		return new Store(
				jsonObj.get("name").toString(), 
				jsonObj.get("email").toString(),
				jsonObj.get("password").toString(), 
				jsonObj.get("address").toString(),
				Double.parseDouble(jsonObj.get("latitude").toString()),
				Double.parseDouble(jsonObj.get("longitude").toString()));
	}

	public Vector<Offer> callGetOffersWebService() throws JSONException {
		Vector<Offer> storeOffers = new Vector<Offer>();
		String serviceUrl = "http://8-dot-itdloffers.appspot.com/rest/GetOffersService";
		String urlParameters = "";
		System.out.println(urlParameters);
		String retJson = Connection.connect(serviceUrl, urlParameters, "POST",
				"application/x-www-form-urlencoded;charset=UTF-8");
		JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(retJson);
			JSONObject object = (JSONObject) obj;
			// String output = "";
			if (object.get("Status").equals("OK")) {
				JSONArray jstores = (JSONArray) parser.parse(object.get("AllStores").toString());
				for (int i = 0; i < jstores.size(); i++) {
					JSONObject jstore;
					jstore = (JSONObject) jstores.get(i);

					String storeLat = jstore.get("latitude").toString();
					String storeLong = jstore.get("longitude").toString();
					String jsonStoreEmail = jstore.get("storeEmail").toString();
					String storeAddress = jstore.get("storeAddress").toString();
					String storeName = jstore.get("storeName").toString();

					
					JSONArray joffers = (JSONArray) parser.parse(jstore.get("offers").toString());
					for (int j = 0; j < joffers.size(); j++) {
						JSONObject joffer;
						joffer = (JSONObject) joffers.get(j);
						Offer o = new Offer();
						o = convertJsonObjToOfferObj(joffer);
						o.setStoreLat(Double.parseDouble(storeLat));
						o.setStoreLong(Double.parseDouble(storeLong));
						o.setJsonStoreEmail(jsonStoreEmail);
						o.setStoreAddress(storeAddress);
						o.setStoreName(storeName);
						storeOffers.add(o);
					}
					// output += storeLat + " " + storeLong + " " +
					// jsonStoreEmail + " " + storeOffers.toString() + "\n";
				}
			}
			// return output;
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return storeOffers;
	}

	public Offer convertJsonObjToOfferObj(JSONObject jsonObj) throws JSONException, ParseException {
		TextCategorization txtCat = new TextCategorization();
		String offerCat = jsonObj.get("CategoryName").toString();
		offerCat = txtCat.callTextCategoryAPI(offerCat);
	//	System.out.println("MMMMMMMMMMMMMMMMMMMMMMMMM   "+offerCat +"   offers ID:  "+jsonObj.get("OfferID").toString());
		return new Offer(jsonObj.get("StoreID").toString(), jsonObj.get("OfferID").toString(),
				offerCat, jsonObj.get("Content").toString(),
				jsonObj.get("StartDate").toString(), jsonObj.get("EndDate").toString());
	}

	public Vector<UserInialWeights> sortUserInitialWeights(Vector<UserInialWeights> userInitialWeights) {
		for (int i = 0; i < userInitialWeights.size(); i++) {
			for (int j = 0; j < userInitialWeights.size(); j++) {
				if (userInitialWeights.get(i).getInialWeight() > userInitialWeights.get(j).getInialWeight()) {
					UserInialWeights uiw = new UserInialWeights();
					uiw = userInitialWeights.get(i);
					userInitialWeights.set(i, userInitialWeights.get(j));
					userInitialWeights.set(j, uiw);
				}
			}
		}
		return userInitialWeights;

	}
	// public Vector<Offer> setCategoriesToOffers(Vector<Offer> offers,
	// Vector<Store>)
	// {
	// for (int i = 0; i < offers.size(); i++) {
	//
	// }
	//
	// }

	public Offer getMatchedOffer(Vector<Offer> offers, String categoryName) {
		
		if(offers!=null)
		{
			
		}
		System.out.println();
		for (int i = 0; i < offers.size(); i++) {
			if(offers.get(i).getCategory().toLowerCase().trim().equals(categoryName.toLowerCase().trim()))
			{
				return offers.get(i);
				
			}
		}
		return null;

	}
	
	public Vector<UserInialWeights> getTop3PreferedCategories(String userID)
	{
		RankingInputsModel rankModel = new RankingInputsModel();
		Vector<UserInialWeights> userPreferences= rankModel.getUserInitialWeightsByUserID(userID);
		
		int limit = 3;
		Vector<UserInialWeights> sortedPreferences = new Vector<UserInialWeights>();
		Vector<UserInialWeights> top3 = new Vector<UserInialWeights>();

		sortedPreferences = sortUserInitialWeights(userPreferences);
		for (int i = 0; i < limit; i++) {
			top3.add(sortedPreferences.get(i));
		}
		return top3;
		
		
	}
}
