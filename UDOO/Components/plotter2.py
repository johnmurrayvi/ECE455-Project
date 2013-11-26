import numpy
import matplotlib.pyplot as plt
import os

files = ['./data_old_sensor' ,\
         './data_new_sensor', \
         './data_after_watering']

for file_name in files:
  f = open (file_name + '.txt', 'r')
  line = f.readline ()

  n = 0
  xData = []
  yData = []

  while len (line) > 5:
    no_output = line[:-1].split ('output')
    better = no_output[0].split()

    if (better [0] != "event"):
      xData.append (n)
      yData.append (better [len (better) - 1])
      n = n + 1

    line = f.readline ()

  plt.plot (xData, yData)
  plt.title ('Moisture Sensor Output: ' + file_name)
  plt.ylim (400, 1025)
  plt.savefig (file_name + '_fig.png')
  os.system ('convert ' + file_name + '_fig.png ' + file_name + '_fig.jpg')
  #plt.show ()
  plt.clf ()
  f.close ()
