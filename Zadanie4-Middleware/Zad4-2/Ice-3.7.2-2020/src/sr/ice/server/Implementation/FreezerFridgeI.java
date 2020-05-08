package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.FreezerFridge;

public class FreezerFridgeI extends FridgeI implements FreezerFridge {
    protected boolean isFreezerOn;
    protected boolean isFridgeOn;

    public FreezerFridgeI(String name){
        super(name);
        isFreezerOn = false;
        isFridgeOn = false;
    }

    @Override
    public void switchFreezerOff(Current current) {
        isFreezerOn = false;
    }

    @Override
    public void switchFreezerOn(Current current) {
        isFreezerOn = true;
    }

    @Override
    public void switchFridgeOff(Current current) {
        isFridgeOn = false;
    }

    @Override
    public void switchFridgeOn(Current current) {
        isFridgeOn = true;
    }

    @Override
    public void switchOn(Current current){
        isFreezerOn = true;
        isFridgeOn = true;
    }

    @Override
    public void switchOff(Current current){
        isFreezerOn = false;
        isFridgeOn = false;
    }

    @Override
    protected boolean isFreezerOn(){
        return isFreezerOn;
    }

    @Override
    protected boolean isFridgeOn(){
        return isFridgeOn;
    }

}
