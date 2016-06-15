package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.json.simple.JSONArray;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import dataEntities.CategoryCountOfSources;
import dataEntities.NoteEntity;
import dataEntities.PairComparison;
import dataEntities.UserInialWeights;

public class DefinedCategories {

	/*
	 * this function reads the predefined set of categories which we will
	 * consider in the project
	 * 
	 * @author Esraa Salem
	 * 
	 * @since Friday 10-06-2016
	 */
	public Vector<String> readFile() {

		String fileName = "C:\\Users\\Esraa\\Coding workspace\\GP_IntelligentToDoList\\category.txt";
		Vector<String> cat = new Vector<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {

				addAllDefinedCategories(line.toLowerCase().trim());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cat;
	}

	/**
	 * add a category to the data entity 'Category'
	 * 
	 * @author Esraa Salem
	 * @since Friday 10-06-2016
	 * @param categoryName
	 */
	public boolean addAllDefinedCategories(String categoryName) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			Entity entity = new Entity("Category");

			entity.setProperty("categoryName", categoryName);

			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	/**
	 * select category by name given its category ID
	 * 
	 * @param categoryID
	 *            the unique Identifier to the category name
	 * @author Esraa Salem
	 * @since Friday 10-06-2016
	 */

	public String getCategoryNameByID(String categoryID) {
		String categoryName = "";
		Key k = KeyFactory.createKey("Category", Long.parseLong(categoryID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity category = datastore.get(k);
			categoryName = category.getProperty("categoryName").toString();

			txn.commit();

		} catch (EntityNotFoundException e) {
			return "";
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

		return categoryName;

	}

	/**
	 * select category ID given its category name
	 * 
	 * @param category
	 *            name
	 * @author Esraa Salem
	 * @since Friday 10-06-2016
	 */

	public String getCategoryIDByName(String categoryName) {

		String categoryID = "";
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter propertyFilter = new FilterPredicate("categoryName", FilterOperator.EQUAL,
				categoryName.trim().toLowerCase());
		Query gaeQuery = new Query("Category").setFilter(propertyFilter);
		PreparedQuery pq = datastore.prepare(gaeQuery);

		Vector<Entity> e = new Vector<Entity>();
		for (Entity entity : pq.asIterable()) {
			e.add(entity);
		}
		if (e.size() > 0) {
			Entity entity = e.get(0);
			categoryID = String.valueOf(entity.getKey().getId());
		}
		
		return categoryID;
	}

	/**
	 * select all defined categories in vector of (CategoryCuntOfSources) from
	 * table category not userInitial Weights
	 * 
	 * @author Esraa Salem
	 * @since Monday 13-06-2016
	 */

	public Vector<CategoryCountOfSources> selectAllCategory() {

		Vector<CategoryCountOfSources> allCategories = new Vector<CategoryCountOfSources>();

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

		Query gaeQuery = new Query("Category");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		for (Entity entity : pq.asIterable()) {

			CategoryCountOfSources category = new CategoryCountOfSources();
			category.setCategoryName(String.valueOf(entity.getProperty("categoryName")).trim().toLowerCase());
			category.setCategoryID(String.valueOf(entity.getKey().getId()));

			allCategories.add(category);

		}

		return allCategories;

	}
}
