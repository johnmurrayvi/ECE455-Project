import serial, time
import struct

port = serial.Serial (port='/dev/ttyUSB0', baudrate=115200, timeout=0.2, bytesize=8, parity='N', stopbits=1, xonxoff=0, rtscts=0)
port.flushInput ()

#packet = struct.Struct ('

while 1:
  bytes_read = port.read (100)
  #print len (bytes_read)
  if len (bytes_read) == 77:
    for i in range (0, len(bytes_read)):
      print i
      print hex(int(ord(bytes_read [i])))
 

    #num_of_readings = (int (ord (bytes_read [10])) << 8) + int (ord (bytes_read [11]))
    #print num_of_readings
    #print hex(int(ord(bytes_read [10])))
    #print hex(int(ord(bytes_read [11])))

    #num_of_readings = (int (ord (bytes_read [11])) << 8) + int (ord (bytes_read [12]))
    #print num_of_readings

    #light_vals = []
    #for i in range (16, 35, 2):
      #light_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    #print light_vals

    #temp_vals = []
    #for i in range (36, 54, 2):
      #temp_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    #print temp_vals

    #hum_vals = []
    #for i in range (55, 76, 2):
      #hum_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    #print hum_vals
    #values = struct.unpack ('H', bytes_read [0:1])

  time.sleep (.2)
  bytes_read = ''

f.close ()
