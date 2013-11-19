"""
  serial_reader.py

  This script will read from the default UART port 
  where the Ardiuno is attached to the UDOO's iMX processor

  Initial version: 11/7/13
  Author: Jamie Finney
"""
import serial, time

# Setup the serial port
ser = serial.Serial ('/dev/ttymxc3', 9600, timeout=1)

print 'Begin reading from serial port /dev/ttymxc3...'

# Loop until user presses CTRL-c
while 1:
  try:
    line = ser.readline ()
    
    # Check for empty lines
    if (len (line) > 5):
      print time.asctime () + ':' + line [:-1]

      # Output to a specific file
      f = open ('/home/ubuntu/project/tmp2.txt', 'a')
      f.write(time.asctime () + ':' + line)
      f.close()

  # Exit condition
  except KeyboardInterrupt:
    print 'Ending serial reading...'
    exit ()
