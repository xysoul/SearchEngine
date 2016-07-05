package com.sjtu.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.sjtu.core.Info;
import com.sjtu.htmlparser.HtmlParser;

/**
 * 创建检索索引的搜索类
 * @author Administrate
 *
 */
public class InfoSearch {
	final static int NUM_OF_RESULTS = 50;
	final static int NUM_OF_MAX = 500;
	
	private String indexPath_author = "E:\\SJTU\\index_author";
	private String indexPath_conference = "E:\\SJTU\\index_conf";
	private String indexPath_journal = "E:\\SJTU\\index_journal";
	

	
	
	/**
	 * search for author pages
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchAuthorName(String searchType, String searchKey) throws ParseException, IOException{
		//Directory Index_Store_Path = FSDirectory.open(new File("E:\\EclipseWorkspace\\SearchSystem\\index"));
		ArrayList<Info> list = new ArrayList<Info>();
//		Info info = new Info();
		try {
			//统计一下索引时间
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_author)));	
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("author query is: " + query.toString());
			
			TopDocs tds = searcher.search(query, NUM_OF_MAX);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			//System.out.println("author符合条件的文档总数： " + numHits);
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					
					String f = doc.get("path");
					
					//System.out.println(f);
					//info = HtmlParser.SingleHTMLParserAuthor(f);
					info.setName_A(doc.get("name"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+ doc.get("name")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					
					String f = doc.get("path");
					
					//System.out.println(f);
					//info = HtmlParser.SingleHTMLParserAuthor(f);
					info.setName_A(doc.get("name"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+doc.get("name")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}
							
			reader.close();
			
			//完成时间
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For title search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			filterListAuthor(list);
		}
		System.out.println("author符合条件的文档总数： " + list.size());
		for(int i = 0; i < list.size(); i++){
			System.out.println(i+":"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_A());
		}
		
		return list;	
	}
	
	
	/**
	 * search for journal pages
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchJournal(String searchType, String searchKey) throws ParseException, IOException{
		//Directory Index_Store_Path = FSDirectory.open(new File("E:\\EclipseWorkspace\\SearchSystem\\index"));
		ArrayList<Info> list = new ArrayList<Info>();
//		Info info = new Info();
		try {
			//统计一下索引时间
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_journal)));	
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("journal query is: " + query.toString());
			
			
			TopDocs tds = searcher.search(query, NUM_OF_MAX);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					//info = HtmlParser.SingleHTMLParserJournal(f);
					info.setName_J(doc.get("journalname"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+doc.get("journalname")+"\t"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					//info = HtmlParser.SingleHTMLParserJournal(f);
					info.setName_J(doc.get("journalname"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+doc.get("journalname")+"\t"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}
							
			reader.close();
			
			//完成时间
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For journal search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			filterListJournal(list);
		}
		System.out.println("journal符合条件的文档总数： " + list.size());
		for(int i = 0; i < list.size(); i++){
			System.out.println(i+":"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_J());
		}
		
		return list;	
	}
	
	
	/**
	 * search for conference pages
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchConference(String searchType, String searchKey) throws ParseException, IOException{
		//Directory Index_Store_Path = FSDirectory.open(new File("E:\\EclipseWorkspace\\SearchSystem\\index"));
		ArrayList<Info> list = new ArrayList<Info>();
		try {
			//统计一下索引时间
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_conference)));	
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("Conference query is: " + query.toString());
					
			TopDocs tds = searcher.search(query, NUM_OF_MAX);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			//System.out.println("Conference符合条件的文档总数： " + numHits);
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					//info = HtmlParser.SingleHTMLParserConference(f);
					info.setName_C(doc.get("confname"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+hits[i].score +doc.get("confname")+":"+doc.get("path")); 

					list.add(info);
					info = null;
		        } 	
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Info info = new Info();
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					//info = HtmlParser.SingleHTMLParserConference(f);
					info.setName_C(doc.get("confname"));
					info.setPath(doc.get("path"));
					info.setScore(hits[i].score);
		            //根据Document对象获取需要的值 
					//System.out.println(i+ ":\t"+doc.get("confname")+":"+doc.get("path")); 
					list.add(info);
					info = null;
		        } 
			}					
			reader.close();
			
			//完成时间
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For conference search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(list.size() > 0){
			filterListConference(list);
		}
		
		System.out.println("journal符合条件的文档总数： " + list.size());
		for(int i = 0; i < list.size(); i++){
			System.out.println(i+":"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_C());
		}

		
		return list;	
	}

	
	/**
	 * search for titles
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchTitle(String searchType, String searchKey) throws ParseException, IOException{
		//统计一下索引时间
		Date beginTime = new Date();
		ArrayList<Info> list = new ArrayList<Info>();
		ArrayList<Info> list1 = new ArrayList<Info>();
		ArrayList<Info> list2 = new ArrayList<Info>();
		
		list1 = indexSearchConference(searchType, searchKey);
		list2 = indexSearchJournal(searchType, searchKey);
		list = indexSearchAuthorName(searchType, searchKey);
		
		
		for(int i = 0; i < list1.size();i++){
			Info info = new Info();
			info.setName_A(list1.get(i).getName_C());
			info.setScore(list1.get(i).getScore());
			info.setPath(list1.get(i).getPath());
			list.add(info);
			info = null;
		}
		
		for(int i = 0; i < list2.size();i++){
			Info info = new Info();
			info.setName_A(list2.get(i).getName_J());
			info.setScore(list2.get(i).getScore());
			info.setPath(list2.get(i).getPath());
			list.add(info);
			info = null;
		}	
		
		sort(list);
		
		int num = list.size();
		System.out.println("\n\n"+"title符合条件的文档总数： " + num);
		
		if(num >= NUM_OF_RESULTS){
			for(int i = 0; i < NUM_OF_RESULTS; i++){
				System.out.println("Sorted:"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_A());
			}
		}else{
			for(int i = 0; i < num; i++){
				System.out.println("Sorted:"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_A());
			}
		}
		//完成时间
		Date endTime = new Date();
		long searchTime = endTime.getTime() - beginTime.getTime();
		System.out.println("The time For title search is " + searchTime + " ms");
		
		
		
		return list;
	}
	
	
	/**
	 * search for author
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchAuthor(String searchKey) throws ParseException, IOException{
		//统计一下索引时间
				Date beginTime = new Date();
				ArrayList<Info> list = new ArrayList<Info>();
				ArrayList<Info> list1 = new ArrayList<Info>();
				ArrayList<Info> list2 = new ArrayList<Info>();
				
				list1 = indexSearchConference("authors", searchKey);
				list2 = indexSearchJournal("authors", searchKey);
				list = indexSearchAuthorName("name", searchKey);
				
				
				for(int i = 0; i < list1.size();i++){
					Info info = new Info();
					info.setName_A(list1.get(i).getName_C());
					info.setScore(list1.get(i).getScore());
					info.setPath(list1.get(i).getPath());
					list.add(info);
					info = null;
				}
				
				for(int i = 0; i < list2.size();i++){
					Info info = new Info();
					info.setName_A(list2.get(i).getName_J());
					info.setScore(list2.get(i).getScore());
					info.setPath(list2.get(i).getPath());
					list.add(info);
					info = null;
				}	
				
				sort(list);
				
				int num = list.size();
				System.out.println("\n\n"+"title符合条件的文档总数： " + num);
				
				if(num >= NUM_OF_RESULTS){
					for(int i = 0; i < NUM_OF_RESULTS; i++){
						System.out.println("Sorted:"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_A());
					}
				}else{
					for(int i = 0; i < num; i++){
						System.out.println("Sorted:"+ list.get(i).getScore() + "\tName:" + list.get(i).getName_A());
					}
				}
				//完成时间
				Date endTime = new Date();
				long searchTime = endTime.getTime() - beginTime.getTime();
				System.out.println("The time For author search is " + searchTime + " ms");
				
				
				
				return list;
	}
	
	
	/**
	 * sort by score
	 */
	
	public void sort(ArrayList<Info> list){
		Collections.sort(list, new Comparator<Info>() {
			int ret = 1;
			@Override
			public int compare(Info o1, Info o2) {
				// TODO Auto-generated method stub
				if(o1.getScore() > o2.getScore())
					ret = -1;
				if(o1.getScore() == o2.getScore())
					ret = 0;
				return ret;
			}
		});
	}
	
	/**
	 * Conference去重
	 */
	public void filterListConference(ArrayList<Info> list){
		
		for(int i = list.size()-1; i > 0; i--){
			for(int j = i-1; j >= 0; j--){
				if(list.get(j).getName_C().equals(list.get(i).getName_C())){
					//list.remove(list.get(j));
					list.remove(j);
					break;
				}
			}
		}		
	}
	
	/**
	 * Journal去重
	 */
	public void filterListJournal(ArrayList<Info> list){
		
		for(int i = list.size()-1; i > 0; i--){
			for(int j = i-1; j >= 0; j--){
				if(list.get(j).getName_J().equals(list.get(i).getName_J())){
					//list.remove(list.get(j));
					list.remove(j);
					break;
				}
			}
		}		
	}
	
	/**
	 * Author去重
	 */
	public void filterListAuthor(ArrayList<Info> list){
		
		for(int i = list.size()-1; i > 0; i--){
			for(int j = i-1; j >= 0; j--){
				if(list.get(j).getName_A().equals(list.get(i).getName_A())){
					//list.remove(list.get(j));
					list.remove(j);
					break;
				}
			}
		}		
	}
	
	
}
