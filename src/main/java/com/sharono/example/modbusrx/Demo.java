package com.sharono.example.modbusrx;

import com.ghgande.j2mod.modbus.util.SerialParameters;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.Arrays;

import static com.ghgande.j2mod.modbus.Modbus.SERIAL_ENCODING_RTU;
import static com.ghgande.j2mod.modbus.net.AbstractSerialConnection.NO_PARITY;

public class Demo {

    public static void main(String[] args) throws Exception {

        SerialParameters parameters = new SerialParameters();
        parameters.setBaudRate(115200);
        parameters.setPortName("COM7");
        parameters.setDatabits(8);
        parameters.setStopbits(1);
        parameters.setEncoding(SERIAL_ENCODING_RTU);
        parameters.setEcho(false);
        parameters.setParity(NO_PARITY);
        ModBusRx modBusRx = new ModBusRx(ModBusMasterFactory.getSerialMaster(parameters));
        modBusRx.connect(100);


        Observable<Integer[]> read = modBusRx.readHoldingRegisters(1, 106, 1);
        Observable.concat(
                modBusRx.writeHoldingRegister(1, 106,10).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 20).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 30).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 40).andThen(read)
        ).subscribe(integers -> System.out.println(Arrays.toString(integers)));
        Thread.sleep(1000L);
    }
}
