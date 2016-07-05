package com.sjtu.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class FileText {
	/**
	 * @param File f
	 * @return the text of a file
	 */
	public static String getText(File f){
		StringBuffer sb = new StringBuffer();
		try{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			while(s != null){
				sb.append(s);
				s = br.readLine();
			}
			br.close();
		}catch(Exception e){
			sb.append("");
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @param s
	 * @return text of a file
	 */
	public static String getText(String s){
		String t = "";
		try{
			File f = new File(s);
			t = getText(f);
		}catch(Exception e){
			t = "";
		}
		return t;
	}
	
	//Test on a html
	/*
	public static void main(String[] args){
		String s = FileText.getText("htmls/te.html");
		System.out.println(s);
		
	}
	*/
}
