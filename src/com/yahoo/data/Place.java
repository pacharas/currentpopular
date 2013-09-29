package com.yahoo.data;

public class Place implements Comparable<Place>{
	private String name;
	private int woeid;
	
	public Place() {
	}
	
	public Place(String name, int woeid) {
		this.name = name;
		this.woeid = woeid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWoeid() {
		return woeid;
	}
	public void setWoeid(int woeid) {
		this.woeid = woeid;
	}

	@Override
	public int compareTo(Place o) {
		return this.name.compareTo(o.name);
	}
	
	
}
