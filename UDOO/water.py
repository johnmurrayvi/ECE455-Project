"""
  water.py

  This activates the solenoid controlled
  water valve attached to GPIO pin 21

  Initial version: 11/18/13
  Author: Jamie Finney
"""

import time

# Setup GPIO pin #21
f = open ('/sys/class/gpio/gpio21/direction', 'w')
f.write ('out')
f.close ()

device = '/sys/class/gpio/gpio21/value'

# Activate solenoid
def begin_watering (gpio_pin):
  f = open (gpio_pin, 'w')
  f.write ('1')
  f.close()

# Deactivate solenoid
def stop_watering (gpio_pin):
  f = open (gpio_pin, 'w')
  f.write ('0')
  f.close()

begin_watering (device)
# Wait
time.sleep (25)
stop_watering (device)

