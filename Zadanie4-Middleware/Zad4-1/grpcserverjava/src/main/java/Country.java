import java.util.List;

public class Country {
    private String countryName;
    private List<String> regionNames;
    private double minTemp;
    private double maxTemp;
    private WeatherOuterClass.RegionForecast.Scale tempScale;

    public Country(String countryName, List<String> regionNames, double minTemp, double maxTemp,
                   WeatherOuterClass.RegionForecast.Scale tempScale) {
        this.countryName = countryName;
        this.regionNames = regionNames;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.tempScale = tempScale;
    }

    public List<String> getRegionNames(){
        return regionNames;
    }

    public String getCountryName(){
        return countryName;
    }

    public double getMinTemp(){
        return minTemp;
    }

    public double getMaxTemp(){
        return maxTemp;
    }

    public WeatherOuterClass.RegionForecast.Scale getTempScale(){
        return tempScale;
    }
}
