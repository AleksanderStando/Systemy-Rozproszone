package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.ArgumentOutOfRange;
import iot.DeviceNotActive;
import iot.Fridge;
import iot.scale;

import static java.lang.Math.round;

public class FridgeI extends BasicDeviceI implements Fridge{
    // temperatures stored in celsius
    protected final float MAX_FRIDGE_TEMP;
    protected final float MAX_FREEZER_TEMP;
    protected final float MIN_FRIDGE_TEMP;
    protected final float MIN_FREEZER_TEMP;

    protected float currentFridgeTemp;
    protected float currentFreezerTemp;

    public FridgeI(String name){
        super(name);
        MAX_FRIDGE_TEMP = 12;
        MIN_FRIDGE_TEMP = 0;
        MAX_FREEZER_TEMP = -5;
        MIN_FREEZER_TEMP = -25;

        currentFridgeTemp = 6;
        currentFreezerTemp = -18;
    }

    @Override
    public void setFridgeTemp(float degrees, scale scale, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isFridgeOn())
            throw new DeviceNotActive();

        float tempInCelsius = convertToCelsius(degrees, scale);
        if(tempInCelsius > this.MAX_FRIDGE_TEMP || tempInCelsius < this.MIN_FRIDGE_TEMP)
            throw new ArgumentOutOfRange();
        this.currentFridgeTemp = tempInCelsius;
    }

    @Override
    public float getFridgeTemp(scale scale, Current current) throws DeviceNotActive {
        if(!isFridgeOn())
            throw new DeviceNotActive();
        switch(scale){
            case CELSIUS:
                return currentFridgeTemp;
            case KELVIN:
                return currentFridgeTemp + 273.15f;
            case FAHRENHEIT:
                return currentFridgeTemp * 1.8f + 32.0f;
        }
        return 0;
    }

    @Override
    public void setFreezerTemp(float degrees, scale scale, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isFreezerOn())
            throw new DeviceNotActive();

        float tempInCelsius =  convertToCelsius(degrees, scale);

        if(tempInCelsius > this.MAX_FREEZER_TEMP || tempInCelsius < this.MIN_FREEZER_TEMP)
            throw new ArgumentOutOfRange();
        this.currentFreezerTemp = tempInCelsius;
    }

    @Override
    public float getFreezerTemp(scale scale, Current current) throws DeviceNotActive {
        if(!isFreezerOn())
            throw new DeviceNotActive();
        switch(scale){
            case CELSIUS:
                return currentFreezerTemp;
            case KELVIN:
                return currentFreezerTemp + 273.15f;
            case FAHRENHEIT:
                return currentFreezerTemp * 1.8f + 32.0f;
        }
        return 0;
    }

    private float convertToCelsius(float degrees, scale scale){
        switch(scale){
            case CELSIUS:
                return round(degrees);
            case KELVIN:
                return round(degrees - 273.15f);
            case FAHRENHEIT:
                return round((degrees - 32.0f) / 1.8f);
        }
        return Float.MAX_VALUE;
    }

    protected boolean isFridgeOn(){
        return isDeviceOn;
    }

    protected  boolean isFreezerOn(){
        return isDeviceOn;
    }
}
