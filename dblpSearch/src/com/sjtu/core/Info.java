package com.sjtu.core;

public class Info {				
	//file path
	private String path;				
			
	//browser authors page
	private String name_A = null;
		
	private String authors_A = null;
		
	private String titles_A = null;
		
	//browser conference page
	private String name_C = null;
		
	private String titles_C = null;
		
	private String authors_C = null;
		
	//browser journal page
	private String name_J = null;
			
	private String titles_J = null;
			
	private String authors_J = null;
	
	private double score;

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName_A() {
		return name_A;
	}

	public void setName_A(String name_A) {
		this.name_A = name_A;
	}

	public String getAuthors_A() {
		return authors_A;
	}

	public void setAuthors_A(String authors_A) {
		this.authors_A = authors_A;
	}

	public String getTitles_A() {
		return titles_A;
	}

	public void setTitles_A(String titles_A) {
		this.titles_A = titles_A;
	}

	public String getName_C() {
		return name_C;
	}

	public void setName_C(String name_C) {
		this.name_C = name_C;
	}

	public String getTitles_C() {
		return titles_C;
	}

	public void setTitles_C(String titles_C) {
		this.titles_C = titles_C;
	}

	public String getAuthors_C() {
		return authors_C;
	}

	public void setAuthors_C(String authors_C) {
		this.authors_C = authors_C;
	}

	public String getName_J() {
		return name_J;
	}

	public void setName_J(String name_J) {
		this.name_J = name_J;
	}

	public String getTitles_J() {
		return titles_J;
	}

	public void setTitles_J(String titles_J) {
		this.titles_J = titles_J;
	}

	public String getAuthors_J() {
		return authors_J;
	}

	public void setAuthors_J(String authors_J) {
		this.authors_J = authors_J;
	}
	
}
