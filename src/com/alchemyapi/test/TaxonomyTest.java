package com.alchemyapi.test;

import com.alchemyapi.api.*;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.appengine.labs.repackaged.org.json.XML;

import org.xml.sax.SAXException;
import org.json.simple.JSONArray;
import org.w3c.dom.Document;

import java.io.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class TaxonomyTest {

	public static String convertXMLtoJson(String xmlResponse) throws JSONException {
		String jsonResponse = "";
		try {
			int PRETTY_PRINT_INDENT_FACTOR = 4;
			JSONObject xmlJSONObj = XML.toJSONObject(xmlResponse);
			jsonResponse = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
		} catch (JSONException je) {
			System.out.println(je.toString());
		}
		return jsonResponse;
	}

	public static String getTextTaxonomy(JSONObject obj) throws JSONException {
		try {
			// System.out.println("SSSSS");
			// System.out.println(obj.toString());

			String status = obj.getJSONObject("results").getString("status");
			if (status.equals("OK")) {
				String taxonomyResult = obj.getJSONObject("results").getString("taxonomy");
				if (!taxonomyResult.equals("")) {
					String pageName = obj.getJSONObject("results").getString("taxonomy");
					JSONObject res = (JSONObject) obj.getJSONObject("results").getJSONObject("taxonomy")
							.getJSONArray("element").getJSONObject(0);

					return res.getString("label");
				} else {

					// System.out.println("NO_Taxonomy");
				}

			} else {

				return "NOT_OK";
			}
		} catch (JSONException je) {
			System.out.println(je.toString());

		}
		return "";
	}

	public static String getTextCategory(String text)
			throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
		AlchemyAPI alchemyObj = AlchemyAPI.GetInstanceFromFile("api_key.txt");
		AlchemyAPI_TaxonomyParams paramters = new AlchemyAPI_TaxonomyParams();

		
		try {
			Document doc = alchemyObj.TextGetTaxonomy(text);
			String TEST_XML_STRING = getStringFromDocument(doc);
			JSONObject obj = new JSONObject(convertXMLtoJson(TEST_XML_STRING));

			return getTextTaxonomy(obj);

		} catch (JSONException je) {
			System.out.println(je);
		}
		
		return "";
	}

	private static String getStringFromDocument(Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);

			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);

			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
