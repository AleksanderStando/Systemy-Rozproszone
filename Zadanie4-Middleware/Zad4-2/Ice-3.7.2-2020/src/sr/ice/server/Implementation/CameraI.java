package sr.ice.server.Implementation;

import com.zeroc.Ice.Current;
import iot.ArgumentOutOfRange;
import iot.Camera;
import iot.DeviceNotActive;

public class CameraI extends BasicDeviceI implements Camera {

    protected final float MAX_ZOOM;
    protected final float MIN_ZOOM;

    protected final float MAX_TILT;
    protected final float MIN_TILT;

    protected final float MAX_PAN;
    protected final float MIN_PAN;

    protected float currZoom;
    protected float currTilt;
    protected float currPan;

    public CameraI(String name){
        super(name);
        MAX_ZOOM = 5.0f;
        MIN_ZOOM = 1.0f;
        MAX_TILT = 20.0f;
        MIN_TILT = -50.0f;
        MAX_PAN = 45.0f;
        MIN_PAN = -45.0f;

        currZoom = 1.0f;
        currTilt = 0.0f;
        currPan = 0.0f;
    }

    @Override
    public void setZoom(float zoomDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        if(zoomDegree > MAX_ZOOM || zoomDegree < MIN_ZOOM)
            throw new ArgumentOutOfRange();
        this.currZoom = zoomDegree;
    }

    @Override
    public float getCurrentZoom(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return currZoom;
    }

    @Override
    public float getMaxZoom(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return MAX_ZOOM;
    }

    @Override
    public void setTilt(float tiltDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        if(tiltDegree > MAX_TILT || tiltDegree < MIN_TILT)
            throw new ArgumentOutOfRange();
        currTilt = tiltDegree;
    }

    @Override
    public void turnUp(float tiltDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        if(tiltDegree < 0)
            throw  new ArgumentOutOfRange();

        float newTilt = tiltDegree + currTilt;
        if(newTilt > MAX_TILT)
            throw new ArgumentOutOfRange();

        currTilt = newTilt;
    }

    @Override
    public void turnDown(float tiltDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();

        if(tiltDegree < 0)
            throw  new ArgumentOutOfRange();

        float newTilt = currTilt - tiltDegree;
        if(newTilt < MIN_TILT)
            throw new ArgumentOutOfRange();

        currTilt = newTilt;
    }

    @Override
    public float getCurrentTilt(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return currTilt;
    }

    @Override
    public float getMinTilt(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return MIN_TILT;
    }

    @Override
    public float getMaxTilt(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return MAX_TILT;
    }

    @Override
    public void setPan(float panDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        if(panDegree > MAX_PAN || panDegree < MIN_PAN)
            throw new ArgumentOutOfRange();

        currPan = panDegree;
    }

    @Override
    public void turnLeft(float panDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();

        if(panDegree < 0)
            throw new ArgumentOutOfRange();

        float newPan = currPan - panDegree;
        if(newPan < MIN_PAN)
            throw new ArgumentOutOfRange();

        currPan = newPan;
    }

    @Override
    public void turnRight(float panDegree, Current current) throws ArgumentOutOfRange, DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();

        if(panDegree < 0)
            throw new ArgumentOutOfRange();

        float newPan = currPan + panDegree;
        if(newPan > MAX_PAN)
            throw new ArgumentOutOfRange();

        currPan = newPan;
    }

    @Override
    public float getCurrentPan(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return currPan;
    }

    @Override
    public float getMaxPan(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return MAX_PAN;
    }

    @Override
    public float getMinPan(Current current) throws DeviceNotActive {
        if(!isDeviceOn)
            throw new DeviceNotActive();
        return MIN_PAN;
    }

}
