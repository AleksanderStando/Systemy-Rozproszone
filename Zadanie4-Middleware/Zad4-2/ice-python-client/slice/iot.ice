#ifndef IOT_ICE
#define IOT_ICE

module iot
{
  enum scale { CELSIUS, FAHRENHEIT, KELVIN };
  sequence <string> devices;

  exception ArgumentOutOfRange {};
  exception DeviceNotActive {};

  interface DeviceList {
    devices getDeviceList();
  };

  interface BasicDevice {
    void switchOn();
    void switchOff();
  };

  interface Fridge extends BasicDevice {
    void setFridgeTemp(float degrees, scale scale) throws ArgumentOutOfRange, DeviceNotActive;
    float getFridgeTemp(scale scale) throws DeviceNotActive;
    void setFreezerTemp(float degrees, scale scale) throws ArgumentOutOfRange, DeviceNotActive;
    float getFreezerTemp(scale scale) throws DeviceNotActive;
  };

  interface LightingFridge extends Fridge {
    void setFridgeLight(int value) throws ArgumentOutOfRange, DeviceNotActive;
    int getFridgeLight() throws DeviceNotActive;
  };

  interface FreezerFridge extends Fridge {
    void switchFreezerOff();
    void switchFreezerOn();
    void switchFridgeOff();
    void switchFridgeOn();
  };

  interface Camera extends BasicDevice {
    void setZoom(float zoomDegree) throws ArgumentOutOfRange, DeviceNotActive;
    float getCurrentZoom() throws DeviceNotActive;
    float getMaxZoom() throws DeviceNotActive;

    void setTilt(float tiltDegree) throws ArgumentOutOfRange, DeviceNotActive;
    void turnUp(float tiltDegree) throws ArgumentOutOfRange, DeviceNotActive;
    void turnDown(float tiltDegree) throws ArgumentOutOfRange, DeviceNotActive;
    float getCurrentTilt() throws DeviceNotActive;
    float getMinTilt() throws DeviceNotActive;
    float getMaxTilt() throws DeviceNotActive;

    void setPan(float panDegree) throws ArgumentOutOfRange, DeviceNotActive;
    void turnLeft(float panDegree) throws ArgumentOutOfRange, DeviceNotActive;
    void turnRight(float panDegree) throws ArgumentOutOfRange, DeviceNotActive;
    float getCurrentPan() throws DeviceNotActive;
    float getMaxPan() throws DeviceNotActive;
    float getMinPan() throws DeviceNotActive;

  };

};

#endif
