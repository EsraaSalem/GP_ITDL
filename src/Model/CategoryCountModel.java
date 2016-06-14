package Model;

import java.util.Vector;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.json.simple.JSONArray;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import dataEntities.CategoryCounts;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;

/**
 * last update 26 April 2016
 * **/
public class CategoryCountModel {

	public boolean isCategoryFound(String category, String userID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("CategoryCount");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Filter propertyFilter = new FilterPredicate("category", FilterOperator.EQUAL, category);
		Filter propertyFilter2 = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
		Filter allConditions = CompositeFilterOperator.and(propertyFilter, propertyFilter2);

		Query q = new Query("CategoryCount").setFilter(allConditions);
		pq = datastore.prepare(q);
		int resultCount = pq.countEntities(FetchOptions.Builder.withDefaults());
		
		System.out.println("ZZZZZZZZZZZZZ  "+resultCount);
		if (resultCount == 0)
			return false;
		
		for (Entity entity : pq.asIterable()) {
			System.out.println(entity.getProperty("userID").toString());
			System.out.println(entity.getProperty("category").toString());
			System.out.println(entity.getProperty("count").toString());
			System.out.println(entity.getKey().toString());
			

		}
		return true;
	}

	public boolean addNewCategory(String category, String userID) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {

			Entity entity = new Entity("CategoryCount");

			entity.setProperty("category", category);
			entity.setProperty("count", "1");
			entity.setProperty("userID", userID);

			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;

	}

	public CategoryCounts getPreviousCategoryCount(String category, String userID) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("CategoryCount");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Filter propertyFilter = new FilterPredicate("category", FilterOperator.EQUAL, category);
		Filter propertyFilter2 = new FilterPredicate("userID", FilterOperator.EQUAL, userID);
		Filter allConditions = CompositeFilterOperator.and(propertyFilter, propertyFilter2);

		Query q = new Query("CategoryCount").setFilter(allConditions);
		pq = datastore.prepare(q);
		int resultCount = pq.countEntities(FetchOptions.Builder.withDefaults());
		int categoryCount = 0;
		System.out.println("resultCount  = " + resultCount);
		CategoryCounts obj = null;
		int counter = 0;
		for (Entity entity : pq.asIterable()) {
			obj = new CategoryCounts();
			counter++;
			obj.setCount(Integer.parseInt(entity.getProperty("count").toString()));
			obj.setCategory(entity.getProperty("category").toString());
			obj.setUserID_DB(entity.getProperty("userID").toString());
			obj.setCategoryID_DB(entity.getKey().getId());

		}
		System.out.println("Couneter = " + counter);

		return obj;
	}

	public boolean updateCategoryCountTable(CategoryCounts obj) {

		long s = obj.getCategoryID_DB();
//		System.out.println("WWWWWWWWWWWWWWWWWWWW  "+s);
//		
//		long x = Long.parseLong(s);
//				System.out.println("LOng number : "+x);
		Key k = KeyFactory.createKey("CategoryCount", s );
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity entity = datastore.get(k);

			entity.setProperty("count", String.valueOf(obj.getCount() + 1));

			datastore.put(entity);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}

	public boolean updateCategoryCount(String category, String userID) {

		boolean isCategoryExists = isCategoryFound(category, userID);
		if (isCategoryExists) {
			CategoryCounts oldCategory = getPreviousCategoryCount(category, userID);
			System.out.println("OLD CATEGORY: " + oldCategory);
			updateCategoryCountTable(oldCategory);
			return true;

		} else {
			addNewCategory(category, userID);
			return true;
		}

		
	}

	public Vector<CategoryCounts> getSpecificUserCategories(String userID) {
		Vector<CategoryCounts> userCategories = new Vector<CategoryCounts>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query gaeQuery = new Query("CategoryCount");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		Filter propertyFilter = new FilterPredicate("userID", FilterOperator.EQUAL, userID);

		Query q = new Query("CategoryCount").setFilter(propertyFilter);
		pq = datastore.prepare(q);
		int resultCount = pq.countEntities(FetchOptions.Builder.withDefaults());
		int categoryCount = 0;
		System.out.println("resultCount  = " + resultCount);

		int counter = 0;
		for (Entity entity : pq.asIterable()) {
			CategoryCounts obj = new CategoryCounts();
			counter++;
			obj.setCount(Integer.parseInt(entity.getProperty("count").toString()));
			obj.setCategory(entity.getProperty("category").toString());
			obj.setUserID_DB(entity.getProperty("userID").toString());
			obj.setCategoryID_DB(entity.getKey().getId());

			userCategories.add(obj);
		}
		System.out.println("Couneter = " + counter);

		System.out.println("SSSSSSSSSSSSSS   All user categories  ");

		for (int i = 0; i < userCategories.size(); i++) {
			System.out.println(userCategories.get(i).toString());
		}
		return userCategories;

	}

}
