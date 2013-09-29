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
import com.yahoo.data.Image;

public class Tumbr {
	public static void main(String[] args) throws IOException {
		String queryUrl = "http://api.tumblr.com/v2/tagged?tag=usc&api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";
		URL url = new URL(queryUrl);
		InputStreamReader isr = new InputStreamReader(url.openStream());
		
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(isr);
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonElement response = jsonObject.get("response");
		JsonArray jsonArray = response.getAsJsonArray();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement blog = jsonArray.get(i);
			JsonObject blogObject = blog.getAsJsonObject();
			
			if (!blogObject.get("type").getAsString().equals("photo")) {
				continue;
			}
			
			JsonElement photos = blogObject.get("photos");
			JsonArray photosArray = photos.getAsJsonArray();
			JsonElement photo = photosArray.get(0);
			JsonElement photoElement = photo.getAsJsonObject().get("original_size");
			JsonObject photoJsonObject = photoElement.getAsJsonObject();
			System.out.println(photoJsonObject.get("width").getAsInt());
			System.out.println(photoJsonObject.get("height").getAsInt());
			System.out.println(photoJsonObject.get("url").getAsString());
		//	JsonObject photosObject = photos.getAsJsonObject();
		//	JsonElement original = photosObject.get("original_size");
			System.out.println();
		}
	}
	
	public static Image[] getImages(String tag) {
		ArrayList<Image> result = new ArrayList<Image>();

		try {
			String queryUrl = "http://api.tumblr.com/v2/tagged?tag=" + URLEncoder.encode(tag, "UTF-8")+ "&api_key=fuiKNFp9vQFvjLNvx4sUwti4Yb5yGutBN4Xh10LXZhhRKjWlV4";
			
			URL url = new URL(queryUrl);

			InputStreamReader isr = new InputStreamReader(url.openStream());
			
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(isr);
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			JsonElement response = jsonObject.get("response");
			JsonArray jsonArray = response.getAsJsonArray();
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonElement blog = jsonArray.get(i);
				JsonObject blogObject = blog.getAsJsonObject();
				
				if (!blogObject.get("type").getAsString().equals("photo")) {
					continue;
				}
				
				JsonElement photos = blogObject.get("photos");
				JsonArray photosArray = photos.getAsJsonArray();
				JsonElement photo = photosArray.get(0);
				JsonElement photoElement = photo.getAsJsonObject().get("original_size");
				JsonObject photoJsonObject = photoElement.getAsJsonObject();
				
				Image image = new Image(photoJsonObject.get("url").getAsString(), photoJsonObject.get("width").getAsInt(), photoJsonObject.get("height").getAsInt());
				
				result.add(image);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toArray(new Image[result.size()]);
	}
}
