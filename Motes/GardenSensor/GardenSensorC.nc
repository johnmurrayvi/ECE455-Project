#include "Timer.h"
#include "GardenData.h"

module GardenSensorC @safe()
{
  uses {
    interface Boot;
    interface SplitControl as RadioControl;
    interface AMSend;
    interface Receive;
    interface Timer<TMilli>;
    interface Timer<TMilli> as oneShot;
    interface Read<uint16_t> as lightSensor;
    interface Read<uint16_t> as tempSensor;
    interface Read<uint16_t> as humdSensor;
    interface Leds;
  }
}

implementation
{
  message_t sendBuf;
  bool sendBusy;

  /* Current local state - interval and accumulated readings */
  gdata_t local;

  uint8_t Lreading; /* 0 to NREADINGS */
  uint8_t Treading; /* 0 to NREADINGS */
  uint8_t Hreading; /* 0 to NREADINGS */

  // Use LEDs to report various status issues.
  void report_problem()
  { 
    call Leds.led0Toggle();
    call oneShot.startOneShot(1000);
  }
  
  void report_sent()
  {
    call Leds.led1Toggle();
    call oneShot.startOneShot(1000);
  }
  
  void report_received()
  {
    call Leds.led2Toggle();
    call oneShot.startOneShot(1000);
  }

  // Turn off leds after 1 second to save battery
  event void oneShot.fired()
  {
    call Leds.set(0);
  }

  event void Boot.booted()
  {
    local.header = 0xFFFF;
    local.numsamp = NREADINGS;
    if (call RadioControl.start() != SUCCESS)
      report_problem();
  }

  void startTimer()
  {
    call Timer.startPeriodic(DEFAULT_INTERVAL);
    Lreading = 0;
    Treading = 0;
    Hreading = 0;
  }

  event void RadioControl.startDone(error_t error)
  {
    startTimer();
  }

  event void RadioControl.stopDone(error_t error)
  {
  }

  event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len)
  {
    gdata_t *rmsg = payload;

    report_received();

    return msg;
  }

  /* At each sample period:
     - if local sample buffer is full, send accumulated samples
     - read next sample
  */
  event void Timer.fired()
  {
    if (Lreading == NREADINGS && Treading == NREADINGS && Hreading == NREADINGS) {
      if (!sendBusy && sizeof local <= call AMSend.maxPayloadLength()) {
        // Don't need to check for null because we've already checked length
        // above
        memcpy(call AMSend.getPayload(&sendBuf, sizeof(local)), &local, sizeof local);
        if (call AMSend.send(AM_BROADCAST_ADDR, &sendBuf, sizeof local) == SUCCESS)
          sendBusy = TRUE;
      }
      if (!sendBusy)
        report_problem();

      Lreading = 0;
      Treading = 0;
      Hreading = 0;
    }

    if (Lreading < NREADINGS)
      if (call lightSensor.read() != SUCCESS)
        report_problem();

    if (Treading < NREADINGS)
      if (call tempSensor.read() != SUCCESS)
        report_problem();

    if (Hreading < NREADINGS)
      if (call humdSensor.read() != SUCCESS)
        report_problem();
  }

  // Radio message send done
  event void AMSend.sendDone(message_t* msg, error_t error)
  {
    if (error == SUCCESS)
      report_sent();
    else
      report_problem();

    sendBusy = FALSE;
  }

  // Read of light sensor done
  event void lightSensor.readDone(error_t result, uint16_t data)
  {
    if (result != SUCCESS) {
      data = 0xffff;
      report_problem();
    }
    if (Lreading < NREADINGS) 
      local.lightData[Lreading++] = data;
  }

  // Read of temperature sensor done
  event void tempSensor.readDone(error_t result, uint16_t data)
  {
    if (result != SUCCESS) {
      data = 0xffff;
      report_problem();
    }
    if (Treading < NREADINGS) 
      local.tempData[Treading++] = data;
  }

  // Read of humidity sensor done
  event void humdSensor.readDone(error_t result, uint16_t data)
  {
    if (result != SUCCESS) {
      data = 0xffff;
      report_problem();
    }
    if (Hreading < NREADINGS) 
      local.humdData[Hreading++] = data;
  }
}
