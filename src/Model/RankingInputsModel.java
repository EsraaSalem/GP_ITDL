package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import TextCategorization_logic_classes.TextCategorization;
import dataEntities.PairComparison;
import dataEntities.UserInialWeights;

public class RankingInputsModel {

	public void updateUserInterestAfterRunAlgo(Vector<UserInialWeights> userInitialWeights) {

		for(int i = 0 ; i < userInitialWeights.size() ; i++)
		{
			userInitialWeights.get(i).incrementTest();
			updatePreference(userInitialWeights.get(i));
		}
	}
	public void updatePreference(UserInialWeights u)
	{
		Key k = KeyFactory.createKey("userInitialWeights", Long.parseLong(u.getCategoryRecordID()));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("userID", u.getUserID());
			note.setProperty("categoryID", u.getCategoryID());
			note.setProperty("categoryName", u.getCategoryName());
			note.setProperty("initialWeight", u.getInialWeight());

			datastore.put(note);
			txn.commit();
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	/**
	 * this is the final function which you will use in the ranking algrithm
	 * getUserInitialWeightsByUserID
	 **/
	public Vector<UserInialWeights> getUserInitialWeightsByUserID(String userID) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Vector<UserInialWeights> userInitialWeights = new Vector<UserInialWeights>();

		Query gaeQuery = new Query("userInitialWeights");
		PreparedQuery pq = datastore.prepare(gaeQuery);

		Filter propertyFilter = new FilterPredicate("userID", FilterOperator.EQUAL, userID.trim());

		Query q = new Query("userInitialWeights").setFilter(propertyFilter);
		pq = datastore.prepare(q);

		for (Entity entity : pq.asIterable()) {
			
			UserInialWeights uiw = new UserInialWeights();
			uiw.setCategoryRecordID(String.valueOf(entity.getKey().getId()));
			
			uiw.setCategoryID(entity.getProperty("categoryID").toString().trim());
			uiw.setCategoryName(entity.getProperty("categoryName").toString().trim());

			uiw.setUserID(entity.getProperty("userID").toString().trim());
			uiw.setInialWeight(Double.parseDouble(entity.getProperty("initialWeight").toString().trim()));
			userInitialWeights.add(uiw);
		}
		return userInitialWeights;
	}

	/**
	 * this is the final function which you will use in the ranking algrithm
	 * getInputSourcesSignificance
	 **/
	public Vector<PairComparison> getInputSourcesSignificance() {

		Vector<PairComparison> pcVector = new Vector<PairComparison>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("InputSourcesSignificance");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			PairComparison pc = new PairComparison();
			pc.setCategoryID1(Integer.parseInt(entity.getProperty("categoryID1").toString()));
			pc.setCategoryID2(Integer.parseInt(entity.getProperty("categoryID2").toString()));
			pc.setInputSource1(entity.getProperty("inputSource1").toString());
			pc.setInputSource2(entity.getProperty("inputSource2").toString());
			pc.setCategory1WeightVsCategory2(
					Double.parseDouble(entity.getProperty("category1WeightVsCategory2").toString()));
			pcVector.add(pc);

		}

	
		return pcVector;

	}

	/**
	 * this is a test function
	 **/
	public String getInputSourcesSignificanceSTR() {

		Vector<PairComparison> pcVector = new Vector<PairComparison>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("InputSourcesSignificance");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			PairComparison pc = new PairComparison();
			pc.setCategoryID1(Integer.parseInt(entity.getProperty("categoryID1").toString()));
			pc.setCategoryID2(Integer.parseInt(entity.getProperty("categoryID2").toString()));
			pc.setInputSource1(entity.getProperty("inputSource1").toString());
			pc.setInputSource2(entity.getProperty("inputSource2").toString());
			pc.setCategory1WeightVsCategory2(
					Double.parseDouble(entity.getProperty("category1WeightVsCategory2").toString()));
			pcVector.add(pc);

		}

		return pcVector.toString();

	}

	/**
	 * just to add InputSourcesSignificance rates in the local host when read it
	 * from file
	 **/
	public Vector<PairComparison> readFile() {

//		InputStream input = this.getClass().getClassLoader()
//				.getResourceAsStream("war/WEB-INF/classes/pairwisecomparison.txt");
		// "src\\inputs\\pairwisecomparison.txt"
		String fileName = "C:\\Users\\Esraa\\Coding workspace\\GP_IntelligentToDoList\\pairwisecomparison.txt";
		// String relativeWebPath = "/STUFF/read.txt";
		// InputStream input =
		// getServletContext().getResourceAsStream(relativeWebPath);
		//

		Vector<PairComparison> pcVector = new Vector<PairComparison>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			// try (BufferedReader br = new BufferedReader(new
			// InputStreamReader(input))) {

			String line;
			while ((line = br.readLine()) != null) {

				line = line.toLowerCase().trim();
				String[] info = line.split(" ");
				PairComparison pc = new PairComparison();
				pc.setCategoryID1(Integer.parseInt(info[0].trim()));
				pc.setCategoryID2(Integer.parseInt(info[1].trim()));
				pc.setInputSource1(info[2].trim());
				pc.setInputSource2(info[3].trim());
				pc.setCategory1WeightVsCategory2(Double.parseDouble(info[4].trim()));
				pcVector.add(pc);
				addPairWiseComparison(pc);

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return pcVector;

	}

	/**
	 * just to add InputSourcesSignificance to google app engine DB
	 **/

	public boolean addPairWiseComparison(PairComparison pc) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			Entity entity = new Entity("InputSourcesSignificance");

			entity.setProperty("categoryID1", String.valueOf(pc.getCategoryID1()));
			entity.setProperty("categoryID2", String.valueOf(pc.getCategoryID2()));
			entity.setProperty("inputSource1", String.valueOf(pc.getInputSource1()));
			entity.setProperty("inputSource2", String.valueOf(pc.getInputSource2()));
			entity.setProperty("category1WeightVsCategory2", String.valueOf(pc.getCategory1WeightVsCategory2()));
			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public boolean addUserInitialWeights(String userID, String jsonArraySTR) throws ParseException, JSONException {
		JSONParser parser = new JSONParser();
		String artCategory = "art and entertainment";
		String hobbiesCategory = "hobbies and interests";
		TextCategorization tc = new TextCategorization();
		JSONArray jsonArray = (JSONArray) parser.parse(jsonArraySTR.toString());

		
		double sumArt = 0.0;
		int counter = 0;

		for (int i = 0; i < jsonArray.size(); i++) {

			JSONObject o = (JSONObject) jsonArray.get(i);
			String categoryName = o.get("categoryName").toString();
			String definedCategory = tc.callTextCategoryAPI(categoryName);

			if (definedCategory.equals(artCategory)) {
				double ratio = Double.parseDouble(o.get("initialWeight").toString());
				sumArt += ratio;
				counter++;

				o.put("categoryName", definedCategory);
				jsonArray.set(i, o);
			}
			if (definedCategory.equals(hobbiesCategory)) {
				o.put("categoryName", definedCategory);
				jsonArray.set(i, o);

			}

			
		}
		JSONArray newJsonArray = new JSONArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject o = (JSONObject) jsonArray.get(i);
			String categoryName = o.get("categoryName").toString();
			if (!categoryName.equals(artCategory)) {
				DefinedCategories dc = new DefinedCategories();
				String categoryID = dc.getCategoryIDByName(categoryName);
				o.put("categoryID", categoryID);
				newJsonArray.add(o);
			}
		}

	JSONObject o2 = new JSONObject();

		String num1Str = String.format("%.5g%n", (double) (sumArt / counter));

		double num1 = Double.parseDouble(num1Str);

		o2.put("categoryName", artCategory);

		o2.put("initialWeight", num1);
		DefinedCategories dc = new DefinedCategories();
		String categoryID = dc.getCategoryIDByName(String.valueOf(o2.get("categoryName")));
		o2.put("categoryID", categoryID);
		newJsonArray.add(o2);

		
		for (int i = 0; i < newJsonArray.size(); i++) {
			JSONObject o = (JSONObject) newJsonArray.get(i);
			boolean result = addUserInitialWeightsInDB(userID, o);

			if (result) {
				System.out.println(i + "   DoneSuccessfully");
			} else {

				System.out.println(i + "  ErrorOcurred");

			}
		}

		return true;
	}

	public boolean addUserInitialWeightsInDB(String userID, JSONObject obj) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		Entity entity = new Entity("userInitialWeights");

		String categoryName = String.valueOf(obj.get("categoryName"));
		String categoryID = String.valueOf(obj.get("categoryID"));
		String ratio = String.valueOf(obj.get("initialWeight"));

		entity.setProperty("userID", userID);
		entity.setProperty("categoryName", categoryName);
		entity.setProperty("categoryID", categoryID);
		entity.setProperty("initialWeight", ratio);

		datastore.put(entity);
		txn.commit();


		return true;

	}


}
