from pythonClient.implementation import weather_pb2, weather_pb2_grpc
import grpc
import time

def run_client():
    channel = grpc.insecure_channel('localhost:12321')
    stub = weather_pb2_grpc.WeatherStub(channel)
    countryName = input("Enter country name\n")
    start(stub, countryName)

def start(stub, countryName):
    try:
        launch_request(stub, countryName)
    except grpc.RpcError as err:
        status_code = err.code()
        if status_code == grpc.StatusCode.UNAVAILABLE:
            print("Lost connection, trying to reconnect")
            time.sleep(2)
            start(stub, countryName)
        if status_code == grpc.StatusCode.INVALID_ARGUMENT:
            print("Wrong country name, try again")
            run_client()
        else:
            print("Unknown error, shuting down")


def launch_request(stub, countryName):
    countryRequest = weather_pb2.CountryRequest(countryName=countryName)
    for forecast in stub.subscribe(countryRequest):
        print("{0}-{1}-{2} {3}:{4}:{5}".format(
            forecast.time.year,
            forecast.time.month,
            forecast.time.day,
            forecast.time.hour,
            forecast.time.minute,
            forecast.time.seconds
        ))

        for region in forecast.regionForecast:
            print("Region: {}, cloudiness: {}, temperature: {:.1f} {}".format(
                region.regionName,
                region.Cloudiness.Name(region.cloudiness),
                region.temp,
                region.Scale.Name(region.scale)
            ))

if __name__ == "__main__":
    run_client()
