import matplotlib.pyplot as plt
import numpy as np
import os

f = open ('telosb_data.txt', 'r')
date_str = f.readline ()[:-1]

lightData = []
tempData = []
humData = []

n = 0

while len (date_str) > 0:

  num = int (f.readline ()[:-1])
  light_vals = [int (i) for i in f.readline ()[:-1].split (',')]
  temp_vals = [int (i) for i in f.readline ()[:-1].split (',')]
  hum_vals = [int (i) for i in f.readline ()[:-1].split (',')]

  #lightData.extend (light_vals)
  #tempData.extend (temp_vals)
  #humData.extend (hum_vals)
  lightData.append (light_vals[0])
  tempData.append (temp_vals[0])
  humData.append (hum_vals[0])

  n = n + 1
  
  date_str = f.readline ()[:-1]

#print len (lightData)
#print n

for i in range (0, len (tempData)):
  tempData[i] = tempData[i] - 6000
  humData[i] = humData[i] - 500
#plt.plot (np.arange (n * 10), lightData)
#plt.plot (np.arange (n * 10), tempData)
#plt.plot (np.arange (n * 10), humData)
plt.plot (np.arange (n), lightData)
plt.plot (np.arange (n), tempData)
plt.plot (np.arange (n), humData)

f.close ()

files = ['./soil_data_aligned']

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
  #plt.title ('Moisture Sensor Output: ' + file_name)
  #plt.ylim (400, 1025)
  #plt.savefig (file_name + '_fig.png')
  #os.system ('convert ' + file_name + '_fig.png ' + file_name + '_fig.jpg')
  #plt.show ()
  #plt.clf ()
  f.close ()

plt.ylim (0, 800)
plt.title ('All Sensor Output: scaled')
pic_name = 'completed_aligned_fig'
plt.savefig (pic_name + '.png')
os.system ('convert ' + pic_name + '.png ' + pic_name + '.jpg')
#plt.show ()
