package com.sharono.example.modbusrx;

import io.reactivex.Completable;

public interface IGateway {
    Completable writeHoldingRegister(final int deviceId, final int address, final int data);
    Completable writeHoldingRegisters(final int deviceId, final int address, final int[] data);
    Completable writeCoil(final int deviceId, final int address, final boolean coil);
    Completable writeCoils(final int deviceId, final int address, final boolean[] coils);

/*
    Observable<Integer> readHoldingRegister(int deviceId, int address);
    Observable<Integer[]> readHoldingRegisters(int deviceId, int address, int length);
    Observable<Integer> readInputRegister(int deviceId, int address);
    Observable<Integer[]> readInputRegisters(int deviceId, int address, int length);
    Observable<Integer> readInputRegister(int deviceId, int address);
*/
}
