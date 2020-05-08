import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ForecastGenerator {
    private Country country;

    public ForecastGenerator(Country country){
        this.country = country;
    }

    public WeatherOuterClass.ForecastResponse getGeneratedResponse(){
        ArrayList<WeatherOuterClass.RegionForecast> regionForecasts = new ArrayList<WeatherOuterClass.RegionForecast>();
        Random rd = new Random();

        for(String regionName : country.getRegionNames() ){
            // random value from minTemp to maxTemp (minTemp and maxTemp are values specific for a country)
            double temp = rd.nextDouble() * ( country.getMaxTemp() - country.getMinTemp() ) + country.getMinTemp();
            int cloudiness = rd.nextInt(3);
            WeatherOuterClass.RegionForecast regionForecast = WeatherOuterClass.RegionForecast.newBuilder()
                    .setCloudinessValue(cloudiness)
                    .setTemp(temp)
                    .setRegionName(regionName)
                    .setScale(country.getTempScale())
                    .build();
            regionForecasts.add(regionForecast);
        }
        System.currentTimeMillis();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy MM dd HH mm ss");
        Date date = new Date(System.currentTimeMillis());
        String dateStr = formatter.format(date);
        /*  dateData[0] <- year
            dateData[1] <- month
            dateData[2] <- day
            dateData[3] <- hour
            dateData[4] <- minute
            dateData[5] <- seconds
        */
        String[] dateData = dateStr.split(" ");

        WeatherOuterClass.ForecastResponse resp = WeatherOuterClass.ForecastResponse.newBuilder()
                .setTime(
                        WeatherOuterClass.Time.newBuilder()
                                .setYear(new Integer(dateData[0]))
                                .setMonth(new Integer(dateData[1]))
                                .setDay(new Integer(dateData[2]))
                                .setHour(new Integer(dateData[3]))
                                .setMinute(new Integer(dateData[4]))
                                .setSeconds(new Integer(dateData[5]))
                                .build()
                )
                .addAllRegionForecast(regionForecasts)
                .build();

        return resp;
    }

}
