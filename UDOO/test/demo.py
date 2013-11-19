import os
import time

f = open ('/sys/class/gpio/gpio3/direction', 'w')
f.write ('in')
f.close ()
f = open ('/sys/class/gpio/gpio9/direction', 'w')
f.write ('out')
f.close ()

input_pin = '/sys/class/gpio/gpio3/value'
output_pin = '/sys/class/gpio/gpio9/value'
try:
  while (1):
    #f = open (path, 'r')
    os.system ('cat ' + input_pin + ' > /home/ubuntu/project/tmp.txt')
    #print i
    f = open ('/home/ubuntu/project/tmp.txt', 'r')
    s = f.readline ()
    if (s[0] == '1'):
      print 'Too dry - adding water at ' + time.asctime()
      os.system ('echo 1 > ' + output_pin)
      time.sleep (30.)
      os.system ('echo 0 > ' + output_pin)
    time.sleep (0.5)

except KeyboardInterrupt:
  print 'Closing demo application'
  exit ()
