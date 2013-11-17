#ifndef GARDENDATA_H
#define GARDENDATA_H

enum {
  /* Number of readings per message. If you increase this, you may have to
     increase the message_t size. */
  NREADINGS = 10,

  /* Default sampling period. */
  DEFAULT_INTERVAL = 256,

  AM_GDATA = 0x99
};

typedef nx_struct gdata {
  nx_uint16_t version; /* Version of the interval. */
  nx_uint16_t interval; /* Samping period. */
  nx_uint16_t id; /* Mote id of sending mote. */
  nx_uint16_t count; /* The readings are samples count * NREADINGS onwards */
  nx_uint16_t lightData[NREADINGS];
  nx_uint16_t tempData[NREADINGS];
  nx_uint16_t humdData[NREADINGS];
} gdata_t;

#endif
