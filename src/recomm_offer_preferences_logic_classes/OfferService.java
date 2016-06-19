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

	public Vector<String> getStoreCategories(Vector<Offer> offers, String storeEmail) {
		Vector<String> storeCategories = new Vector<String>();
		for (int i = 0; i < offers.size(); i++) {

			if (offers.get(i).getStoreID().trim().toLowerCase().equals(storeEmail.toLowerCase().trim())) {
				// Here you may need the categorization API to double check on
				// the category type
				storeCategories.add(offers.get(i).getCategory().trim().toLowerCase());

			}
		}
		return storeCategories;
	}

	@POST
	@Path("/getNearestStoresToUserService")
	public String getNearestStoresToUserService(@FormParam("userID") String userID)
			throws ParseException, JSONException {
		JSONObject jsonObj = new JSONObject();
		JSONArray resultNearestStores = new JSONArray();
		NoteCRUDOperations notefetch = new NoteCRUDOperations();
		Vector<NoteEntity> shoppingNotesOnly = new Vector<NoteEntity>();
		shoppingNotesOnly = notefetch.getShoppingNotesONLY(userID);

		System.out.println("***********************************************");
		System.out.println("*******SHOPPING NOTES ONLY*******************");
		System.out.println("***********************************************");
		TextCategorization textCat = new TextCategorization();

		for (int i = 0; i < shoppingNotesOnly.size(); i++) {
			String productCategory = ((ShoppingNoteEntity) shoppingNotesOnly.get(i)).getProductCategory();
			System.out.println("JJJJJJJJJJJJJJJ   " + productCategory);
			productCategory = textCat.callTextCategoryAPI(productCategory);
			((ShoppingNoteEntity) shoppingNotesOnly.get(i)).setProductCategory(productCategory);

			System.out.println(shoppingNotesOnly.get(i).toString());
		}

		if (shoppingNotesOnly.size() > 0) {

			OfferRecommender offerRecommendaer = new OfferRecommender();

			Vector<Store> allstores = new Vector<Store>();
			allstores = offerRecommendaer.callGetStoreWebService();
			System.out
					.println("----------------------------ALL Store ------------------------------" + allstores.size());
			for (int i = 0; i < allstores.size(); i++) {
				System.out.println(allstores.get(i).toString());
			}
			Vector<Offer> offers = new Vector<Offer>();
			offers = offerRecommendaer.callGetAllOffersWebService();
			System.out.println("----------------------------All Offers------------------------------" + offers.size());
			for (int i = 0; i < offers.size(); i++) {
				System.out.println(offers.get(i).toString());
			}

			System.out.println("Sizeeeeeeeeee " + allstores.size());

			for (int i = 0; i < allstores.size(); i++) {
				Vector<String> storeCategories = new Vector<String>();
				for (int j = 0; j < offers.size(); j++) {

					String storeEmail = allstores.get(i).getEmail();
					String offerStoreID = offers.get(j).getStoreID();
					// System.out.println("############ "+storeEmail+ "
					// "+offerStoreID);
					if (storeEmail.equals(offerStoreID)) {

						if (offers.get(j).getCategory().toLowerCase().trim().equals("")
								|| offers.get(j).getCategory().toLowerCase().trim().equals("nocategory")) {

						} else

						{
							storeCategories.add(offers.get(j).getCategory());
						}

					}
				}
				allstores.get(i).setStoreCategory(storeCategories);

				// storeCategories = getStoreCategories(offers,
				// allstores.get(i).getEmail().trim().toLowerCase());
				// System.out.println("########### " +
				// storeCategories.toString());
				// allstores.get(i).setStoreCategory(storeCategories);
			}
			System.out.println(
					"#####################-ALL Store WITH categories------------------------------" + allstores.size());
			for (int i = 0; i < allstores.size(); i++) {

				System.out.println("Nour Size = " + allstores.get(i).getStoreVecSize());

				if (allstores.get(i).getStoreVecSize() == 0) {
					allstores.remove(i);
				}
				// System.out.println(allstores.get(i).toString());
			}
			System.out.println(
					"&&&&&&&&&&&&&&&&&&&&&&&ALL Store AFTER removal------------------------------" + allstores.size());
			for (int i = 0; i < allstores.size(); i++) {

				System.out.println(allstores.get(i).toString());
			}

			for (int i = 0; i < allstores.size(); i++) {

				System.out.println("Esraa Size = " + allstores.get(i).getStoreVecSize());
				if (allstores.get(i).getStoreVecSize() == 0) {
					allstores.remove(i);
				}
				// System.out.println(allstores.get(i).toString());
			}
///you are here
			System.out.println("NNNNNNNNNNNN   " + allstores.size());

			for (int i = 0; i < allstores.size(); i++) {

				System.out.println(allstores.get(i).toString());
			}
			Vector<NearestStore> nearestStore = new Vector<NearestStore>();
			for (int i = 0; i < allstores.size(); i++) {
				Vector<String> notes = new Vector<String>();
				for (int k = 0; k < shoppingNotesOnly.size(); k++) {
					ShoppingNoteEntity sh = (ShoppingNoteEntity) shoppingNotesOnly.get(k);
					if (allstores.get(i).isCategoryFound(sh.getProductCategory().trim().toLowerCase())) {
					notes.add(sh.getProductToBuy().trim().toLowerCase());
					
					}
					
				}
				if(notes.size()>0)
				{
					NearestStore ns = new NearestStore();
					ns.setStoreName(allstores.get(i).getName());
					ns.setLat(allstores.get(i).getLat());
					ns.setLongt(allstores.get(i).getLon());
					ns.setListOfUserShoppingNotes(notes);
					ns.setStoreAddress(allstores.get(i).getAddress());
					ns.setStoreEmail(allstores.get(i).getEmail());
					ns.setStoreCategories(allstores.get(i).getStoreAllCategories());
					//ns.setUserProductToBuy(sh.getProductToBuy());
					//ns.setCategory(sh.getProductCategory());

					// System.out.println("---------------------- " +
					// ns.toString());
					nearestStore.add(ns);
				}
			}
			
		

			if (nearestStore.size() > 0) {
				resultNearestStores = convertVectorNearestStoresToJsonArrSTR(nearestStore);

				jsonObj.put("Status", "OK");
				jsonObj.put("result", resultNearestStores);
				jsonObj.put("resultSize", resultNearestStores.size());
			} else {
				JSONArray tempArr = new JSONArray();
				jsonObj.put("Status", "Failed");
				jsonObj.put("result", tempArr);
				jsonObj.put("resultSize", 0);
			}
		} else {
			JSONArray tempArr = new JSONArray();
			jsonObj.put("Status", "Failed");
			jsonObj.put("result", tempArr);
			jsonObj.put("resultSize", 0);
		}

		return jsonObj.toString();

	}

	@SuppressWarnings("unchecked")
	public JSONArray convertVectorNearestStoresToJsonArrSTR(Vector<NearestStore> nearestStores) {

		JSONArray jsonarray = new JSONArray();

		for (int i = 0; i < nearestStores.size(); i++) {
			JSONObject obj = new JSONObject();
			NearestStore n = new NearestStore();
			n = nearestStores.get(i);
			obj.put("storeName", n.getStoreName());
			obj.put("storeAddress",n.getStoreAddress());
			//obj.put("userProductToBuy", n.getUserProductToBuy());
			obj.put("lat", n.getLat());
			obj.put("longt", n.getLongt());
			//obj.put("category", n.getCategory());

			JSONArray noteList = new JSONArray();
			Vector<String> userShoppingNotes = new Vector<String>();
			userShoppingNotes = n.getListOfUserShoppingsNote();
			for (int j = 0; j < userShoppingNotes.size(); j++) {
				noteList.add(userShoppingNotes.get(j));
			}
			
			JSONArray listOfcat = new JSONArray();
			Vector<String> listOfCatVec = new Vector<String>();
			listOfCatVec = n.getStoreCategories();
			for (int j = 0; j < listOfCatVec.size(); j++) {
				listOfcat.add(listOfCatVec.get(j));
			}
			obj.put("listOfStoreCategories", listOfcat);

			obj.put("listOfShoppingNote", noteList);
			obj.put("storeEmail", n.getStoreEmail());
			jsonarray.add(obj);
		}
		return jsonarray;
	}

	@POST
	@Path("/getTop3Preferences")
	public String getTop3Preferences(@FormParam("userID") String userID) throws JSONException, ParseException {

		OfferRecommender offerrecommender = new OfferRecommender();

		Vector<UserInialWeights> userTop3Pref = offerrecommender.getTop3PreferedCategories(userID);
		String result = convertUserInterestToJsonArrSTR(userTop3Pref);
		JSONObject res = new JSONObject();
		if (userTop3Pref.size() > 0 && result != null) {
			res.put("status", "OK");
			res.put("result", result);

		} else {
			res.put("status", "Failed");
			res.put("result", "");
		}
		return res.toString();
	}

	// JSONArray jsonarray = new JSONArray();
	//
	// for (int i = 0; i < offers.size(); i++) {
	// JSONObject obj = new JSONObject();
	// Offer o = new Offer();
	// o = offers.get(i);
	// obj.put("offerID", o.getOfferID());
	// obj.put("category", o.getCategory().trim().toLowerCase());
	// obj.put("content", o.getContent());
	// obj.put("startDate", o.getStartDate());
	// obj.put("endDate", o.getEndDate());
	//
	// obj.put("storeID", o.getStoreID());
	// obj.put("storeLat", o.getStoreLat());
	// obj.put("storeLong", o.getStoreLong());
	// obj.put("jsonStoreEmail", o.getJsonStoreEmail());
	// jsonarray.add(obj);
	//
	// }
	// return jsonarray.toString();
	public String convertUserInterestToJsonArrSTR(Vector<UserInialWeights> u) {
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

		// System.out.println("BEfore sorting");
		// for (int i = 0; i < userPreferences.size(); i++) {
		// System.out.println(userPreferences.get(i).toString());
		// }
		Vector<Offer> offers = new Vector<Offer>();
		offers = offerrecommender.callGetOffersWebService();
		// for (int i = 0; i < offers.size(); i++) {
		// System.out.println(offers.get(i).toString());
		// }

		sortedPreferences = offerrecommender.sortUserInitialWeights(userPreferences);

		// if(sortedPreferences != null)
		// {
		// System.out.println("sorted NOT NULL");
		// for (int i = 0; i < sortedPreferences.size(); i++) {
		// System.out.println(sortedPreferences.get(i).toString());
		// }
		// }
		// else
		// {
		// System.out.println("sortedPreferences is NULL");
		// }

		Vector<Offer> preferedOffers = new Vector<Offer>();
		int limit = 0;
		if (sortedPreferences.size() >= 3) {
			limit = 3;
		} else {
			limit = sortedPreferences.size();
		}
		/**
		 * for (int i = 0; i < limit; i++) {
		 * 
		 * Offer o = new Offer();
		 * 
		 * o = offerrecommender.getMatchedOffer(offers,
		 * sortedPreferences.get(i).getCategoryName().trim().toLowerCase());
		 * preferedOffers.add(o);
		 * 
		 * }
		 **/
		// TextCategorization textCategory = new TextCategorization();
		for (int i = 0; i < limit; i++) {

			String userInterest = sortedPreferences.get(i).getCategoryName();
			// System.out.println("userInterest = "+userInterest);
			for (int j = 0; j < offers.size(); j++) {
				// String offersCategory =
				// textCategory.callTextCategoryAPI(offers.get(j).getCategory());
				String offersCategory = offers.get(j).getCategory();
				// System.out.println("EEEEEEEEEEEEEE "+offersCategory);
				if (userInterest.equals(offersCategory)) {
					preferedOffers.add(offers.get(j));
				}

			}

		}

		// for (int i = 0; i < preferedOffers.size(); i++) {
		// System.out.println(preferedOffers.get(i).toString());
		// }
		JSONArray result = convertVectorOfferToJsonArrSTR(preferedOffers);

		JSONObject o = new JSONObject();
		o.put("result", result);
		o.put("resultSize", result.size());
		o.put("status", "OK");

		return o.toString();

		// return "offer testing";
	}

	@SuppressWarnings("unchecked")
	public JSONArray convertVectorOfferToJsonArrSTR(Vector<Offer> offers) {

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

			obj.put("storeAddress", o.getStoreAddress());

			obj.put("storeName", o.getStoreName());

			jsonarray.add(obj);

		}
		return jsonarray;
	}

}
