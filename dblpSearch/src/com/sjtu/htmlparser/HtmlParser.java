package com.sjtu.htmlparser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sjtu.core.Info;
import com.sjtu.utils.FileList;
import com.sjtu.utils.FileText;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.htmlparser.*;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.TitleTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class HtmlParser {
	
	public static String[] files;
	
	//�õ��ĵ�����ҳ���ַ�������
	public static String html;
	
	//����file
	//public static File file;
	//����Info����
	//public static Info info = new Info();
	
	//JSON��ʽ���ݽ�������
	public static JSONObject jsonobject = new JSONObject();
	public static JSONArray jsonarray;
	
	
	//��������ȫ����Ϣ�б�
	public static ArrayList<Info> infolist = new ArrayList<Info>();
	public static ArrayList<HashMap> maplist = new ArrayList<HashMap>();
	
	
	final static String AUTHOR_json = "E:\\SJTU\\jsonTXT\\author_json.txt";
	final static String CONF_json = "E:\\SJTU\\jsonTXT\\conf_json_Z0.txt";
	final static String JOURNAL_json = "E:\\SJTU\\jsonTXT\\journal_json.txt";
	final static String TITLE_json = "E:\\SJTU\\jsonTXT\\title_json.txt";
	
	
	//��������ƥ��õ�ҳ�����
	private static String getEncoding(String file){
		String encoding = "UTF-8";
		Pattern p = Pattern.compile("(charset|Charset|CHARSET)\\s*=\\s*\"?\\s*([-\\w]*?)[^-\\w]");
		Matcher m = p.matcher(file);
		if(m.find()){
			encoding = m.group(2);
		}
		return encoding;
	}
	
	/**
	 * ����һ��·���µ������ļ�����������ҳ
	 * @param path
	 * @throws ParserException
	 */
	public static void HTMLParserAllAuthor(String path) throws ParserException{
		
		infolist.clear();
		maplist.clear();
		try {
				files = FileList.getFiles(path);
				int nums = files.length;
				System.out.println("Total files:" + nums);
				for(int i = 0; i < nums; i++){
					Info info = new Info();
					//System.out.println("======================"+FileText.getText(files[i])+"======================");
					Info authorInfo = new Info();
					authorInfo = SingleHTMLParserAuthor(files[i]);
					//����Map����
					HashMap<String,String> map = new HashMap<String,String>();
					map.put("name_A", authorInfo.getName_A());
					map.put("path_A", authorInfo.getPath());
					//map.put("authors_A", info.getAuthors_A());
					map.put("titles_A", info.getTitles_A());
					maplist.add(map);
					jsonarray = JSONArray.fromObject(maplist);
					jsonobject.put("author", jsonarray);
					System.out.println(map);
					if(maplist.size() == 500){
						//WriteToFile(AUTHOR_json, maplist.toString());		
						WriteToFile(AUTHOR_json, jsonobject.toString());		
						maplist.clear();
					}
					info = null;
				}

				if(!maplist.isEmpty()){
					//WriteToFile(AUTHOR_json, maplist.toString());
					WriteToFile(AUTHOR_json, jsonobject.toString());	
					maplist.clear();
				}
				
				System.out.println("Parse all Author Done!");
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
	}
	
	/**
	 * ����һ��·���µ������ļ�����journal��ҳ
	 * @param path
	 * @throws ParserException
	 */
	public static void HTMLParserAllJournal(String path) throws ParserException{
		
		infolist.clear();
		maplist.clear();
		try {
				files = FileList.getFiles(path);
				int nums = files.length;
				System.out.println("Total files:" + nums);
				for(int i = 0; i < nums; i++){
					Info info = new Info();
					//System.out.println("======================"+FileText.getText(files[i])+"======================");
					info = SingleHTMLParserJournal(files[i]);
					//����Map����
					HashMap<String,String> map = new HashMap<String, String>();
					map.put("name_J", info.getName_J());
					map.put("path_J", info.getPath());
					map.put("authors_J", info.getAuthors_J());
					map.put("titles_J", info.getTitles_J());
					maplist.add(map);
					jsonarray = JSONArray.fromObject(maplist);
					jsonobject.put("Journal", jsonarray);
					System.out.println(map);
					if(maplist.size() == 500){
						//WriteToFile(JOURNAL_json, maplist.toString());
						WriteToFile(JOURNAL_json, jsonobject.toString());
						maplist.clear();
					}
					info = null;
				}
				if(!maplist.isEmpty()){
					//WriteToFile(JOURNAL_json, maplist.toString());
					WriteToFile(JOURNAL_json, jsonobject.toString());
					maplist.clear();
				}
				System.out.println("Parse all Journal Done!");
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		//return infolist;
	}
	
	
	/**
	 * ����һ��·���µ������ļ�����Conference
	 * @param path
	 * @throws ParserException
	 */
	public static void HTMLParserConferenceAll(String path) throws ParserException{
		
		infolist.clear();
		maplist.clear();
		try {
				files = FileList.getFiles(path);
				int nums = files.length;
				for(int i = 0; i < nums; i++){
					Info info = new Info();
					//System.out.println("======================"+FileText.getText(files[i])+"======================");
					info = SingleHTMLParserConference(files[i]);
					//����Map����
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("name_C", info.getName_C());
					map.put("path_C", info.getPath());
					map.put("authors_C", info.getAuthors_C());
					map.put("titles_C", info.getTitles_C());
					maplist.add(map);
					jsonarray = JSONArray.fromObject(maplist);
					jsonobject.put("Conference", jsonarray);
					System.out.println(map);
					if(maplist.size() == 500){
						//WriteToFile(CONF_json, maplist.toString());
						WriteToFile(CONF_json, jsonobject.toString());
						maplist.clear();
					}
					info = null;
				}
				if(!maplist.isEmpty()){
					//WriteToFile(CONF_json, maplist.toString());
					WriteToFile(CONF_json, jsonobject.toString());
					maplist.clear();
				}
				System.out.println("Parse all Conference Done!");
				//System.out.println(infolist);
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		//return infolist;
	}
	
	/**
	 * ����������ҳ����Conference
	 * @return 
	 * @throws IOException 
	 */
	public static Info SingleHTMLParserConference(String f) throws IOException{
		
		String name_C = ""; //1 ������
				
		String titles_C = "";//2���ı���
		
		String authors_C = "";//3����//һ��ҳ���е�author������
		
		//����file
		File file = new File(f);
		//����Info����
		//info = new Info();
		Info info = new Info();
				
		info.setPath(file.getPath());
		
		
		html = FileText.getText(file);
		
		try {
				Parser parser = Parser.createParser(html, HtmlParser.getEncoding(html));
				//��ȡconference�����filter
				NodeFilter filter_name_C = new TagNameFilter("h1");//title�ڵ����
				NodeFilter filter_title = new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("class","title"));//���Ľڵ����
				NodeFilter filter_author = new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop","author"));//���Ľڵ����
				//NodeFilter filter_publisher = new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop","publisher"));//���Ľڵ����
				//NodeFilter filter_datePublished = new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop","datePublished"));//���Ľڵ����
				//NodeFilter filter_isbn = new AndFilter(new TagNameFilter("span"),new HasAttributeFilter("itemprop","isbn"));//���Ľڵ����
				
				//����headline
				NodeList nodelist1 = parser.extractAllNodesThatMatch(filter_name_C);//���˵÷��Ϲ���Ҫ��Ľڵ��LIST
				Node node_name_C = nodelist1.elementAt(0);//ȡ�ڵ�
				StringBuffer bufheadline = new StringBuffer();
				if(node_name_C == null){//�ж��Ƿ�Ϊ��
					bufheadline.append("");
				}
				else{
					bufheadline.append(node_name_C.toPlainTextString());//�ѽڵ�����ı��ڵ�ת��ΪString �ӵ�buftitle��
				}
				name_C = bufheadline.toString();//ת��ΪString
//				
				info.setName_C(name_C);
				//WriteToFile(name);
						
				//����Parser
				parser.reset();
						
				//����title
				NodeList nodelist2 = parser.extractAllNodesThatMatch(filter_title);//���˳�����filter_title�Ľڵ�LIST
				for(int i=0; i<nodelist2.size(); i++){//ѭ���ӵ�buftitle��
					Node node_titles = nodelist2.elementAt(i);
					titles_C += node_titles.toPlainTextString().trim() + "_";
				}
				
				info.setTitles_C(titles_C);
				
				
				parser.reset();
				
				//����authors
				//NodeList nodelist3 = parser.parse(filter_author);
				NodeList nodelist3 = parser.extractAllNodesThatMatch(filter_author);
				//System.out.println(nodelist3.size()+" kji  "+filter_author.toString());
				for(int i = 0; i < nodelist3.size(); i++){
					Node node_author = nodelist3.elementAt(i);
					//authors_C = node_author.toPlainTextString().trim();	
					//System.out.println("author " +authors_C[i]);	
					authors_C += node_author.toPlainTextString().trim() + "_";
				}
				info.setAuthors_C(authors_C);	
				//System.out.println("sfsdfsd  "+list.get(i).getAuthors_C()[1]);
				parser.reset();//����
				
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return info;				
	}
	
	/**
	 * ����������ҳ_������ҳ
	 * @return 
	 * @throws IOException 
	 */
	public static Info SingleHTMLParserAuthor(String f) throws IOException{
		//��������
		String name_A="";
				
		//author name
		String authors_A = "";
				
		//paper����
		String titles_A = "";
			
		//����InfoAuthor����
		Info info = new Info();
		
		
		//����file
		File file = new File(f);
	
		info.setPath(file.getPath());
		
		html = FileText.getText(file);
		
		try {
				Parser parser = Parser.createParser(html, HtmlParser.getEncoding(html));
				//��ȡ��ҳ�������ֵ�filter
				NodeFilter filter_name = new AndFilter(new TagNameFilter("span"),
						new HasAttributeFilter("class", "name primary"));
				
				//��ȡpaper�������ֵ�filter
				NodeFilter filter_author = new AndFilter(new TagNameFilter("span"),
						new HasAttributeFilter("itemprop", "author"));
				//��ȡpaper title��filter
				NodeFilter filter_title = new AndFilter(new TagNameFilter("span"),
						new HasAttributeFilter("class", "title"));

				//������ҳ��������
				NodeList nodelist = parser.parse(filter_name);
					
				Node node_name = nodelist.elementAt(0);
				name_A = node_name.toPlainTextString().trim();
				//System.out.println("name is " + name);
				//System.out.println("++++++++++++++++++++++++++++++++++++++");
				info.setName_A(name_A);
						
				//����Parser
				parser.reset();
						
				//����author
				nodelist = parser.extractAllNodesThatMatch(filter_author);
				//Node[] node_author = nodelist.toNodeArray();
				for(int i = 0; i < nodelist.size(); i++){
					Node node_author = nodelist.elementAt(i);
					//System.out.println("author " + i + " :" + node_author.toPlainTextString().trim());
					//System.out.println("=========================================");
					authors_A += node_author.toPlainTextString().trim()+"_";
					
				}
				//System.out.println("================="+authors+"========================");
				info.setAuthors_A(authors_A);

				
				parser.reset();
				
				//����paper title
				nodelist = parser.extractAllNodesThatMatch(filter_title);
				//Node[] node_title = nodelist.toNodeArray();
				for(int i = 0; i < nodelist.size(); i++){
					Node node_title = nodelist.elementAt(i);
					//System.out.println("title " + i + " :" + node_title.toPlainTextString().trim());
					//System.out.println("****************************************");
					titles_A += node_title.toPlainTextString().trim()+"_";
				}
				//System.out.println("*****************"+titles+"***********************");
				info.setTitles_A(titles_A);
				
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return info;				
	}
	
	/**
	 * ����������ҳ_Journal
	 * @return Info
	 * @throws IOException 
	 */
	public static Info SingleHTMLParserJournal(String f) throws IOException{
		//��������
		String name_J="";
				
		//author name
		String authors_J = "";
				
		//paper����
		String titles_J = "";
			
		//����InfoConjour����
		Info info = new Info();
		
		//����file
		File file = new File(f);

		info.setPath(file.getPath());
		
		html = FileText.getText(file);
		
		try {
				Parser parser = Parser.createParser(html, HtmlParser.getEncoding(html));
				//��ȡjournal���ֵ�filter
				NodeClassFilter titleFilter = new NodeClassFilter(TitleTag.class);				
				//��ȡpaper�������ֵ�filter
				NodeFilter filter_author = new AndFilter(new TagNameFilter("span"),
						new HasAttributeFilter("itemprop", "author"));
				//��ȡpaper title��filter
				NodeFilter filter_title = new AndFilter(new TagNameFilter("span"),
						new HasAttributeFilter("class", "title"));

				//����journal��
				NodeList nodelist = parser.parse(titleFilter);					
				Node node_name = nodelist.elementAt(0);
				name_J = node_name.toPlainTextString().trim();
				//System.out.println("name is " + name);
				//System.out.println("++++++++++++++++++++++++++++++++++++++");
//				MetaTag mt = (MetaTag) nodelist.elementAt(0);
//				conJourName = mt.getMetaContent();
				info.setName_J(name_J);
						
				//����Parser
				parser.reset();
						
				//����author
				nodelist = parser.extractAllNodesThatMatch(filter_author);
				//Node[] node_author = nodelist.toNodeArray();
				for(int i = 0; i < nodelist.size(); i++){
					Node node_author = nodelist.elementAt(i);
					//System.out.println("author " + i + " :" + node_author.toPlainTextString().trim());
					//System.out.println("=========================================");
					authors_J += node_author.toPlainTextString().trim()+"_";
				}
				//System.out.println("================="+authors+"========================");
				info.setAuthors_J(authors_J);

				
				parser.reset();
				
				//����paper title
				nodelist = parser.extractAllNodesThatMatch(filter_title);
				//Node[] node_title = nodelist.toNodeArray();
				for(int i = 0; i < nodelist.size(); i++){
					Node node_title = nodelist.elementAt(i);
					//System.out.println("title " + i + " :" + node_title.toPlainTextString().trim());
					//System.out.println("****************************************");
					titles_J += node_title.toPlainTextString().trim()+"_";
				}
				//System.out.println("*****************"+titles+"***********************");
				info.setTitles_J(titles_J);			

				
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return info;				
	}
	
	
	/**
	 * ���ļ��а����ݶ��������е����ݷ���һ��String�з���
	 * @param file
	 * @return
	 */
	public static String loadFileToString(File file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			StringBuffer sb = new StringBuffer();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			br.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * ��json����д���ļ�
	 * @param args
	 * @throws IOException
	 * @throws ParserException
	 */
	public static void WriteToFile(String filename, String str) throws IOException{
		FileWriter fw = new FileWriter(filename,true);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(str);
		bw.write("\r\n");
		bw.close();
		fw.close();
		System.out.println("Write Done!!");
	}
	
	

	//test
		public static void main(String[] args) throws IOException, ParserException{
			
			/*test for SingleHTMLParserJournal
			String f = "F:\\acube\\SearchEngine\\htmls\\B0\\Volume 3- 2016.html";
			Info info = new Info();
			info = SingleHTMLParserJournal(f);
			System.out.println("Name is " + info.getName_J());
			System.out.println("path is " + info.getPath());
//			System.out.println("filename is " + info.getFilename());
			System.out.println("authors is " + info.getAuthors_J());
			System.out.println("titles is " + info.getTitles_J());
			System.out.println("*******************SingleHTMLParserJournal DONE!!!********************");
			*/
			
			/*test for SingleHTMLParserConference
			String f = "F:\\acube\\SearchEngine\\htmls\\A0\\ACM Inroads\\Volume 1_ 2010.html";
			Info info = new Info();
			info = SingleHTMLParserConference(f);
			System.out.println("Name is " + info.getName_C());
			System.out.println("path is " + info.getPath());
			System.out.println("filename is " + info.getFilename());
			System.out.println("authors is " + info.getAuthors_C());
			System.out.println("titles is " + info.getTitles_C());
			//System.out.println("content is " + info.getContent());
			System.out.println("*******************SingleHTMLParserConference DONE!!!********************");
			*/
			
			/*test for HTMLParserAllJournal
			String inputDir = "F:\\acube\\SearchEngine\\htmls\\B0";
			ArrayList<Info> list = HTMLParserAllJournal(inputDir);
			
			for(int i = 0; i < list.size();i++){
				System.out.println(i + "th Name is " + list.get(i).getAuthors_J());
				System.out.println(i + "th path is " + list.get(i).getPath());
//				System.out.println(i + "th filename is " + list.get(i).getFilename());
				System.out.println(i + "th authors is " + list.get(i).getAuthors_J());
				System.out.println(i + "th titles is " + list.get(i).getTitles_J());
				//System.out.println(i + "th content is " + list.get(i).getContent());
				System.out.println("============="+ i +"==================");
			}
			System.out.println("=============HTMLParserAllJournal Done==================");
			*/
			
			/* test HTMLParserConferenceAll
			String inputDir = "f:\\acube\\SearchEngine\\htmls\\test";
			ArrayList<Info> list = HTMLParserConferenceAll(inputDir);
			for(int i = 0; i < list.size(); i++){
				System.out.println(i + "th Name is " + list.get(i).getName_C());
				System.out.println(i + "th path is " + list.get(i).getPath());
				System.out.println(i + "th filename is " + list.get(i).getFilename());
				System.out.println(i + "th authors is " + list.get(i).getAuthors_C());
				System.out.println(i + "th titles is " + list.get(i).getTitles_C());
				//System.out.println(i + "th content is " + list.get(i).getContent());
				System.out.println("============="+ i +"==================");
				
			}
			System.out.println("=============HTMLParserConferenceAll Done==================");
			*/
			
			/* test HTMLParserAllAuthor*/
			//String inputDir = "f:\\acube\\SearchEngine\\htmls\\C0";
			//String inputDir2 = "f:\\acube\\SearchEngine\\htmls\\B0";
			String inputDir3 = "E:\\SJTU\\SearchEngine\\conference\\Z0";
			//HTMLParserAllAuthor(inputDir);
			//HTMLParserAllJournal(inputDir2);
			HTMLParserConferenceAll(inputDir3);
			
			
			//System.out.println("=============HTMLParserAllAuthor Done==================");
			
		}
	
}
