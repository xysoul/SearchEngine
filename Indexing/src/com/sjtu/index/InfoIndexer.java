package com.sjtu.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.htmlparser.util.ParserException;
import org.apache.lucene.util.Version;

import com.sjtu.core.Info;
import com.sjtu.htmlparser.HtmlParser;
import com.sjtu.utils.FileText;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 创建索引的类
 * @author Administrate
 *
 */
public class InfoIndexer {
		
	private static final String AUTHOR_NAME = "name";
	
	private static final String JOURNAL_NAME = "journalname";
	
	private static final String CONFERENCE_NAME = "confname";
	
	
	private static final String AUTHORS = "authors";
	
	private static final String TITLES = "titles";
	
	//private static final String FILE_NAME = "filename";
	
	//private static final String CONTENT = "content";
	
	private static final String PATH = "path";
		
	static String docsPath_author = "f:/acube/SearchEngine/htmls/C0";
	
	static String docsPath_journal = "f:/acube/SearchEngine/htmls/B0";
	
	static String docsPath_conference = "E:\\SJTU\\SearchEngine\\conference\\A0\\700";
	
	final static String AUTHOR_json = "E:\\SJTU\\jsonTXT\\author_json.txt";
	final static String CONF_json = "E:\\SJTU\\jsonTXT\\conf_json_Z0.txt";
	final static String JOURNAL_json = "E:\\SJTU\\jsonTXT\\journal_json.txt";
	final static String TITLE_json = "E:\\SJTU\\jsonTXT\\title_json.txt";
	
	public static String lineText; 
	//JSON格式数据解析对象
//	public static JSONObject jsonobject;
//	public static JSONArray jsonarray;
	
	public InfoIndexer() throws Exception{
	
	}
	
	
	/**
	 * 获取Conference中的信息
	 */
	public static ArrayList<HashMap> getConferenceJsonTXT(String path){
		//String text = ReadFile(path);
		//System.out.println(text);
		BufferedReader reader = null;
		String laststr = "";
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			reader = new BufferedReader(isr);
			String temp = null;
			
			while((temp = reader.readLine())!= null){
				JSONObject obj = JSONObject.fromObject(temp);				
				JSONArray array = obj.getJSONArray("Conference");			
				int size = array.size();
				//System.out.println("Size is:" + size);
				for(int i = 0; i < size; i++){
					JSONObject object = array.getJSONObject(i);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("name_C", object.getString("name_C"));
					map.put("path_C", object.getString("path_C"));
					map.put("authors_C", object.getString("authors_C"));
					map.put("titles_C", object.getString("titles_C"));
					list.add(map);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
			
		return list;
	}
	
	/**
	 * 读取文件中的map内容
	 */
	public static String ReadFile(String path){
		BufferedReader reader = null;
		String laststr = "";
		
		try {
			FileInputStream fis = new FileInputStream(path);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
			reader = new BufferedReader(isr);
			String temp = null;
			
			while((temp = reader.readLine())!= null){
				laststr += temp;
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return laststr;
	}
	
	
	/**
	 * 获取JSON文件中的关于Author的信息
	 * @param path
	 * @return
	 */
	public static ArrayList<HashMap> getAuthorJsonTXT(String path){
		
		String text = ReadFile(path);
		System.out.println(text);
		
		JSONObject obj = JSONObject.fromObject(text);
			
		JSONArray array = obj.getJSONArray("author");
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		
		int size = array.size();
		System.out.println("Size is:" + size);
		for(int i = 0; i < size; i++){
			JSONObject jsonobject = array.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name_A", jsonobject.getString("name_A"));
			map.put("path_A", jsonobject.getString("path_A"));
//			map.put("authors_A", jsonobject.getString("authors_A"));
			map.put("titles_A", jsonobject.getString("titles_A"));
			list.add(map);
		}
			
		return list;
	}
	
	/**
	 * 获取JSON文件中关于JOURNAL的信息
	 */
	public static ArrayList<HashMap> getJournalJsonTXT(String path){
		String text = ReadFile(path);
		
		JSONObject obj = JSONObject.fromObject(text);
		JSONArray array = obj.getJSONArray("Journal");
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		
		int size = array.size();
		System.out.println("Size is:" + size);
		for(int i = 0; i < size; i++){
			JSONObject object = array.getJSONObject(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name_J", object.getString("name_J"));
			map.put("path_J", object.getString("path_J"));
			map.put("authors_J", object.getString("authors_J"));
			map.put("titles_J", object.getString("titles_J"));
			list.add(map);
		}
		return list;
	}
	
	
	
	
	

	/**
	 * 给Author创建索引
	 * @param indexPath
	 * @throws IOException
	 * @throws ParserException
	 */
	public void createIndexAuthor(String indexPath) throws IOException, ParserException{
		Directory directory = FSDirectory.open(new File(indexPath));
		
		//创建标准分词器
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
								
		//创建indexWriter配置信息
		IndexWriterConfig iwconfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		
		//设置索引的打开方式
		iwconfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		//Optional
		// iwc.setRAMBufferSizeMB(256.0);
		
		IndexWriter writer = new IndexWriter(directory, iwconfig);	
						
		//从文件中读取json数据
		ArrayList<HashMap> list = getAuthorJsonTXT(AUTHOR_json);
		
		for(int i = 0; i < list.size(); i++){

			Document doc = new Document();
			
			String name = (String) list.get(i).get("name_A");
			//String authors = list.get(i).getAuthors_A();
			String titles = (String) list.get(i).get("titles_A");
			//String filename = list.get(i).getFilename();
			//String content = list.get(i).getContent();
			String path = (String) list.get(i).get("path_A");
			
			
			Field field = new TextField(AUTHOR_NAME, name, Field.Store.YES);
			doc.add(field);
			field = new TextField(TITLES, titles, Field.Store.NO);
			doc.add(field);
			field = new TextField(PATH, path, Field.Store.YES);
			doc.add(field);
			
			writer.addDocument(doc);	
			System.out.println("File: " + name + "\nFilePath:"+ path + " indexed!");
		}	
		//writer.commit();
		System.out.println("Done!");
		writer.close();	
		directory.close();
	}
		
//	
//	//optimize the index
////	public void optimizeIndex() throws IOException{
////		writer.optimize();
////	}
//	
//
	/**
	 * 给Journal创建索引
	 * @param indexPath
	 * @throws IOException
	 * @throws ParserException
	 */
	public void createIndexJournal(String indexPath) throws IOException, ParserException{
		Directory directory = FSDirectory.open(new File(indexPath));
		
		//创建标准分词器
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
								
		//创建indexWriter配置信息
		IndexWriterConfig iwconfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		
		//设置索引的打开方式
		iwconfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		//Optional
		// iwc.setRAMBufferSizeMB(256.0);
		
		IndexWriter writer = new IndexWriter(directory, iwconfig);	
				
		//从文件中读取json数据
		ArrayList<HashMap> list = getJournalJsonTXT(JOURNAL_json);
		
		for(int i = 0; i < list.size(); i++){

			Document doc = new Document();
			
			String name_j = (String) list.get(i).get("name_J");
			String authors = (String) list.get(i).get("authors_J");
			String titles = (String) list.get(i).get("titles_J");
			String path = (String) list.get(i).get("path_J");
			
			
			Field field = new TextField(JOURNAL_NAME, name_j, Field.Store.YES);
			doc.add(field);
			
			field = new TextField(AUTHORS, authors, Field.Store.NO);
			doc.add(field);
			
			field = new TextField(TITLES, titles, Field.Store.NO);
			doc.add(field);
			
			field = new TextField(PATH, path, Field.Store.YES);
			doc.add(field);
			
			writer.addDocument(doc);	
			System.out.println("File: " + name_j + "\nFilePath:"+ path + " indexed!");
		}	
		//writer.commit();
		System.out.println("Done!");
		writer.close();	
		directory.close();
	}
	
	/**
	 * 给conference创建索引
	 * @param indexPath
	 * @throws IOException
	 * @throws ParserException
	 */
	public void createIndexConf(String indexPath) throws IOException, ParserException{
		Directory directory = FSDirectory.open(new File(indexPath));
		
		//创建标准分词器
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
								
		//创建indexWriter配置信息
		IndexWriterConfig iwconfig = new IndexWriterConfig(Version.LUCENE_43, analyzer);
		
		//设置索引的打开方式
		iwconfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		
		//Optional
		// iwc.setRAMBufferSizeMB(256.0);
		
		IndexWriter writer = new IndexWriter(directory, iwconfig);	
				
		//File filesDir = new File(docsPath_conference);
		
		//从文件中读取json数据
		ArrayList<HashMap> list = getConferenceJsonTXT(CONF_json);
		
		for(int i = 0; i < list.size(); i++){

			Document doc = new Document();
			
			String name_C = (String) list.get(i).get("name_C");
			String authors = (String) list.get(i).get("authors_C");
			String titles = (String) list.get(i).get("titles_C");
			String path = (String) list.get(i).get("path_C");
			
			
			Field field = new TextField(CONFERENCE_NAME, name_C, Field.Store.YES);
			doc.add(field);
			
			field = new TextField(AUTHORS, authors, Field.Store.NO);
			doc.add(field);
			
			field = new TextField(TITLES, titles, Field.Store.NO);
			doc.add(field);
			
			field = new TextField(PATH, path, Field.Store.YES);
			doc.add(field);
			
			writer.addDocument(doc);	
			System.out.println("===================="+i+ "=====================");
			System.out.println("File: " + name_C + "\nFilePath:"+ path + " indexed!");
		}	
		//writer.commit();
		System.out.println("Done!");
		writer.close();	
		directory.close();
	}
	
	
	public static void main(String[] args) throws ParserException, ParseException, IOException{
		/* test for indexing
		//Directory Index_Store_Path = FSDirectory.open(new File("f:\\acube\\SearchEngine\\index"));
		String indexPath = "index";
		Directory Index_Store_Path = FSDirectory.open(new File(indexPath));
		//使用indexReader来读取索引
		try {
			IndexReader reader = IndexReader.open(Index_Store_Path);
			//显示索引内所有的Document
			System.out.println("索引文件列表: ");
			for(int i = 0; i < reader.numDocs(); i++){
				System.out.println(reader.document(i));
			}
			
			//输出当前索引的文档数量
			System.out.println("索引内的文档数量: " + reader.numDocs());
					
			reader.close();
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
		/*test for getAuthorJsonTXTData
		
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		//list = getAuthorJsonTXTData(AUTHOR_json);
		//list = getJournalJsonTXT(JOURNAL_json);
		list = getConferenceJsonTXT(CONF_json);
		
		
		System.out.println("Size:" + list.size());
		for(int i = 0; i < 50; i++){
			//System.out.println(i+" Name:"+list.get(i).get("name_A"));
			//System.out.println(i+" Name:"+list.get(i).get("name_J"));
			System.out.println(i+" Name:"+list.get(i).get("name_C"));
		}
		*/
		
		/*test for getConferenceJsonTXT2*/
		ArrayList<HashMap> list = new ArrayList<HashMap>();
		list = getConferenceJsonTXT(CONF_json);
		
		System.out.println("Size:" + list.size());
		for(int i = 0; i < 50; i++){
			//System.out.println(i+" Name:"+list.get(i).get("name_A"));
			//System.out.println(i+" Name:"+list.get(i).get("name_J"));
			System.out.println(i+" Name:"+list.get(i).get("name_C"));
		}
	}	
}
