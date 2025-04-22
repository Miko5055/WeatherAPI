import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPIData {
    public static void main(String[] args) {
        try{
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                // Retrive user input
                System.out.println("=====================================================");
                System.out.print("Enter City (Say No to quit): ");
                city = scanner.nextLine();

                if (city.equalsIgnoreCase("No")) break;

                // Get location data
                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");

                displayWeatherData(latitude, longitude);
            }while(!city.equalsIgnoreCase("no"));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static JSONObject getLocationData(String city){
        city = city.replaceAll(" ", "+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=Tokyo&count=1&language=en&format=json" +
                city + "&count=1&language=en&format=json";

        try{
            // 1. Fetch the API response based on API Link
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            // Check for response status
            // 200 - means that connection was a success
            if (apiConnection.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }

            // 2. Read the response and convert store String type
            String jsonResponse = readApiResponse(apiConnection);

            // 3. Praise the string into a JSON Object
            JSONPraser = new JSONPraser();
            JSONObject resultJsonObj = (JSONObject) praser.prase(jsonResponse);

            // 4. Retrive Location Data
            JSONArray locationData = (JSONArray) resultJsonObj.get("results");
            return (JSONObject) locationData.get(0);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}