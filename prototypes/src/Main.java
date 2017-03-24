import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main
{

	public static void main(String[] args)
	{

		try
		{
//
//			URL url = new URL(
//					"https://places.cit.api.here.com/places/v1/autosuggest?at=48.7137376,9.3942463&q=grocery&app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&tf=plain&pretty=true");

//			URL url = new URL(
//					"https://places.cit.api.here.com/places/v1/categories/places?app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&at=48.7137376,9.3942463&pretty=true");

			URL url = new URL(
					"https://places.cit.api.here.com/places/v1/discover/explore?at=48.7137376,9.3942463&cat=shopping&app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&pretty=true");
			
			
//			/places/v1/discover/explore?at=52.5159%2C13.3777&cat=shopping&app_id=DemoAppId01082013GAL&app_code=AJKnXv84fjrb0KIHawS0Tg
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200)
			{
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null)
			{
				System.out.println(output);
			}

			conn.disconnect();

		} catch (MalformedURLException e)
		{
			e.printStackTrace();

		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}
}
