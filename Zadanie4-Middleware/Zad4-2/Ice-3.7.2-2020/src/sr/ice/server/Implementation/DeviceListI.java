package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.DeviceList;

public class DeviceListI implements DeviceList {
    protected String[] deviceList;

    public DeviceListI(){
        String[] devices = {"camera1, camera2, fridge1, lightingFridge1, freezerFridge1"};
        this.deviceList = devices;
    }

    @Override
    public String[] getDeviceList(Current current) {
        return deviceList;
    }
}
