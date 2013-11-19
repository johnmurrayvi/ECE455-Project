import serial, time

ser = serial.Serial ('/dev/ttyUSB0', 9600, timeout=1)
print 'Beginning serial test...'
#f = open ('/home/ubuntu/project/tmp2.txt', 'w')
while 1:
  try:
    line = ser.readline ()
    if (len (line) > 5):
#      words = line.split (" ")
#  line [27] = 'X'
      print time.asctime () + ':' + line [:-1]
#      f = open ('/home/ubuntu/project/tmp2.txt', 'a')
#      f.write(time.asctime () + ':' + line)
#      f.close()
  #print words
  #line [27] = 'X'
  except KeyboardInterrupt:
    print 'Ending serial test...'
    #f.close()
    exit ()
