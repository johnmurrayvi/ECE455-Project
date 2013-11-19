import serial

f = open ('/sys/class/gpio/gpio158/direction', 'w')
f.write ('in')
f.close ()

##f = open ('/sys/class/gpio/gpio158/value', 'r')
##while (1):
##  print f.readline ()
##f.close ()

device = '/sys/class/gpio/gpio158/value'
baud = 9600

ser = serial.Serial (device, baud)

while 1:
  ser.readline ()
