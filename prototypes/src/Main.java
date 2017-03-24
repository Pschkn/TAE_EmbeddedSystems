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

			URL url = new URL(
					"https://places.cit.api.here.com/places/v1/autosuggest?at=48.7137376,9.3942463&q=rewe&app_id=iY1aAtZDpreaMQ6wrjzm&app_code=kwWoYMR9pQ3qINj3Y8jbLQ&tf=plain&pretty=true");

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
