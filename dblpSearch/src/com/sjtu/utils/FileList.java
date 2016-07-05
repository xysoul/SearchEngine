package com.sjtu.utils;

import java.io.File;
import java.io.IOException;

public class FileList {
	/**
	 * return all the files under a directory
	 */
	private static final String SEP = "/";
	private static StringBuffer sb = new StringBuffer("");
	
	//input a file
	public static String[] getFiles(File f) throws IOException{
		if(f.isDirectory()){
			File[] fs = f.listFiles();
			for(int i = 0; i < fs.length; i++){
				getFiles(fs[i]);
			}
		}else{
			sb.append(f.getPath()+SEP);
		}
		String s = sb.toString();
		String[] list = s.split(SEP);
		return list;
	}
	
	//input a String
	public static String[] getFiles(String s) throws IOException{
		File f = new File(s);
		if(f.isDirectory()){
			File[] fs = f.listFiles();
			for(int i = 0; i < fs.length; i++){
				getFiles(fs[i]);
			}
		}else{
			sb.append(f.getPath()+SEP);
		}
		String ss = sb.toString();
		String[] list = ss.split(SEP);
		return list;
	}
	
	
	
	//test for a multiple layer directory	
	public static void main(String[] args){
		String f = "F:\\acube\\SearchEngine\\htmls\\A0";
		try {
			String[] list = getFiles(f);
			for(int i = 0; i < list.length; i++){
				System.out.println("list " + i + ": " + list[i]);
			}
			System.out.println("the length is " + list.length);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
