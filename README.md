# Overview
This project use [j2mod](https://github.com/steveohara/j2mod/) library to make ModBus requests with rxjava2.

This implementation supports only Modbus master (TCP, UDP, RTU over TCP, Serial RTU and Serial ASCII).

# Usage:

## Serial:

    SerialParameters parameters = new SerialParameters();
    ModBusRx modBusRx = new ModBusRx(ModBusMasterFactory.getSerialMaster(parameters));
    modBusRx.connect(100);

## TCP:

    ModBusRx modBusRx = new ModBusRx(ModBusMasterFactory.getTCPMaster("address", 502));
    modBusRx.connect(100);

### Create requests:
    // Write
    modBusRx.writeHoldingRegister(1, 100, 80).subscribe();
    
    // Read
    modBusRx.readHoldingRegisters(1, 0, 10)
        .subscribe(integers -> {
             for (Integer integer : integers)
                   System.out.println(integer);
             }
        });
        
# Supported functions:
* `Completable writeHoldingRegister(final int deviceId, final int address, final int data);`

* `Completable writeHoldingRegisters(final int deviceId, final int address, final int[] data);`

* `Completable writeCoil(final int deviceId, final int address, final boolean coil);`

* `Completable writeCoils(final int deviceId, final int address, final boolean[] coils);`

* `Observable<Integer[]> readInputRegisters(int deviceId, int address, int length);`

* `Observable<Integer[]> readHoldingRegisters(int deviceId, int address, int length);`

* `Observable<Boolean[]> readDiscreteInputRegisters(int deviceId, int address, int length);`

* `Observable<Boolean[]> readCoils(int deviceId, int address, int length);`

