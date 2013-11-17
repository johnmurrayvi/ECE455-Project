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

  /* Current local state - interval, version and accumulated readings */
  gdata_t local;

  uint8_t reading; /* 0 to NREADINGS */

  /* When we head an Oscilloscope message, we check it's sample count. If
     it's ahead of ours, we "jump" forwards (set our count to the received
     count). However, we must then suppress our next count increment. This
     is a very simple form of "time" synchronization (for an abstract
     notion of time). */
  bool suppressCountChange;

  // Use LEDs to report various status issues.
  void report_problem() { call Leds.led0Toggle(); }
  void report_sent() { call Leds.led1Toggle(); }
  void report_received() { call Leds.led2Toggle(); }

  event void Boot.booted()
  {
    local.interval = DEFAULT_INTERVAL;
    local.id = TOS_NODE_ID;
    if (call RadioControl.start() != SUCCESS)
      report_problem();
  }

  void startTimer()
  {
    call Timer.startPeriodic(local.interval);
    reading = 0;
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
    if (reading == NREADINGS) {
      if (!sendBusy && sizeof local <= call AMSend.maxPayloadLength()) {
        // Don't need to check for null because we've already checked length
        // above
        memcpy(call AMSend.getPayload(&sendBuf, sizeof(local)), &local, sizeof local);
        if (call AMSend.send(AM_BROADCAST_ADDR, &sendBuf, sizeof local) == SUCCESS)
          sendBusy = TRUE;
      }
      if (!sendBusy)
        report_problem();

      reading = 0;
    }

    if (call lightSensor.read() != SUCCESS)
      report_problem();

    if (call tempSensor.read() != SUCCESS)
      report_problem();

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
    if (reading < NREADINGS) 
      local.lightData[reading++] = data;
  }

  // Read of temperature sensor done
  event void tempSensor.readDone(error_t result, uint16_t data)
  {
    if (result != SUCCESS) {
      data = 0xffff;
      report_problem();
    }
    if (reading < NREADINGS) 
      local.tempData[reading++] = data;
  }

  // Read of humidity sensor done
  event void humdSensor.readDone(error_t result, uint16_t data)
  {
    if (result != SUCCESS) {
      data = 0xffff;
      report_problem();
    }
    if (reading < NREADINGS) 
      local.humdData[reading++] = data;
  }
}
