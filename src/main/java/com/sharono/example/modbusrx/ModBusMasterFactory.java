package com.sharono.example.modbusrx;

import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.util.SerialParameters;

public class ModBusMasterFactory {

    public static ModbusSerialMaster getSerialMaster(SerialParameters parameters) {
        return new ModbusSerialMaster(parameters);
    }

    public static ModbusTCPMaster getTCPMaster(String address, int port) {
        return new ModbusTCPMaster(address, port);
    }
}
