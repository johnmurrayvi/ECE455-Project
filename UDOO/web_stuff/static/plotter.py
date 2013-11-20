import numpy as np
import matplotlib
import matplotlib.pyplot as plt

f = open ('./tmp_data.txt', 'r')
old_date = ''
date = ''
last_line = ''
line = f.readline ()
output = ""
n = 0
xData = []
yData = []
while (len (line) > 10):
  #print 'Processing: ' + line
  words = line.split (' ')
  date = words [0] + '_' + words [1] + '_' + words [2]
  #if words [0] == "event":
  #  print 'Hit Event!!!!!'

  if words [0] != "event":
    s = line.split ('output')
    t = s [0]
    words = t.split (' ')
    #print words
    #print int (words [len (words) - 2])
    #print 'Processing: ' + line
    y = int (words [len (words) - 2])
    if type (y) == int:
      xData.append (n)
      yData.append (y)
      n = n + 1

    if (old_date != date and n > 5):
      plt.ylim ([0, 1023])
      plt.clf ()
      plt.plot (xData, yData)
      plt.title (old_date)
      #plt.show ()
      image_name = old_date + "_fig.png"
      plt.savefig (image_name)
      image_str = '<img src="./' + image_name + '" />'
      output = '<br>' + image_str + '<br>'
      print output
      xData = []
      yData = []
      n = 0

    old_date = date
  line = f.readline ()

plt.clf ()
plt.plot (xData, yData)
plt.title (old_date)
plt.ylim ([0, 1023])
image_name = old_date + "_fig.png"
plt.savefig (image_name)
image_str = '<img src="./' + image_name + '" />'
output = '<br>' + image_str + '<br>'
print output
