import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountryManager {
    private Map<String, Country> countries;

    public CountryManager(JsonArray configData){

        countries = new HashMap<>();

        Gson gson = new Gson();
        Type stringType = new TypeToken<String>() {}.getType();
        Type listType = new TypeToken<List<String>>() {}.getType();
        Type doubleType = new TypeToken<Double>() {}.getType();
        Type intType = new TypeToken<Integer>() {}.getType();

        for(JsonElement element : configData){
            JsonObject country = element.getAsJsonObject();

            JsonPrimitive countryNameJson = country.getAsJsonPrimitive("countryName");
            String countryName = gson.fromJson(countryNameJson, stringType);

            JsonElement regionsJSON = country.get("regions");
            ArrayList<String> regions = gson.fromJson(regionsJSON.toString(), listType);

            JsonPrimitive minTempJson = country.getAsJsonPrimitive("minTemp");
            double minTemp = gson.fromJson(minTempJson, doubleType);

            JsonPrimitive maxTempJson = country.getAsJsonPrimitive("maxTemp");
            double maxTemp = gson.fromJson(maxTempJson, doubleType);

            JsonPrimitive scaleJson = country.getAsJsonPrimitive("scale");
            WeatherOuterClass.RegionForecast.Scale scale = gson.fromJson(scaleJson, WeatherOuterClass.RegionForecast.Scale.class);

            countries.put(countryName, new Country(countryName, regions, minTemp, maxTemp, scale));
        }
    }

    public List<Country> getCountries(){
        return new ArrayList<Country>(countries.values());
    }

    public Country getCity(String cityName){
        return countries.get(cityName);
    }
}
