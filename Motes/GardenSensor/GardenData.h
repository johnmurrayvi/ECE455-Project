#ifndef GARDENDATA_H
#define GARDENDATA_H

enum {
  /* Number of readings per message. If you increase this, you may have to
     increase the message_t size. */
  NREADINGS = 10,

  /* Default sampling period. */
  /* 1000 msec/sec * 60 sec/min * 6 min = 360000 msec/samp */
  DEFAULT_INTERVAL = 360000, // 6 minutes

  AM_GDATA = 0x99
};

typedef nx_struct gdata {
  nx_uint16_t header; /* to visually read serial packet */
  nx_uint16_t numsamp; /* number of readings. */
  nx_uint16_t lightData[NREADINGS];
  nx_uint16_t tempData[NREADINGS];
  nx_uint16_t humdData[NREADINGS];
} gdata_t;

#endif
