package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Vector;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Transaction;
import com.google.apphosting.datastore.DatastoreV4.LookupRequest;

import TextCategorization_logic_classes.TextCategorization;
import dataEntities.PairComparison;

public class RankingInputsModel {

	/**
	 * this is the final function which you will use in the ranking algrithm
	 * **/
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
	 * **/
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
	 * just to add InputSourcesSignificance rates in the local host when read it from file
	 * **/
	public Vector<PairComparison> readFile() {

		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("war/WEB-INF/classes/pairwisecomparison.txt");
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
	 * **/
	
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



	public boolean addUserInitialWeights(String userID, String jsonArraySTR) throws ParseException, JSONException
	{
		JSONParser parser = new JSONParser();

		TextCategorization tc = new TextCategorization();
		JSONArray jsonArray = (JSONArray) parser.parse(jsonArraySTR.toString());
		for(int i = 0 ; i < jsonArray.size(); i++)
		{
			JSONObject o = (JSONObject)jsonArray.get(i);
			String categoryName = o.get("categoryName").toString();
			System.out.println(categoryName);
			String definedCategory = tc.callTextCategoryAPI(categoryName);
			o.put("categoryName", definedCategory);
			System.out.println("AAAAAAAAAAAAAAAAAAAAAA=  "+o.toString());
			
			
		}
		
		System.out.println("111111111111111111111111111");
		for(int i = 0 ; i < jsonArray.size(); i++)
		{
			
			boolean result = add1UserInitialWeightsDB(userID, (JSONObject)jsonArray.get(i));
			if(result == false) return false;
		}
		
		return true;
	}
	public boolean add1UserInitialWeightsDB(String userID, JSONObject jsonObj)
	{
		
		System.out.println("22222222222222222222222");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			Entity entity = new Entity("userInitialWeights");

			
			String categoryName = jsonObj.get("categoryName").toString().trim().toLowerCase();
			DefinedCategories dc = new DefinedCategories();
			String categoryID  = dc.getCategoryIDByName(categoryName);
			entity.setProperty("userID", userID);
			entity.setProperty("categoryID", String.valueOf(categoryID));
			entity.setProperty("categoryName", String.valueOf(jsonObj.get("categoryName").toString().toLowerCase().toString()));
			entity.setProperty("initialWeight", String.valueOf(jsonObj.get("initialWeight").toString()));
			
			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
				return false;
			}
			
		}
		return true;
	}
	













}
