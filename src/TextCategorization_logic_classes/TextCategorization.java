package TextCategorization_logic_classes;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.alchemyapi.test.NLP_TextCategorization;
import com.google.appengine.labs.repackaged.org.json.JSONException;

import HTTPConnection.Connection;
import dataEntities.UClassifyCategory;

public class TextCategorization {


	public  String classifyByUclassify(String sentence) throws org.json.simple.parser.ParseException, JSONException {

	String serviceUrl = "https://uclassify.com/browse/uclassify/topics/ClassifyText";
	String param = "readkey=C14x5zvtGQoL&output=json&version=1.01&text=" + sentence.trim();
	String retJson = Connection.connect(serviceUrl, param, "POST",
			"application/x-www-form-urlencoded;charset=UTF-8");
	
	
	JSONParser parser = new JSONParser();
	

	Object obj = parser.parse(retJson);
	JSONObject object = (JSONObject) obj;
	boolean result = (boolean) object.get("success");
	
	String categories = object.get("cls1").toString();
	categories = categories.substring(1);
	categories = categories.substring(0, categories.length() - 1);
	String[] tokens = categories.split("\\,");
	double max = -10000;
	UClassifyCategory highestPreference = new UClassifyCategory();
	
	for (int i = 0; i < tokens.length; i++) {

		String[] tokens2 = tokens[i].split("\\:");
		String s = tokens2[0].trim();
		double d = Double.parseDouble(tokens2[1].trim());
		s = s.substring(1);
		s = s.substring(0, s.length() - 1);
		if (d > max) {
			max = d;
			highestPreference.setCategory(s);
			highestPreference.setCount(max);
		}
	}
	return highestPreference.getCategory();
}

public static String classifyByAlchemy(String sentence) throws org.json.simple.parser.ParseException {

	NLP_TextCategorization textCategory = new NLP_TextCategorization();
	String result = textCategory.getTextCategory(sentence);

	return result;
}

public String callTextCategoryAPI(String textinput) throws JSONException, ParseException
{
	

	String sentenceClass = classifyByAlchemy(textinput);
	if(sentenceClass.equals(""))
	{
		sentenceClass = classifyByUclassify(textinput);
		sentenceClass = classifyByAlchemy(sentenceClass);
	}
	if(sentenceClass == null)
	{
		sentenceClass = "";
	}
	
	
	return sentenceClass;
	
}


}
