package com.alchemyapi.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class NLP_TextCategorization {
	private static TaxonomyTest taxonomy;

	public static String getTextCategory(String text) {
		String category = "";
		try {
			String res = taxonomy.getTextCategory(text);

			if (res.equals("")) {
				category = "NoCategory";
			} else {
				String[] lable = res.split("/");
				category = lable[1];
			}

			
		} catch (XPathExpressionException | IOException | SAXException | ParserConfigurationException e) {
			// TODO Auto-generated catch block
			// return "art and entertainment";
			return "";
		} catch (Exception ee) {
			
			// return "art and entertainment";
			return "";
		}
		return category;
	}

}
