import com.google.rpc.Code;
import com.google.rpc.Status;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;

import java.util.NoSuchElementException;

public class WeatherService extends WeatherGrpc.WeatherImplBase {
    private ForecastManager forecastManager;
    private CountryManager countryManager;

    public WeatherService(ForecastManager forecastManager, CountryManager countryManager){
        this.forecastManager = forecastManager;
        this.countryManager = countryManager;
    }

    @Override
    public void subscribe(WeatherOuterClass.CountryRequest request, StreamObserver<WeatherOuterClass.ForecastResponse> responseObserver) {
        Country country = countryManager.getCity(request.getCountryName());
        if(country != null){
            System.out.println("Someone subscribed to: " + request.getCountryName());
            forecastManager.subscribe(country, responseObserver);
        }
        else{
            System.out.println("Someone tried to connect, but typed a non-existing country");
            Status status = Status.newBuilder()
                    .setCode(Code.INVALID_ARGUMENT.getNumber())
                    .setMessage("No such country in config file")
                    .build();

            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        }
    }
}
