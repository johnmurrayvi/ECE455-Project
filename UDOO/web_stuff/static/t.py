fin = open ('data.txt', 'r')
fout = open ('data1.txt', 'w')
line = fin.readline ()
while len (line) > 0:
  words = line.split()
  fout.write (words [0] + '\n')
  line = fin.readline ()
fin.close()
fout.close()  
