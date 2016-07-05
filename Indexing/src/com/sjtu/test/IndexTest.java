package com.sjtu.test;

import com.sjtu.index.InfoIndexer;

public class IndexTest {
	
	public static void main(String[] args){
		//String INDEX_STORE_PATH_AUTHOR = "E:\\SJTU\\index_author";
		String INDEX_STORE_PATH_CONFERENCE = "E:\\SJTU\\index_conf";
		//String INDEX_STORE_PATH_TITLE = "E:\\SJTU\\index_title";
		//String INDEX_STORE_PATH_JOURNAL = "E:\\SJTU\\index_journal";
				
		//String inputDir = "f:\\acube\\SearchSystem\\htmls\\C0\\C., Carlos A. Castro.html";
		
		try {
			InfoIndexer indexer = new InfoIndexer();
			//indexer.createIndexAuthor(INDEX_STORE_PATH_AUTHOR);
			indexer.createIndexConf(INDEX_STORE_PATH_CONFERENCE);
			//indexer.createIndexJournal(INDEX_STORE_PATH_JOURNAL);
			
			System.out.println("============================================");
			System.out.println("Index Write Done!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
