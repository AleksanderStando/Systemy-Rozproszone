package sr.ice.server;

import com.zeroc.Ice.*;
import com.zeroc.Ice.Object;
import iot.*;
import sr.ice.server.Implementation.*;

public class MyServantLocator implements ServantLocator {

    public MyServantLocator(){

    }

    @Override
    public LocateResult locate(Current current) throws UserException {
        String name = current.id.name;

        ObjectAdapter adapter = current.adapter;

        switch(name){
            case "camera1":
                Camera camera1 = new CameraI("camera1");
                adapter.add(camera1, new Identity("camera1", "devices"));
                return new ServantLocator.LocateResult(camera1, null);
            case "camera2":
                Camera camera2 = new CameraI("camera2");
                adapter.add(camera2, new Identity("camera2", "devices"));
                return new ServantLocator.LocateResult(camera2, null);
            case "fridge1":
                Fridge fridge1 = new FridgeI("fridge1");
                adapter.add(fridge1, new Identity("fridge1", "devices"));
                return new ServantLocator.LocateResult(fridge1, null);
            case "lightingFridge1":
                LightingFridge lightingFridge1 = new LightingFridgeI("lightingFridge1");
                adapter.add(lightingFridge1, new Identity("lightingFridge1", "devices"));
                return new ServantLocator.LocateResult(lightingFridge1, null);
            case "freezerFridge1":
                FreezerFridge freezerFridge1 = new FreezerFridgeI("freezerFridge1");
                adapter.add(freezerFridge1, new Identity("freezerFridge1", "devices"));
                return new ServantLocator.LocateResult(freezerFridge1, null);
            case "deviceList":
                DeviceList deviceList = new DeviceListI();
                adapter.add(deviceList, new Identity("deviceList", "devices"));
                return new ServantLocator.LocateResult(deviceList, null);
        }

        return null;

    }

    @Override
    public void deactivate(String s) {

    }

    @Override
    public void finished(Current current, Object object, java.lang.Object o) throws UserException {

    }
}
