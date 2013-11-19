import web
import os
import numpy, matplotlib.pyplot as plt

def get_page():
  f = open ('./static/data1.txt', 'r')
  line = f.readline ()
  output = ""
  while (len (line) > 0):
    output += (line + "<br>")
    line = f.readline ()
  f.close ()
  return output

def get_log ():
  #os.system ('cp ../tmp2.txt ./static/data.txt')
  f = open ('./static/data.txt', 'r')
  old_date = ''
  date = ''
  last_line = ''
  line = f.readline ()
  output = ""
  n = 0
  xData = []
  yData = []
  while (len (line) > 20):
    words = line.split (' ')
    old_date = date
    date = words [0] + ' ' + words [1] + ' ' + words [2]

    if type (words [len (words) - 1]) == int:
      xData.append (n)
      yData.append (int (words [len (words) - 1]))
      n = n + 1

    if (old_date != date):
      plt.plot (xData, yData)
      plt.title (old_date)
      image_name = old_date + "_fig.png"
      plt.savefig (image_name)
      image_str = '<img src="./' + image_name + '" />'
      output += '<br>' + image_str + '<br>'
      xData = []
      yData = []
      n = 0

    s = line.split ('output')
    output += (date + ": " + (s [0] + "<br>"))

    last_line = line
    line = f.readline ()
  f.close ()
  return output
  
