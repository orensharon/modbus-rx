# Overview
This project is [j2mod](https://github.com/steveohara/j2mod/) library wrapper for making  ModBus master requests with rxjava2.

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
        .subscribe(values -> System.out.println(Arrays.toString(values));

And more complex:

     Observable<Integer[]> read = modBusRx.readHoldingRegisters(1, 10, 1);
        Observable.concat(
                modBusRx.writeHoldingRegister(1, 106,10).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 20).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 30).andThen(read),
                modBusRx.writeHoldingRegister(1, 106, 40).andThen(read)
        ).subscribe(values -> System.out.println(Arrays.toString(values)));


# Supported functions:
* `Completable writeHoldingRegister(int deviceId, int address, int data);`

* `Completable writeHoldingRegisters(int deviceId, int address, int[] data);`

* `Completable writeCoil(int deviceId, int address, boolean coil);`

* `Completable writeCoils(int deviceId, int address, boolean[] coils);`

* `Observable<Integer[]> readInputRegisters(int deviceId, int address, int length);`

* `Observable<Integer[]> readHoldingRegisters(int deviceId, int address, int length);`

* `Observable<Boolean[]> readDiscreteInputRegisters(int deviceId, int address, int length);`

* `Observable<Boolean[]> readCoils(int deviceId, int address, int length);`

