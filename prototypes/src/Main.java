import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

	public static void main(String[] args) {

		try {

			// URL url = new URL(
			// "https://places.cit.api.here.com/places/v1/autosuggest?at=48.71494,9.39371&q=rewe&app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&tf=plain&pretty=true");

			// URL url = new URL(
			// "https://places.cit.api.here.com/places/v1/categories/places?app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&at=48.71494,9.39371&pretty=true");

			URL url = new URL(
					"https://places.cit.api.here.com/places/v1/discover/explore?in=48.71494,9.39371;r=50&cat=food-drink&app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&pretty=true");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			String json = "";
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				json += output;

				System.out.println(output);
				
			}
//			System.out.println(json);

			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(json).getAsJsonObject();
			JsonObject results = obj.get("results").getAsJsonObject();

			JsonArray items = results.get("items").getAsJsonArray();

			JsonObject shop = items.get(0).getAsJsonObject();

			String shopName = shop.get("title").getAsString();

			System.out.println(shopName);

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
