package com.alchemyapi.test;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class NLP_TextCategorization {
	private static TaxonomyTest taxonomy;
	public static String getTextCategory(String text)
	{
		try {
			String res =taxonomy.getTextCategory(text);
			
			String[] lable = res.split("/");
			
			if(res.equals(""))
				return "";
			return lable[1];
		} catch (XPathExpressionException | IOException | SAXException
				| ParserConfigurationException e) {
			// TODO Auto-generated catch block
			return "";
		} catch(Exception ee)
		{
			return "";
		}
		
	}

}
