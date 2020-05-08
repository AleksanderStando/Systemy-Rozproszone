import sys
import Ice
Ice.loadSlice('../slice/iot.ice')
import iot


class BasicHandler:
    def __init__(self, communicator):
        self.communicator = communicator
    def printPrompt(self):
        pass
    def printHelp(self):
        print("Podaj jedna z ponizszych nazw: ")
        base = communicator.stringToProxy("devices/deviceList:tcp -h localhost -p 10000")
        deviceList = iot.DeviceListPrx.checkedCast(base)
        print(deviceList.getDeviceList())

    def processCommand(self, command):
        pass
    def takeCommands(self):
        while True:
            try:
                self.printPrompt()
                command = input();
                if command.upper() == 'EXIT':
                    break
                if command.upper() == 'HELP':
                    self.printHelp()
                    continue
                self.processCommand(command)
            except iot.DeviceNotActive:
                print("Device not active!\nTurn on the device first")
            except iot.ArgumentOutOfRange:
                print("Wrong value of argument, device would be in invalid state after executing command!")
            except:
                print("Parsing input went wrong")

class FridgeHandler(BasicHandler):
    def __init__(self, communicator):
        super().__init__(communicator)
    def printPrompt(self):
        print("FRIDGE> ", end ='')
    def processCommand(self, command):
        try:
            base = communicator.stringToProxy("devices/" + command + ":tcp -h localhost -p 10000")
            fridge = iot.FridgePrx.checkedCast(base)
            fridgePrxHandler = FridgePrxHandler(fridge, command)
            fridgePrxHandler.takeCommands()
        except:
            print("Connecting with server gone wrong")

class LightingFridgeHandler(FridgeHandler):
    def __init__(self, communicator):
        super().__init__(communicator)
    def printPrompt(self):
        print("LIGHTING_FRIDGE> ", end ='')
    def processCommand(self, command):
        try:
            base = communicator.stringToProxy("devices/" + command + ":tcp -h localhost -p 10000")
            lighting_fridge = iot.LightingFridgePrx.checkedCast(base)
            lightingFridgePrxHandler = LightingFridgePrxHandler(lighting_fridge, command)
            lightingFridgePrxHandler.takeCommands()
        except:
            print("Connecting with server gone wrong")

class FreezerFridgeHandler(FridgeHandler):
    def __init__(self, communicator):
        super().__init__(communicator)
    def printPrompt(self):
        print("FREEZER_FRIDGE> ", end ='')
    def processCommand(self, command):
        try:
            base = communicator.stringToProxy("devices/" + command + ":tcp -h localhost -p 10000")
            freezer_fridge = iot.FreezerFridgePrx.checkedCast(base)
            freezerFridgePrxHandler = FreezerFridgePrxHandler(freezer_fridge, command)
            freezerFridgePrxHandler.takeCommands()
        except:
            print("Connecting with server gone wrong")

class CameraHandler(BasicHandler):
    def __init__(self, communicator):
        super().__init__(communicator)
    def printPrompt(self):
        print("CAMERA> ", end ='')
    def processCommand(self, command):
        try:
            base = communicator.stringToProxy("devices/" + command + ":tcp -h localhost -p 10000")
            camera = iot.CameraPrx.checkedCast(base)
            cameraPrxHandler = CameraPrxHandler(camera, command)
            cameraPrxHandler.takeCommands()
        except:
            print("Connecting with server gone wrong")

class FridgePrxHandler(BasicHandler):
    def __init__(self, fridge, name):
        self.fridge = fridge
        self.name = name
    def printPrompt(self):
        print("FRIDGE {}> ".format(self.name), end ='')

    def printHelp(self):
        print("switchOn -> 1")
        print("switchOff -> 2")
        print("setFridgeTemp -> 3 [value] [C | F | K]")
        print("getFridgeTemp -> 4 [C | F | K]")
        print("setFreezerTemp -> 5 [value] [C | F | K]")
        print("getFreezerTemp -> 6 [C | F | K]")

    def processCommand(self, command):
        cmd = command.split()
        if cmd[0] == "1":
            self.fridge.switchOn()
        if cmd[0] == "2":
            self.fridge.switchOff()
        if cmd[0] == "3":
            unit = self.getUnit(cmd[2])
            self.fridge.setFridgeTemp(float(cmd[1]), unit)
        if cmd[0] == "4":
            unit = self.getUnit(cmd[1])
            print(self.fridge.getFridgeTemp(unit))
        if cmd[0] == "5":
            unit = self.getUnit(cmd[2])
            self.fridge.setFreezerTemp(float(cmd[1]), unit)
        if cmd[0] == "6":
            unit = self.getUnit(cmd[1])
            print(self.fridge.getFreezerTemp(unit))

    def getUnit(self, symbol):
        if symbol == 'K':
            return iot.scale.KELVIN
        if symbol == 'C':
            return iot.scale.CELSIUS
        if symbol == 'F':
            return iot.scale.FAHRENHEIT
        return None

class FreezerFridgePrxHandler(FridgePrxHandler):
    def __init__(self, fridge, name):
        self.fridge = fridge
        self.name = name
    def printPrompt(self):
        print("FREEZER FRIDGE {}> ".format(self.name), end ='')
    def printHelp(self):
        super().printHelp()
        print("switchFreezerOn -> 11")
        print("switchFreezerOff -> 12")
        print("switchFridgeOn -> 13")
        print("switchFridgeOff -> 14")

    def processCommand(self, command):
        super().processCommand(command)
        cmd = command.split(' ')
        if cmd[0] == "11":
            self.fridge.switchFreezerOn()
        if cmd[0] == "12":
            self.fridge.switchFreezerOff()
        if cmd[0] == "13":
            self.fridge.switchFridgeOn()
        if cmd[0] == "14":
            self.fridge.switchFridgeOff()

class LightingFridgePrxHandler(FridgePrxHandler):
    def __init__(self, fridge, name):
        self.fridge = fridge
        self.name = name
    def printPrompt(self):
        print("LIGHTING FRIDGE {}> ".format(self.name), end ='')
    def printHelp(self):
        super().printHelp()
        print("setFridgeLight -> 11 [value (0 - 100)]")
        print("getFridgeLight -> 12")
    def processCommand(self, command):
        super().processCommand(command)
        cmd = command.split(' ')
        if cmd[0] == "11":
            self.fridge.setFridgeLight(int(cmd[1]))
        if cmd[0] == "12":
            print(self.fridge.getFridgeLight())

class CameraPrxHandler(BasicHandler):
    def __init__(self, camera, name):
        self.camera = camera
        self.name = name
    def printPrompt(self):
        print("CAMERA {}> ".format(self.name), end ='')
    def printHelp(self):
        print("switchOn -> 1")
        print("switchOff -> 2")
        print("getCurrentPan -> 3")
        print("getMaxPan -> 4")
        print("getMinPan -> 5")
        print("setPan -> 6 [pan value]")
        print("turnLeft -> 7 [value]")
        print("turnRight -> 8 [value]")
        print("getCurrentTile -> 9")
        print("getMinTilt -> 10")
        print("getMaxTile -> 11")
        print("setTilt -> 12 [tilt value]")
        print("turnUp -> 13 [value]")
        print("turnDown -> 14 [value]")
        print("getCurrentZoom -> 15")
        print("getMaxZoom -> 16")
        print("setZoom -> 17 [value]")




    def processCommand(self, command):
            cmd = command.split()
            if cmd[0] == "1":
                self.camera.switchOn()
            if cmd[0] == "2":
                self.camera.switchOff()
            if cmd[0] == "3":
                print(self.camera.getCurrentPan())
            if cmd[0] == "4":
                print(self.camera.getMaxPan())
            if cmd[0] == "5":
                print(self.camera.getMinPan())
            if cmd[0] == "6":
                self.camera.setPan(float(cmd[1]))
            if cmd[0] == "7":
                self.camera.turnLeft(float(cmd[1]))
            if cmd[0] == "8":
                self.camera.turnRight(float(cmd[1]))
            if cmd[0] == "9":
                print(self.camera.getCurrentTilt())
            if cmd[0] == "10":
                print(self.camera.getMinTilt())
            if cmd[0] == "11":
                print(self.camera.getMaxTilt())
            if cmd[0] == "12":
                self.camera.setTilt(float(cmd[1]))
            if cmd[0] == "13":
                self.camera.turnUp(float(cmd[1]))
            if cmd[0] == "14":
                self.camera.turnDown(float(cmd[1]))
            if cmd[0] == "15":
                print(self.camera.getCurrentZoom())
            if cmd[0] == "16":
                print(self.camera.getMaxZoom())
            if cmd[0] == "17":
                self.camera.setZoom(float(cmd[1]))


def printHelp():
    print("exit <- to exit")
    print("fridge <- to access basic fridge")
    print("lighting <- to access fridge with light settings")
    print("freezer <- to access fridge with ability to turn on fridge and freezer seperatly")
    print("camera <- to access camera")



with Ice.initialize(sys.argv) as communicator:
    while True:
        print("> ", end = '')
        command = input().split(' ')
        command[0] = command[0].upper()
        if command[0] == 'CAMERA':
            camera_handler = CameraHandler(communicator)
            camera_handler.takeCommands()
        if command[0] == 'FRIDGE':
            fridge_handler = FridgeHandler(communicator)
            fridge_handler.takeCommands()
        if command[0] == 'LIGHTING':
            lighting_fridge_handler = LightingFridgeHandler(communicator)
            lighting_fridge_handler.takeCommands()
        if command[0] == 'FREEZER':
            freezer_fridge_handler = FreezerFridgeHandler(communicator)
            freezer_fridge_handler.takeCommands()
        if command[0] == 'HELP':
            printHelp()
        if command[0] == 'EXIT':
            break
