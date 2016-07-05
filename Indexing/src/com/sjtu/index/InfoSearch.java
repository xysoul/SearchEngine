package com.sjtu.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.FileLockInterruptionException;
import java.util.ArrayList;
import java.util.Date;

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
 * ��������������������
 * @author Administrate
 *
 */
public class InfoSearch {
	final static int NUM_OF_RESULTS = 100;
	
	private String indexPath_author = "f:\\acube\\SearchEngine\\index_author";
	private String indexPath_conference = "E:\\SJTU\\index_conf";
	private String indexPath_journal = "E:\\SJTU\\index_journal";
	
	
//	/**
//	 * ����Lucene������
//	 * @param searchKey �����Ĺؼ���
//	 * @throws ParseException 
//	 * @throws IOException 
//	 */
//	public void indexSearch(String searchType, String searchKey) throws ParseException, IOException{
//		//Directory Index_Store_Path = FSDirectory.open(new File("E:\\EclipseWorkspace\\SearchSystem\\index"));
//		
//		try {
////			//ͳ��һ������ʱ��
//			Date beginTime = new Date();
//			
//			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_author)));
//			//��ʾ���������е�Document
////			System.out.println("�����ļ��б�: ");
////			for(int i = 0; i < reader.numDocs(); i++){
////				System.out.println(reader.document(i));
////			}
////			
////			//�����ǰ�������ĵ�����
////			System.out.println("�����ڵ��ĵ�����: " + reader.numDocs());
////			
////			//����һ���ʣ����������в���
////			System.out.println();
////			System.out.println("================���Ҵ���==============");
////			
//			
//			IndexSearcher searcher = new IndexSearcher(reader);
//			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
//			
//			BufferedReader in = null;
//			
//			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
//					analyer);
//			
//			Query query = parser.parse(searchKey);
//			System.out.println("query is: " + query.toString());
//			
//			
//			TopDocs tds = searcher.search(query, 1000);
//			
//			ScoreDoc[] hits = tds.scoreDocs; 
//			
//			int numHits = tds.totalHits;
//			System.out.println("�����������ĵ������� " + numHits);
//			
//			for(ScoreDoc sd:hits){ 
//				Document doc = searcher.doc(sd.doc); 
//	            //8������Document�����ȡ��Ҫ��ֵ 
//				System.out.println(doc.get("confname")+":"+doc.get("path")); 
//	        } 				
//			reader.close();
//			
//			//���ʱ��
//			Date endTime = new Date();
//			long searchTime = endTime.getTime() - beginTime.getTime();
//			System.out.println("The time For index search is " + searchTime + " ms");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	
//	}
	
	
	
	
	/**
	 * search for author pages
	 * @param searchType
	 * @param searchKey
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public ArrayList<Info> indexSearchAuthor(String searchType, String searchKey) throws ParseException, IOException{
		//Directory Index_Store_Path = FSDirectory.open(new File("E:\\EclipseWorkspace\\SearchSystem\\index"));
		ArrayList<Info> list = new ArrayList<Info>();
		Info info = new Info();
		try {
			//ͳ��һ������ʱ��
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_author)));	
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("query is: " + query.toString());
			
			
			TopDocs tds = searcher.search(query, 500);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			System.out.println("�����������ĵ������� " + numHits);
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Document doc = searcher.doc(hits[i].doc); 
					
					String f = doc.get("path");
					
					//System.out.println(f);
					info = HtmlParser.SingleHTMLParserAuthor(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+ doc.get("name")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Document doc = searcher.doc(hits[i].doc); 
					
					String f = doc.get("path");
					
					//System.out.println(f);
					info = HtmlParser.SingleHTMLParserAuthor(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+doc.get("name")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}
							
			reader.close();
			
			//���ʱ��
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For index search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Info info = new Info();
		try {
			//ͳ��һ������ʱ��
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_journal)));	
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("query is: " + query.toString());
			
			
			TopDocs tds = searcher.search(query, 500);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			System.out.println("�����������ĵ������� " + numHits);
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					info = HtmlParser.SingleHTMLParserJournal(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+doc.get("journalname")+"\t"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					info = HtmlParser.SingleHTMLParserJournal(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+doc.get("journalname")+"\t"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}
							
			reader.close();
			
			//���ʱ��
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For index search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		Info info = new Info();
		try {
			//ͳ��һ������ʱ��
			Date beginTime = new Date();
			
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexPath_conference)));	
			
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyer = new StandardAnalyzer(Version.LUCENE_43);
			
			BufferedReader in = null;
			
			QueryParser parser = new QueryParser(Version.LUCENE_43, searchType, 
					analyer);
			
			Query query = parser.parse(searchKey);
			System.out.println("query is: " + query.toString());
			
			
			TopDocs tds = searcher.search(query, 500);
			
			ScoreDoc[] hits = tds.scoreDocs; 
			
			int numHits = tds.totalHits;
			System.out.println("�����������ĵ������� " + numHits);
			
			if(numHits > NUM_OF_RESULTS){
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < NUM_OF_RESULTS; i++){
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					info = HtmlParser.SingleHTMLParserConference(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+doc.get("confname")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 	
			}else{
				//for(ScoreDoc sd:hits){ 
				for(int i = 0; i < numHits; i++){
					Document doc = searcher.doc(hits[i].doc); 
					String f = doc.get("path");
					info = HtmlParser.SingleHTMLParserConference(f);
		            //����Document�����ȡ��Ҫ��ֵ 
					System.out.println(i+ ":\t"+doc.get("confname")+":"+doc.get("path")); 
					
					list.add(info);
					
		        } 
			}
						
			reader.close();
			
			//���ʱ��
			Date endTime = new Date();
			long searchTime = endTime.getTime() - beginTime.getTime();
			System.out.println("The time For index search is " + searchTime + " ms");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;	
	}

}
