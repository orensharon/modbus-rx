package com.sharono.example.modbusrx;

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import io.reactivex.Completable;
import io.reactivex.Observable;

public class ModBusRx implements IModBusRx {

    private final AbstractModbusMaster master;
    private final IGateway gateway;

    public ModBusRx(AbstractModbusMaster master) {
        if (master == null) {
            throw new NullPointerException();
        }
        this.master = master;
        this.gateway = new Gateway(this.master);
    }

    public void connect(int timeout) throws Exception {
        this.master.connect();
        this.master.setTimeout(timeout);
    }

    @Override
    public Completable writeHoldingRegister(final int deviceId, final int address, final int data) {
        return this.gateway.writeHoldingRegister(deviceId, address, data);
    }

    @Override
    public Completable writeHoldingRegisters(int deviceId, int address, int[] data) {
        return this.gateway.writeHoldingRegisters(deviceId, address, data);
    }

    @Override
    public Completable writeCoil(int deviceId, int address, boolean coil) {
        return this.gateway.writeCoil(deviceId, address, coil);
    }

    @Override
    public Completable writeCoils(int deviceId, int address, boolean[] coils) {
        return this.gateway.writeCoils(deviceId, address, coils);
    }

    @Override
    public Observable<Integer[]> readInputRegisters(int deviceId, int address, int length) {
        return this.gateway.readInputRegisters(deviceId, address, length);
    }

    @Override
    public Observable<Integer[]> readHoldingRegisters(int deviceId, int address, int length) {
        return this.gateway.readHoldingRegisters(deviceId, address, length);
    }

    @Override
    public Observable<Boolean[]> readDiscreteInputRegisters(int deviceId, int address, int length) {
        return this.gateway.readDiscreteInputRegisters(deviceId, address, length);
    }

    @Override
    public Observable<Boolean[]> readCoils(int deviceId, int address, int length) {
        return this.gateway.readCoils(deviceId, address, length);
    }
}
