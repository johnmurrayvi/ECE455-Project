import serial, time
#import struct

port = serial.Serial (port='/dev/ttyUSB0', baudrate=115200, timeout=0.2, bytesize=8, parity='N', stopbits=1, xonxoff=0, rtscts=0)
port.flushInput ()

#packet = struct.Struct ('

while 1:
  bytes_read = port.read (100)
  #print len (bytes_read)
  if len (bytes_read) == 77:
    print time.asctime ()
    #for i in range (0, len(bytes_read)):
      #print i
      #print hex(int(ord(bytes_read [i])))
 

    #num_of_readings = (int (ord (bytes_read [10])) << 8) + int (ord (bytes_read [11]))
    #print num_of_readings
    #print hex(int(ord(bytes_read [10])))
    #print hex(int(ord(bytes_read [11])))

    num_of_readings = (int (ord (bytes_read [12])) << 8) + int (ord (bytes_read [13]))
    print num_of_readings

    light_vals = []
    for i in range (14, 33, 2):
      light_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    print light_vals

    temp_vals = []
    for i in range (34, 53, 2):
      temp_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    print temp_vals

    hum_vals = []
    for i in range (54, 73, 2):
      hum_vals.append ((int (ord (bytes_read [i])) << 8) + int (ord (bytes_read [i + 1])))
    print hum_vals

    fout = open ('./telosb_data.txt', 'a')
    fout.write (time.asctime ())
    fout.write (str(num_of_readings))
    fout.write (str(light_vals))
    fout.write (str(temp_vals))
    fout.write (str(hum_vals))
    fout.close ()

  time.sleep (.2)
  bytes_read = ''

f.close ()
