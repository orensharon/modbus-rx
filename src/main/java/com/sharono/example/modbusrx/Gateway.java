package com.sharono.example.modbusrx;

import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import com.ghgande.j2mod.modbus.procimg.InputRegister;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.procimg.SimpleRegister;
import com.ghgande.j2mod.modbus.util.BitVector;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Gateway implements IGateway {

    private AbstractModbusMaster master;

    Gateway(AbstractModbusMaster master) {
        if (master == null) {
            throw new NullPointerException();
        }
        this.master = master;
    }

    @Override
    public Completable writeHoldingRegister(int deviceId, int address, int data) {
        final Register register = new SimpleRegister(data);
        return Completable
                .fromAction(() -> this.master.writeSingleRegister(deviceId, address, register))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable writeHoldingRegisters(int deviceId, int address, int[] data) {
        final Register[] registers = this.getRegisters(data);
        return Completable
                .fromAction(() -> this.master.writeMultipleRegisters(deviceId, address, registers))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable writeCoil(int deviceId, int address, boolean coil) {
        return Completable
                .fromAction(() -> this.master.writeCoil(deviceId, address, coil))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable writeCoils(int deviceId, int address, boolean[] coils) {
        BitVector bitVector = this.getBitVector(coils);
        return Completable
                .fromAction(() -> this.master.writeMultipleCoils(deviceId, address, bitVector))
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Observable<Integer[]> readInputRegisters(int deviceId, int address, int length) {
        return Observable
                .fromCallable(() -> this.master.readInputRegisters(deviceId, address, length))
                .map(this::toIntArray);
    }

    @Override
    public Observable<Integer[]> readHoldingRegisters(int deviceId, int address, int length) {
        return Observable
                .fromCallable(() -> this.master.readMultipleRegisters(deviceId, address, length))
                .map(this::toIntArray);
    }

    @Override
    public Observable<Boolean[]> readDiscreteInputRegisters(int deviceId, int address, int length) {
        return Observable
                .fromCallable(() -> this.master.readInputDiscretes(deviceId, address, length))
                .map(this::toBooleanArray);
    }

    @Override
    public Observable<Boolean[]> readCoils(int deviceId, int address, int length) {
        return Observable
                .fromCallable(() -> this.master.readCoils(deviceId, address, length))
                .map(this::toBooleanArray);
    }

    private BitVector getBitVector(boolean[] coils) {
        final BitVector vector = new BitVector(coils.length);
        for (int i = 0; i < coils.length; i++) {
            vector.setBit(i, coils[i]);
        }
        return vector;
    }

    private Register[] getRegisters(int[] data) {
        final Register[] registers = new SimpleRegister[data.length];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = new SimpleRegister(data[i]);
        }
        return registers;
    }

    private Boolean[] toBooleanArray(BitVector vector) {
        Boolean[] result = new Boolean[vector.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = vector.getBit(i);
        }
        return result;
    }

    private Integer[] toIntArray(InputRegister[] inputRegisters) {
        Integer[] result = new Integer[inputRegisters.length];
        for (int i = 0; i < inputRegisters.length; i++) {
            result[i] = inputRegisters[i].getValue();
        }
        return result;
    }
}
