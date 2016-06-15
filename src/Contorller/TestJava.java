package Contorller;
import java.util.ArrayList;
import com.cybozu.labs.langdetect.Detector;
import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;
import com.cybozu.labs.langdetect.Language;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Vector;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TestJava {

	public static Vector<String> readFile() {
		// String path = "war\\WEB-INF\\lib\\projectData";
		String fileName = "category.txt";

		Vector<String> cat = new Vector<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

			String line;
			while ((line = br.readLine()) != null) {
				cat.add(line.toLowerCase().trim());

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return cat;
	}
	public void init(String profileDirectory) throws LangDetectException {
        DetectorFactory.loadProfile(profileDirectory);
    }
    public static String  detect(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.detect();
    }
    public ArrayList detectLangs(String text) throws LangDetectException {
        Detector detector = DetectorFactory.create();
        detector.append(text);
        return detector.getProbabilities();
    }
	public static void main(String[] args) throws ParseException, LangDetectException {

		//System.out.println(detect("today is good"));
		
		 
		java.util.Date date = new java.util.Date();
		Timestamp t = new Timestamp(date.getTime());
		String s1 = "2016-06-15";
		String s2 = "18:23:55";
		//Timestamp ddd = java.sql.Timestamp.valueOf(s1)
		Timestamp ttt = java.sql.Timestamp.valueOf("2016-06-15 18:23:55.664");
		// new Timestamp(date.getTime());//
		String x = String.valueOf(new Timestamp(date.getTime()));
		System.out.println("Date  = " + date + "    xx = " + x + "    ppp   = " + date.getTime());
		System.out.println("time stamp = " + t);
		int dd = t.getDate();
		System.out.println("ttt = " + ttt.toString());
		System.out.println(ttt.compareTo(t));
		String timeStamp = new SimpleDateFormat("yyyy/MM/dd").format(new java.util.Date());
		System.out.println(timeStamp);

		// SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		// String dateInString = "7-Jun-2013";
		//
		// try {
		//
		// Date date =(Date) formatter.parse(dateInString);
		// System.out.println(date);
		// System.out.println(formatter.format(date));
		//
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		//
		// String dateStr = "06/27/2007";
		// DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		// Date startDate = (Date)formatter.parse(timeStamp);
		// System.out.println("AAA = "+startDate.toString());
		// Vector<String> cat = new Vector<String>();
		// cat = readFile();
		// for (int i = 0; i < cat.size(); i++) {
		// System.out.println(cat.get(i));
		// }

	}
}
