package com.yahoo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yahoo.data.Video;

public class Youtube {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String queryUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20youtube.search%20where%20query%3D%22usc%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
		URL url = new URL(queryUrl);
		InputStreamReader isr = new InputStreamReader(url.openStream());
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(isr);
		
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonElement query = jsonObject.get("query");
		JsonElement results = query.getAsJsonObject().get("results");
		JsonElement video = results.getAsJsonObject().get("video");
		JsonArray jsonArray = video.getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement vdo = jsonArray.get(i);
			JsonObject vdoObject = vdo.getAsJsonObject();
			String title = vdoObject.get("title").getAsString();
			String vdoUrl = vdoObject.get("url").getAsString();
			System.out.println(title);
			System.out.println(vdoUrl);
		}
	}

	public static Video[] getVideos(String tag) {
		ArrayList<Video> result = new ArrayList<Video>();
		
		try {
			String queryUrl = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20youtube.search%20where%20query%3D%22" + URLEncoder.encode(tag, "UTF-8")+ "%22&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
			URL url = new URL(queryUrl);
			InputStreamReader isr = new InputStreamReader(url.openStream());
			
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(isr);
			
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement query = jsonObject.get("query");
			JsonElement results = query.getAsJsonObject().get("results");
			JsonElement video = results.getAsJsonObject().get("video");
			JsonArray jsonArray = video.getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonElement vdo = jsonArray.get(i);
				JsonObject vdoObject = vdo.getAsJsonObject();
				String title = vdoObject.get("title").getAsString();
				String vdoUrl = vdoObject.get("url").getAsString();
				
				Video thisVideo = new Video(title, vdoUrl);
				result.add(thisVideo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toArray(new Video[result.size()]);
	}
}
