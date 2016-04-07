package com.parser.xml;

import static org.joox.JOOX.$;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.SAXException;

/**
 * parses the xml using jOOX 
 * 
 * @author ram
 *
 */
public class ChangeLogParser {

	private static String path_prefix = "src/main/resources/changelog/";
	private static String root_xml = "root.xml";

	/**
	 * main method will be called from the pom.xml
	 * exec-maven-plugin 
	 * @param args
	 * @throws SAXException
	 * @throws IOException
	 */
	public static void main(String[] args) throws SAXException, IOException {


		printSQLFileName();

	}



	/**
	 * 
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void printSQLFileName() throws SAXException, IOException {

		$(new File(path_prefix+root_xml)).find("include")
		.each(ctx -> {
			System.out.println($(ctx).attr("file"));
			String fileName = getFileName($(ctx).attr("file"));
			System.out.println("filename= "+fileName);

			try {
				parseChildXMLs(fileName);
			} catch (Exception e) {
				//TODO: throw custom exception.
			}
		});
	}



	/**
	 * parse the included xmls in the root.xml
	 * @param fileName
	 * @throws SAXException
	 * @throws IOException
	 */
	private static void parseChildXMLs(String fileName) throws SAXException, IOException {
		List<ChangeLogParser.SQLFileObject> objList = new ArrayList<ChangeLogParser.SQLFileObject>();
		
		$(new File(path_prefix+fileName)).find("changeSet")
		.each(ctx1 -> {
			//System.out.println("author= "+$(ctx1).attr("author")+" id= "+$(ctx1).attr("id")+" sqlfile= "+getFileName($(ctx1).child("sqlFile").attr("path")));
			
			ChangeLogParser.SQLFileObject fileObject = new ChangeLogParser.SQLFileObject();
			fileObject.setAuthor($(ctx1).attr("author"));
			fileObject.setId($(ctx1).attr("id"));
			fileObject.setFileName(getFileName($(ctx1).child("sqlFile").attr("path")));
			
			objList.add(fileObject);
			

		});
		
		
		writeToFile(objList);
	}

	/**
	 * get file name from the path. 
	 * @param sqlFilePath
	 * @return
	 */
	private static String getFileName(String sqlFilePath){

		String[] arr = sqlFilePath.split("\\/");
		return arr[arr.length-1];

	}
	
	/**
	 * writes to the file in the current workspace as csv
	 * @param objList
	 * @throws IOException
	 */
	private static void writeToFile(List<ChangeLogParser.SQLFileObject> objList) throws IOException{
		
		BufferedWriter br = new BufferedWriter(new FileWriter(path_prefix+"myfile.csv"));
		StringBuilder sb = new StringBuilder();
		sb.append("id,author,filename");
		sb.append("\n");
		
		for (ChangeLogParser.SQLFileObject element : objList) {
		 sb.append(element);
		 sb.append(",");
		 sb.append("\n");
		}

		br.write(sb.toString());
		br.close();
		
	}
	
	
	private static class SQLFileObject {
		
		private String id;
		private String author;
		private String fileName;
		
		
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		
		@Override
		public String toString() {
			return  id + "," + author + "," + fileName;
		}
		
		
		
		
	}

}
