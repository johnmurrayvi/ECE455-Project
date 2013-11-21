import os

f = open ('./tmp_data.txt', 'r')
line = f.readline ()

while len (line) > 10:
  print line [:-1]
  better_line = line.split ('output')
  print better_line[0][:-1]
  just_words = better_line[0][:-1].split ()
  print just_words
  line = f.readline ()

f.close ()
