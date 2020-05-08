import java.util.HashMap;
import java.util.Map;

public class ForecastNotifier implements Runnable{
    private ForecastManager manager;
    private Map<Country, ForecastGenerator> generators;

    public ForecastNotifier(ForecastManager manager, CountryManager countryManager) {
        this.manager = manager;
        this.generators = new HashMap<Country, ForecastGenerator>();
        for(Country country : countryManager.getCountries()){
            this.generators.put(country, new ForecastGenerator(country));
        }
    }

    @Override
    public void run() {
        while(true){
            try{
                Thread.sleep(5000);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            for(Country country : generators.keySet()){
                WeatherOuterClass.ForecastResponse resp = generators.get(country).getGeneratedResponse();
                manager.notify(country, resp);
            }
            System.out.println("Sending weather forecast to subscribed clients");
        }
    }
}
