import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class Main {

	public static void main(String[] args) {

		try {

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

			Gson gson = new Gson();

			MarketDataObject mdo = gson.fromJson(json, MarketDataObject.class);

			System.out.println(mdo.getResults().getItems().get(0).getTitle());

			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
