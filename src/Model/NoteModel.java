package Model;

import java.util.List;
import java.util.Vector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

import dataEntities.DeadlineNoteEntity;
import dataEntities.MeetingNoteEntity;
import dataEntities.NoteEntity;
import dataEntities.OrdinaryNoteEntity;
import dataEntities.ShoppingNoteEntity;



public class NoteModel {
	final static String shopping = "Shopping";
	final static String ordinary = "Ordinary";
	final static String deadline = "DeadLine";
	final static String meeting = "Meeting";
	private NoteParser noteParser;

	public NoteModel()
	{
		noteParser = new NoteParser();
	}
	public boolean saveShoppingNoteUpdate(String noteID, String productToBuy, String productCategory) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("productCategory", productCategory);
			note.setProperty("productToBuy", productToBuy);
		
			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean saveDeadlineNoteUpdate(String noteID, String deadLineDate, String deadLineTitle,
			String progressPercentage) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("deadLineDate", deadLineDate);
			note.setProperty("deadLineTitle", deadLineTitle);
			note.setProperty("progressPercentage", progressPercentage);

			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean saveMeetingNoteUpdate(String noteID, String meetingAgenda, String meetingNoteDate,
			String meetingPlace, String meetingTitle, String estimatedTransportTime) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("meetingAgenda", meetingAgenda);
			note.setProperty("meetingNoteDate", meetingNoteDate);
			note.setProperty("meetingPlace", meetingPlace);
			note.setProperty("meetingTitle", meetingTitle);
			note.setProperty("estimatedTransportTime", estimatedTransportTime);

			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean saveOrdinaryNoteUpdate(String noteID, String noteContent) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("noteContent", noteContent);
			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public JSONObject getShoppingNoteToUpdate(String noteID) {
		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			datastore.get(k);
			Entity entity = datastore.get(k);
			JSONObject obj = noteParser.handleShoppingNoteJSONObject(new ShoppingNoteEntity(
					String.valueOf(entity.getKey().getId()), entity.getProperty("userID").toString(),
					java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
					Boolean.valueOf(entity.getProperty("isDone").toString()),
					Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
					entity.getProperty("noteType").toString(), entity.getProperty("productToBuy").toString(),
					entity.getProperty("productCategory").toString()));
			return obj;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject geDeadlineNoteToUpdate(String noteID) {
		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			datastore.get(k);
			Entity entity = datastore.get(k);
			JSONObject obj = noteParser.handleDeadLineNoteJSONObject(
					new DeadlineNoteEntity(Integer.parseInt(String.valueOf(entity.getProperty("progressPercentage"))),
							String.valueOf(entity.getProperty("deadLineTitle")),
							java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("deadLineDate"))),
							String.valueOf(entity.getKey().getId()), String.valueOf(entity.getProperty("userID")),
							java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("creationDate"))),
							Boolean.valueOf(String.valueOf(entity.getProperty("isDone"))),
							Boolean.valueOf(String.valueOf(entity.getProperty("isTextCategorized"))),
							String.valueOf(entity.getProperty("noteType"))));
			return obj;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getMeetingNoteToUpdate(String noteID) {
		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			datastore.get(k);
			Entity entity = datastore.get(k);
			JSONObject obj = noteParser.handleMeetingNoteJSONObject(new MeetingNoteEntity(
					java.sql.Timestamp.valueOf(entity.getProperty("meetingNoteDate").toString()),
					java.sql.Time.valueOf(entity.getProperty("estimatedTransportTime").toString()),
					entity.getProperty("meetingTitle").toString(), entity.getProperty("meetingPlace").toString(),
					entity.getProperty("meetingAgenda").toString(), String.valueOf(entity.getKey().getId()),
					entity.getProperty("userID").toString(),
					java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
					Boolean.valueOf(entity.getProperty("isDone").toString()),
					Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
					entity.getProperty("noteType").toString()));
			return obj;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONObject getOrdinaryNoteToUpdate(String noteID) {
		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		try {
			datastore.get(k);
			Entity entity = datastore.get(k);
			JSONObject obj = noteParser.handleOrdinaryNoteJSONObject(new OrdinaryNoteEntity(
					String.valueOf(entity.getKey().getId()), entity.getProperty("userID").toString(),
					java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
					Boolean.valueOf(entity.getProperty("isDone").toString()),
					Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
					entity.getProperty("noteType").toString(), entity.getProperty("noteContent").toString()));
			return obj;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteAnyTypeOfNotes(String noteID) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("isActive", "false");
			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	
	public boolean noteIsDone(String noteID) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("isDone", "true");
			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
	public boolean noteIsTextCategorized(String noteID) {

		Key k = KeyFactory.createKey("Note", Long.parseLong(noteID));
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			datastore.get(k);
			Entity note = datastore.get(k);
			note.setProperty("isTextCategorized", "true");
			datastore.put(note);
			txn.commit();
			return true;
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean addOrdinaryNote(OrdinaryNoteEntity ordinaryNoteObj) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			Entity entity = new Entity("Note");

			entity.setProperty("userID", ordinaryNoteObj.getUserID());
			entity.setProperty("creationDate", String.valueOf(ordinaryNoteObj.getCreationDate()));
			entity.setProperty("isDone", String.valueOf(ordinaryNoteObj.isDone()));
			entity.setProperty("isTextCategorized", String.valueOf(ordinaryNoteObj.isTextCategorized()));
			entity.setProperty("noteType", ordinaryNoteObj.getNoteType());
			entity.setProperty("noteContent", ordinaryNoteObj.getNoteContent());
			entity.setProperty("isActive", String.valueOf(true));

			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public boolean addShoppingNote(ShoppingNoteEntity shoppingNoteObj) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		try {
			Entity entity = new Entity("Note");

			entity.setProperty("userID", shoppingNoteObj.getUserID());
			entity.setProperty("creationDate", String.valueOf(shoppingNoteObj.getCreationDate()));
			entity.setProperty("isDone", String.valueOf(shoppingNoteObj.isDone()));
			entity.setProperty("isTextCategorized", String.valueOf(shoppingNoteObj.isTextCategorized()));
			entity.setProperty("noteType", shoppingNoteObj.getNoteType());
			entity.setProperty("productCategory", shoppingNoteObj.getProductCategory());
			entity.setProperty("productToBuy", shoppingNoteObj.getProductToBuy());
			entity.setProperty("isActive", String.valueOf(true));

			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public boolean adddeadLineNote(DeadlineNoteEntity deadLineNoteObj) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();

		try {
			Entity entity = new Entity("Note");

			entity.setProperty("userID", deadLineNoteObj.getUserID());
			entity.setProperty("creationDate", String.valueOf(deadLineNoteObj.getCreationDate()));
			entity.setProperty("isDone", String.valueOf(deadLineNoteObj.isDone()));
			entity.setProperty("isTextCategorized", String.valueOf(deadLineNoteObj.isTextCategorized()));
			entity.setProperty("noteType", deadLineNoteObj.getNoteType());
			entity.setProperty("progressPercentage", String.valueOf(deadLineNoteObj.getProgressPercentage()));
			entity.setProperty("deadLineTitle", deadLineNoteObj.getDeadLineTitle());
			entity.setProperty("deadLineDate", String.valueOf(deadLineNoteObj.getDeadLineDate()));

			entity.setProperty("isActive", String.valueOf(true));
			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	public boolean addMeetingNote(MeetingNoteEntity meetingNoteObj) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = datastore.beginTransaction();
		try {
			Entity entity = new Entity("Note");

			entity.setProperty("userID", meetingNoteObj.getUserID());
			entity.setProperty("creationDate", String.valueOf(meetingNoteObj.getCreationDate()));
			entity.setProperty("isDone", String.valueOf(meetingNoteObj.isDone()));
			entity.setProperty("isTextCategorized", String.valueOf(meetingNoteObj.isTextCategorized()));
			entity.setProperty("noteType", meetingNoteObj.getNoteType());
			entity.setProperty("meetingTitle", meetingNoteObj.getMeetingTitle());
			entity.setProperty("meetingAgenda", meetingNoteObj.getMeetingAgenda());
			entity.setProperty("meetingPlace", meetingNoteObj.getMeetingPlace());
			entity.setProperty("estimatedTransportTime", String.valueOf(meetingNoteObj.getEstimatedTransportTime()));
			entity.setProperty("meetingNoteDate", String.valueOf(meetingNoteObj.getmeetingNoteDate()));
			entity.setProperty("isActive", String.valueOf(true));

			datastore.put(entity);
			txn.commit();
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		return true;
	}

	
	public JSONArray getAllNotes(String userID) {

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Vector<NoteEntity> allNotes = new Vector<NoteEntity>();
		JSONArray returnedJson = new JSONArray();
		Query gaeQuery = new Query("Note");
		PreparedQuery pq = datastore.prepare(gaeQuery);
		
		Filter propertyFilter = new FilterPredicate("userID", FilterOperator.EQUAL, userID.trim());
		Filter propertyFilter2 = new FilterPredicate("isDone", FilterOperator.EQUAL, "false");
		Filter propertyFilter3 = new FilterPredicate("isActive", FilterOperator.EQUAL, "true");
		Filter allConditions =
				  CompositeFilterOperator.and(propertyFilter,propertyFilter2, propertyFilter3);

		
		Query q = new Query("Note").setFilter(allConditions);
		pq = datastore.prepare(q);
		
		
		for (Entity entity : pq.asIterable()) {
			entity.setProperty("isActive", "true");
			if (entity.getProperty("noteType").toString().equals(meeting)) {
				MeetingNoteEntity meetingNoteObj = new MeetingNoteEntity(
						java.sql.Timestamp.valueOf(entity.getProperty("meetingNoteDate").toString()),
						java.sql.Time.valueOf(entity.getProperty("estimatedTransportTime").toString()),
						entity.getProperty("meetingTitle").toString(), 
						entity.getProperty("meetingPlace").toString(),
						entity.getProperty("meetingAgenda").toString(), 
						String.valueOf(entity.getKey().getId()),
						entity.getProperty("userID").toString(),
						java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
						Boolean.valueOf(entity.getProperty("isDone").toString()),
						Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
						entity.getProperty("noteType").toString());
				JSONObject object = new JSONObject();
				object = noteParser.handleMeetingNoteJSONObject(meetingNoteObj);
				JSONObject nType_nValues = new JSONObject();
				nType_nValues.put(meeting, object.toJSONString());
				returnedJson.add(nType_nValues);
				allNotes.add(meetingNoteObj);
			} else if (entity.getProperty("noteType").toString().equals(ordinary)) {
				OrdinaryNoteEntity ordniaryNoteObj = new OrdinaryNoteEntity(String.valueOf(entity.getKey().getId()),
						entity.getProperty("userID").toString(),
						java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
						Boolean.valueOf(entity.getProperty("isDone").toString()),
						Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
						entity.getProperty("noteType").toString(), entity.getProperty("noteContent").toString());
				JSONObject object = new JSONObject();
				object = noteParser.handleOrdinaryNoteJSONObject(ordniaryNoteObj);
				JSONObject nType_nValues = new JSONObject();
				nType_nValues.put(ordinary, object.toJSONString());
				returnedJson.add(nType_nValues);

				allNotes.add(ordniaryNoteObj);
			} else if (entity.getProperty("noteType").toString().equals(shopping)) {

				ShoppingNoteEntity shoppingNoteObj = new ShoppingNoteEntity(String.valueOf(entity.getKey().getId()),
						entity.getProperty("userID").toString(),
						java.sql.Timestamp.valueOf(entity.getProperty("creationDate").toString()),
						Boolean.valueOf(entity.getProperty("isDone").toString()),
						Boolean.valueOf(entity.getProperty("isTextCategorized").toString()),
						entity.getProperty("noteType").toString(), entity.getProperty("productToBuy").toString(),
						entity.getProperty("productCategory").toString());
				JSONObject object = new JSONObject();
				object = noteParser.handleShoppingNoteJSONObject(shoppingNoteObj);
				JSONObject nType_nValues = new JSONObject();
				nType_nValues.put(shopping, object.toJSONString());
				returnedJson.add(nType_nValues);

				allNotes.add(shoppingNoteObj);
			} else if (entity.getProperty("noteType").toString().equals(deadline)) {

				DeadlineNoteEntity deadLineNoteObj = new DeadlineNoteEntity(
						Integer.parseInt(String.valueOf(entity.getProperty("progressPercentage"))),
						String.valueOf(entity.getProperty("deadLineTitle")),
						java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("deadLineDate"))),
						String.valueOf(entity.getKey().getId()), String.valueOf(entity.getProperty("userID")),
						java.sql.Timestamp.valueOf(String.valueOf(entity.getProperty("creationDate"))),
						Boolean.valueOf(String.valueOf(entity.getProperty("isDone"))),
						Boolean.valueOf(String.valueOf(entity.getProperty("isTextCategorized"))),
						String.valueOf(entity.getProperty("noteType")));
				JSONObject object = new JSONObject();
				object = noteParser.handleDeadLineNoteJSONObject(deadLineNoteObj);
				JSONObject nType_nValues = new JSONObject();
				nType_nValues.put(deadline, object.toJSONString());
				returnedJson.add(nType_nValues);

				allNotes.add(deadLineNoteObj);
			}
		}
		

		return returnedJson;
	}


}
