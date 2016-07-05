package com.sjtu.test;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import com.sjtu.core.Info;
import com.sjtu.index.InfoIndexer;
import com.sjtu.index.InfoSearch;

public class SearchTest {

	public static void main(String[] args) throws ParseException, IOException {
		// TODO Auto-generated method stub
		InfoSearch search = new InfoSearch();
		//传入索引搜索关键字
		//search.indexSearch("content", "adaniel");
		
		//test for the void indexSearch
		//search.indexSearch("name", "Amir");
		
		ArrayList<Info> info = new ArrayList<Info>();
		
		//info = search.indexSearchAuthor("name", "Amir");
		info = search.indexSearchConference("confname", "mobile");
		//info = search.indexSearchJournal("journalname", "Psychology and Learning");
		
		for(int i = 0; i < info.size(); i++){
			//System.out.println("info " + i + ": " + info.get(i).getName_A());
			System.out.println("info " + i + ": " + info.get(i).getName_C());
			//System.out.println("info " + i + ": " + info.get(i).getName_J());
		}
		
		
		System.out.println("\n\n=============Search Done!================");	
	}

}
