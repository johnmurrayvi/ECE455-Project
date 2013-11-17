configuration GardenSensorAppC {}

implementation
{
  components GardenSensorC, MainC, ActiveMessageC, LedsC,
    new TimerMilliC(), new TimerMilliC() as OneShot,
    new HamamatsuS1087ParC() as LightSensor,
    new SensirionSht11C() as TempSensor,
    new SensirionSht11C() as HumdSensor,
    new AMSenderC(AM_GDATA), 
    new AMReceiverC(AM_GDATA);

  GardenSensorC.Boot -> MainC;
  GardenSensorC.RadioControl -> ActiveMessageC;
  GardenSensorC.AMSend -> AMSenderC;
  GardenSensorC.Receive -> AMReceiverC;
  GardenSensorC.Timer -> TimerMilliC;
  GardenSensorC.oneShot -> OneShot;
  GardenSensorC.lightSensor -> LightSensor;
  GardenSensorC.tempSensor -> TempSensor.Temperature;
  GardenSensorC.humdSensor -> HumdSensor.Humidity;
  GardenSensorC.Leds -> LedsC;  
}
