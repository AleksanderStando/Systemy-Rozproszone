import io.grpc.stub.StreamObserver;

import java.util.*;

public class ForecastManager {
    CountryManager countryManager;
    Map<Country, ArrayList<StreamObserver<WeatherOuterClass.ForecastResponse>>> subscribedStreams;

    public ForecastManager(CountryManager countryManager){
        this.countryManager = countryManager;
        this.subscribedStreams = new HashMap<Country, ArrayList<StreamObserver<WeatherOuterClass.ForecastResponse>>>();
        for(Country country : countryManager.getCountries()){
            subscribedStreams.put(country, new ArrayList<StreamObserver<WeatherOuterClass.ForecastResponse>>());
        }
    }

    public void subscribe(Country country, StreamObserver<WeatherOuterClass.ForecastResponse> stream){
        subscribedStreams.get(country).add(stream);
    }

    public void unsubscribe(Country country, StreamObserver<WeatherOuterClass.ForecastResponse> stream){
        subscribedStreams.get(country).remove(stream);
    }

    public void notify(Country country, WeatherOuterClass.ForecastResponse response){
        ArrayList<StreamObserver<WeatherOuterClass.ForecastResponse>> streamsToNotify = new ArrayList<>(subscribedStreams.get(country));
        for(StreamObserver<WeatherOuterClass.ForecastResponse> stream : streamsToNotify){
            try{
                stream.onNext(response);
            } catch(io.grpc.StatusRuntimeException e){
                System.out.println("Error in sending data, removing subscription");
                unsubscribe(country, stream);
            }
        }
    }
}
