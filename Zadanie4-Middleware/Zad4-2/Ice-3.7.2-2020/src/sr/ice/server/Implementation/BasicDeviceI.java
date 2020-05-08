package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.BasicDevice;

public abstract class BasicDeviceI implements BasicDevice {
    protected boolean isDeviceOn;
    protected String name;

    public BasicDeviceI(String name){
        isDeviceOn = false;
        this.name = name;
    }

    @Override
    public void switchOn(Current current) {
        this.isDeviceOn = true;
    }

    @Override
    public void switchOff(Current current) {
        this.isDeviceOn = false;
    }

    public String getName(){
        return name;
    }
}
