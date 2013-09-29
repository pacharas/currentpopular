package com.yahoo;

import java.util.ArrayList;
import java.util.Collections;

import com.yahoo.data.Place;

import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Twitter {
	
	private static Place[] cachePlaces;
	
	static {
		
	}
	
	public static void main(String[] args) throws TwitterException {
		System.out.println("start....3");
		twitter4j.Twitter twitter = TwitterFactory.getSingleton();
		//List<Status> statuses = twitter.getHomeTimeline();
	    //System.out.println("Showing home timeline.");
	    //for (Status status : statuses) {
	     //   System.out.println(status.getUser().getName() + ":" +
	       //                    status.getText());
	   // }
		
		ResponseList<Location> locations = twitter.getAvailableTrends();
		for (Location location : locations) {
			System.out.println(location.getWoeid());
		}
		
		Trends trends = twitter.getPlaceTrends(2464592);
		Trend[] trns = trends.getTrends();
		for (Trend trend : trns) {
			System.out.println(trend.getName());
		}
	}
	
	public static String[] getTrends(int oweid) {
		twitter4j.Twitter twitter = TwitterFactory.getSingleton();
		Trends trends = null;
		try {
			trends = twitter.getPlaceTrends(oweid);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		ArrayList<String> result = new ArrayList<String>();
		
		if (trends != null) {
			Trend[] trns = trends.getTrends();
			for (Trend trend : trns) {
				result.add(trend.getName());
			}
		}
		return result.toArray(new String[result.size()]);
	}
	
	private static void loadPlaces() {
		twitter4j.Twitter twitter = TwitterFactory.getSingleton();
		ResponseList<Location> locations = null;
		try {
			locations = twitter.getAvailableTrends();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		ArrayList<Place> places = new ArrayList<Place>();
		
		if (locations != null) {
			for (Location location : locations) {
				Place place = new Place(location.getName(), location.getWoeid());
				places.add(place);
			}
		}

		Collections.sort(places);
		cachePlaces = places.toArray(new Place[places.size()]);
	}
	
	public static Place[] getAvailablePlaces() {
		if (cachePlaces == null || cachePlaces.length == 0) {
			loadPlaces();
		}
		return cachePlaces;
	}
}
