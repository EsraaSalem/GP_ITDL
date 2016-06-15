package recomm_offer_preferences_logic_classes;

import java.util.ArrayList;
import java.util.Vector;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.google.appengine.labs.repackaged.org.json.JSONException;

import Model.RankingInputsModel;
import TextCategorization_logic_classes.TextCategorization;
import dataEntities.InputType;
import dataEntities.NearestStore;
import dataEntities.NoteEntity;
import dataEntities.Offer;
import dataEntities.ShoppingNoteEntity;
import dataEntities.Store;
import dataEntities.UserInialWeights;
import note_crud_operation_logic_classes.NoteCRUDOperations;
import recomm_reranking_algorithm_logic_classes.RerankingAlgorithmLogic;

@Path("/")
@Produces(MediaType.TEXT_PLAIN)
public class OfferService {
	
	
	@POST
	@Path("/getTop3Preferences")
	public String getTop3Preferences(@FormParam("userID") String userID) throws JSONException, ParseException {

		OfferRecommender offerrecommender = new OfferRecommender();

		Vector<UserInialWeights> userTop3Pref = offerrecommender.getTop3PreferedCategories(userID);
		String result = convertUserInterestToJsonArrSTR(userTop3Pref);
		JSONObject res = new JSONObject();
		if(userTop3Pref.size()>0 && result != null)
		{
			res.put("status", "OK");
			res.put("result", result);
			
		}
		else
		{
			res.put("status", "Failed");
			res.put("result", "");
		}
		return res.toString();
	}	
//	JSONArray jsonarray = new JSONArray();
//
//	for (int i = 0; i < offers.size(); i++) {
//		JSONObject obj = new JSONObject();
//		Offer o = new Offer();
//		o = offers.get(i);
//		obj.put("offerID", o.getOfferID());
//		obj.put("category", o.getCategory().trim().toLowerCase());
//		obj.put("content", o.getContent());
//		obj.put("startDate", o.getStartDate());
//		obj.put("endDate", o.getEndDate());
//
//		obj.put("storeID", o.getStoreID());
//		obj.put("storeLat", o.getStoreLat());
//		obj.put("storeLong", o.getStoreLong());
//		obj.put("jsonStoreEmail", o.getJsonStoreEmail());
//		jsonarray.add(obj);
//
//	}
//	return jsonarray.toString();
	public String convertUserInterestToJsonArrSTR(Vector<UserInialWeights> u)
	{
		JSONArray jsonArr = new JSONArray();
		for (int i = 0; i < u.size(); i++) {
			JSONObject obj = new JSONObject();
			UserInialWeights uiw = new UserInialWeights();
			uiw = u.get(i);
			obj.put("inialWeight", String.valueOf(uiw.getInialWeight()));
			obj.put("categoryID", uiw.getCategoryID());
			obj.put("categoryName", uiw.getCategoryName());
			obj.put("userID", uiw.getUserID());
			obj.put("categoryRecordID", uiw.getCategoryRecordID());
			jsonArr.add(obj);
		}
		return jsonArr.toString();
	}

	@POST
	@Path("/getOfferService")
	public String getOfferService(@FormParam("userID") String userID) throws JSONException, ParseException {

		OfferRecommender offerrecommender = new OfferRecommender();
		Vector<UserInialWeights> userPreferences = new Vector<UserInialWeights>();
		Vector<UserInialWeights> sortedPreferences = new Vector<UserInialWeights>();

		RankingInputsModel rankModel = new RankingInputsModel();
		userPreferences = rankModel.getUserInitialWeightsByUserID(userID);
		
//		System.out.println("BEfore sorting");
//		for (int i = 0; i < userPreferences.size(); i++) {
//			System.out.println(userPreferences.get(i).toString());
//		}
		Vector<Offer> offers = new Vector<Offer>();
		 offers = offerrecommender.callGetOffersWebService();
//		 for (int i = 0; i < offers.size(); i++) {
//			System.out.println(offers.get(i).toString());
//		}
		 
		sortedPreferences = offerrecommender.sortUserInitialWeights(userPreferences);
		
//		if(sortedPreferences != null)
//		{
//			System.out.println("sorted  NOT NULL");
//			for (int i = 0; i < sortedPreferences.size(); i++) {
//				System.out.println(sortedPreferences.get(i).toString());
//			}
//		}
//		else
//		{
//			System.out.println("sortedPreferences is NULL");
//		}
		
		Vector<Offer> preferedOffers = new Vector<Offer>();
		int limit = 0;
		if (sortedPreferences.size() >= 3) {
			limit = 3;
		} else {
			limit = sortedPreferences.size();
		}
/**
		for (int i = 0; i < limit; i++) {

			Offer o = new Offer();
			
			o = offerrecommender.getMatchedOffer(offers, sortedPreferences.get(i).getCategoryName().trim().toLowerCase());
			preferedOffers.add(o);

		}
**/
		//TextCategorization textCategory  = new TextCategorization();
		for (int i = 0; i < limit; i++) {

			String userInterest = sortedPreferences.get(i).getCategoryName();
			//System.out.println("userInterest = "+userInterest);
			for (int j = 0; j < offers.size(); j++) {
				//String offersCategory = textCategory.callTextCategoryAPI(offers.get(j).getCategory());
				String offersCategory =offers.get(j).getCategory();
				//System.out.println("EEEEEEEEEEEEEE   "+offersCategory);
				if(userInterest.equals(offersCategory))
				{
					preferedOffers.add(offers.get(j));
				}
				
			}

		}

//		for (int i = 0; i < preferedOffers.size(); i++) {
//			System.out.println(preferedOffers.get(i).toString());
//		}
		String result = convertVectorOfferToJsonArrSTR(preferedOffers);
		
		 
		return result;
		
		 
		// return "offer testing";
	}

	@SuppressWarnings("unchecked")
	public String convertVectorOfferToJsonArrSTR(Vector<Offer> offers) {

		JSONArray jsonarray = new JSONArray();

		for (int i = 0; i < offers.size(); i++) {
			JSONObject obj = new JSONObject();
			Offer o = new Offer();
			o = offers.get(i);
			obj.put("offerID", o.getOfferID());
			obj.put("category", o.getCategory().trim().toLowerCase());
			obj.put("content", o.getContent());
			obj.put("startDate", o.getStartDate());
			obj.put("endDate", o.getEndDate());

			obj.put("storeID", o.getStoreID());
			obj.put("storeLat", o.getStoreLat());
			obj.put("storeLong", o.getStoreLong());
			obj.put("jsonStoreEmail", o.getJsonStoreEmail());
			jsonarray.add(obj);

		}
		return jsonarray.toString();
	}
	
	public Vector<String> getStoreCategories(Vector<Offer> offers, String storeEmail )
	{
		Vector<String> storeCategories = new Vector<String>();
		for (int i = 0; i < offers.size(); i++) {

			if(offers.get(i).getJsonStoreEmail().trim().toLowerCase().equals(storeEmail.toLowerCase().trim()))
			{
				//Here you may need the categorization API to double check on the category type
				storeCategories.add(offers.get(i).getCategory().trim().toLowerCase());
				
			}
		}
		return storeCategories;
	}
	@POST
	@Path("/getNearestStoresToUserService")
	public String getNearestStoresToUserService(@FormParam("userID") String userID) throws ParseException, JSONException {
	
		OfferRecommender offerRecommendaer = new OfferRecommender();
		
		Vector<Store> allstores = new Vector<Store>();
		allstores = offerRecommendaer.callGetStoreWebService();
		
		
		Vector<Offer> offers = new Vector<Offer>();
		 offers = offerRecommendaer.callGetAllOffersWebService();
		
		for (int i = 0; i < allstores.size(); i++) {
			Vector<String> storeCategories = new Vector<String>();
			storeCategories = getStoreCategories(offers, allstores.get(i).getEmail().trim().toLowerCase());
			allstores.get(i).setStoreCategory(storeCategories);
		}
		
		NoteCRUDOperations notefetch = new NoteCRUDOperations();
		Vector<NoteEntity> shoppingNotesOnly = new Vector<NoteEntity>();
		shoppingNotesOnly = notefetch.getShoppingNotesONLY(userID);
		Vector<NearestStore> nearestStore = new Vector<NearestStore>();
		for (int i = 0; i < shoppingNotesOnly.size(); i++) {
			ShoppingNoteEntity sh = (ShoppingNoteEntity)shoppingNotesOnly.get(i);
			for (int j = 0; j < allstores.size(); j++) {
				if(allstores.get(j).isCategoryFound(sh.getProductCategory().trim().toLowerCase()))
				{
					NearestStore ns = new NearestStore();
					ns.setStoreName(allstores.get(i).getName());
					ns.setLat(allstores.get(i).getLat());
					ns.setLongt(allstores.get(i).getLon());
					ns.setUserProductToBuy(sh.getProductCategory());
					nearestStore.add(ns);
				}
			}
		}
		
		String resultNearestStores = convertVectorNearestStoresToJsonArrSTR(nearestStore);
		
		return resultNearestStores;
		
	}
	@SuppressWarnings("unchecked")
	public String convertVectorNearestStoresToJsonArrSTR(Vector<NearestStore> nearestStores) {

		JSONArray jsonarray = new JSONArray();

		for (int i = 0; i < nearestStores.size(); i++) {
			JSONObject obj = new JSONObject();
			NearestStore n = new NearestStore();
			obj.put("storeName", n.getStoreName());
			obj.put("userProductToBuy", n.getUserProductToBuy());
			obj.put("lat", n.getLat());
			obj.put("longt", n.getLongt());
			jsonarray.add(obj);

		}
		return jsonarray.toString();
	}

}
