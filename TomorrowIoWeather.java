import java.net.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class TomorrowIoWeather {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Sisesta ühe euroopa riigi pealinn (näiteks Tallinn või Helsinki): ");
        String city = scanner.nextLine().trim();
        scanner.close();

        String location = getLocationForCity(city);
        if (location == null) {
            System.out.println("Tundmatu linn: " + city);
            return;
        }

        JSONObject json = getJsonObject(location);

        // Vaatame JSON struktuuri: timelines -> hourly
        JSONObject timelines = json.getJSONObject("timelines");
        JSONArray hourly = timelines.getJSONArray("hourly");

        String today = LocalDate.now().format(DateTimeFormatter.ISO_DATE); // yyyy-MM-dd

        for (int i = 0; i < hourly.length(); i++) {
            JSONObject hourData = hourly.getJSONObject(i);
            String time = hourData.getString("time");

            if (time.startsWith(today)) { // Kontrollime kas selle kuupäevaga
                double temperature = hourData.getJSONObject("values").getDouble("temperature");
                System.out.println(time + " " + Math.round(temperature) + "C");
            }
        }
    }

    private static JSONObject getJsonObject(String location) throws IOException {
        String apiKey = "yfA1r8sh29iNr9drZ7LKlAc54My0uJCB";
        URL url = new URL("https://api.tomorrow.io/v4/weather/forecast?location=" + location + "&fields=temperature&timesteps=1h&apikey=" + apiKey);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String jsonString = response.toString();
        return new JSONObject(jsonString);
    }

    private static String getLocationForCity(String city) {
        Map<String, String> cityLocations = new HashMap<>();
        cityLocations.put("tirana", "41.3275,19.8189"); // Albania
        cityLocations.put("andorra la vella", "42.5078,1.5211"); // Andorra
        cityLocations.put("vienna", "48.2082,16.3738"); // Austria
        cityLocations.put("minsk", "53.9006,27.5590"); // Belarus
        cityLocations.put("brussels", "50.8503,4.3517"); // Belgium
        cityLocations.put("sarajevo", "43.8563,18.4131"); // Bosnia and Herzegovina
        cityLocations.put("sofia", "42.6977,23.3219"); // Bulgaria
        cityLocations.put("zagreb", "45.8150,15.9819"); // Croatia
        cityLocations.put("nicosia", "35.1856,33.3823"); // Cyprus
        cityLocations.put("prague", "50.0755,14.4378"); // Czech Republic
        cityLocations.put("copenhagen", "55.6761,12.5683"); // Denmark
        cityLocations.put("tallinn", "59.4370,24.7535"); // Estonia
        cityLocations.put("helsinki", "60.1695,24.9354"); // Finland
        cityLocations.put("paris", "48.8566,2.3522"); // France
        cityLocations.put("berlin", "52.5200,13.4050"); // Germany
        cityLocations.put("athens", "37.9838,23.7275"); // Greece
        cityLocations.put("budapest", "47.4979,19.0402"); // Hungary
        cityLocations.put("reykjavik", "64.1466,-21.9426"); // Iceland
        cityLocations.put("dublin", "53.3498,-6.2603"); // Ireland
        cityLocations.put("rome", "41.9028,12.4964"); // Italy
        cityLocations.put("pristina", "42.6629,21.1655"); // Kosovo
        cityLocations.put("riga", "56.9496,24.1052"); // Latvia
        cityLocations.put("vaduz", "47.1416,9.5215"); // Liechtenstein
        cityLocations.put("vilnius", "54.6872,25.2797"); // Lithuania
        cityLocations.put("luxembourg", "49.6117,6.1319"); // Luxembourg
        cityLocations.put("valletta", "35.8997,14.5146"); // Malta
        cityLocations.put("chisinau", "47.0105,28.8638"); // Moldova
        cityLocations.put("monaco", "43.7384,7.4246"); // Monaco
        cityLocations.put("podgorica", "42.4304,19.2594"); // Montenegro
        cityLocations.put("amsterdam", "52.3676,4.9041"); // Netherlands
        cityLocations.put("skopje", "41.9981,21.4254"); // North Macedonia
        cityLocations.put("oslo", "59.9139,10.7522"); // Norway
        cityLocations.put("warsaw", "52.2297,21.0122"); // Poland
        cityLocations.put("lisbon", "38.7169,-9.1399"); // Portugal
        cityLocations.put("bucharest", "44.4268,26.1025"); // Romania
        cityLocations.put("moscow", "55.7558,37.6173"); // Russia
        cityLocations.put("san marino", "43.9333,12.4500"); // San Marino
        cityLocations.put("belgrade", "44.7866,20.4489"); // Serbia
        cityLocations.put("bratislava", "48.1486,17.1077"); // Slovakia
        cityLocations.put("ljubljana", "46.0569,14.5058"); // Slovenia
        cityLocations.put("madrid", "40.4168,-3.7038"); // Spain
        cityLocations.put("stockholm", "59.3293,18.0686"); // Sweden
        cityLocations.put("bern", "46.9481,7.4474"); // Switzerland
        cityLocations.put("kyiv", "50.4501,30.5234"); // Ukraine
        cityLocations.put("london", "51.5072,-0.1276"); // United Kingdom
        cityLocations.put("vatican city", "41.9029,12.4534"); // Vatican City

        return cityLocations.get(city.toLowerCase());
    }
}
