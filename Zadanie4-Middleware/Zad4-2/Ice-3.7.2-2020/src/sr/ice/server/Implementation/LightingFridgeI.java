package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.ArgumentOutOfRange;
import iot.DeviceNotActive;
import iot.LightingFridge;

public class LightingFridgeI extends FridgeI implements LightingFridge {

    protected int lightValue;

    public LightingFridgeI(String name){
        super(name);
    }

    @Override
    public void setFridgeLight(int value, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        if(value < 0 || value > 100)
            throw new ArgumentOutOfRange();

        this.lightValue = value;
    }

    @Override
    public int getFridgeLight(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();

        return lightValue;
    }


}
