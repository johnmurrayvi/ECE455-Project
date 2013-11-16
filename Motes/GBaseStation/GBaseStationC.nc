configuration GBaseStationC {}

implementation
{
  components MainC, GBaseStationP, LedsC;
  components ActiveMessageC as Radio, SerialActiveMessageC as Serial;
  
  MainC.Boot <- GBaseStationP;

  GBaseStationP.RadioControl -> Radio;
  GBaseStationP.SerialControl -> Serial;
  
  GBaseStationP.UartSend -> Serial;
  GBaseStationP.UartReceive -> Serial.Receive;
  GBaseStationP.UartPacket -> Serial;
  GBaseStationP.UartAMPacket -> Serial;
  
  GBaseStationP.RadioSend -> Radio;
  GBaseStationP.RadioReceive -> Radio.Receive;
  GBaseStationP.RadioSnoop -> Radio.Snoop;
  GBaseStationP.RadioPacket -> Radio;
  GBaseStationP.RadioAMPacket -> Radio;
  
  GBaseStationP.Leds -> LedsC;
}
