import re
import os
from operator import itemgetter
from sets import Set


rhythmFilename = "rhythms.txt"

# Figure out which columns to select
rhythmFile = open(rhythmFilename, 'r')
rhythms = []
for line in rhythmFile:
    rhythm = []
    if re.search(r'^#', line) is None:
        rhythm = line.split(" ")
        rhythm[len(rhythm)-1] = rhythm[len(rhythm)-1].strip()
        if rhythm[len(rhythm)-1] == "":
            rhythm[len(rhythm)-1] = "REMOVE"
            rhythm.remove("REMOVE")
    rhythms.append(rhythm)
        
rhythmFile.close()

# print rhythms

arffFile = open("rhythm.arff", 'w')

# print header information
arffFile.write("@relation rhythm\n")
arffFile.write("\n")

arffFile.write("@attribute numBeats NUMERIC\n")
arffFile.write("@attribute numConsecutiveBeats NUMERIC\n")
# arffFile.write("@attribute b1 {0,1}\n")
# arffFile.write("@attribute b2 {0,1}\n")
# arffFile.write("@attribute b3 {0,1}\n")
# arffFile.write("@attribute b4 {0,1}\n")
# arffFile.write("@attribute b5 {0,1}\n")
# arffFile.write("@attribute b6 {0,1}\n")
# arffFile.write("@attribute b7 {0,1}\n")
# arffFile.write("@attribute b8 {0,1}\n")
# arffFile.write("@attribute b9 {0,1}\n")
# arffFile.write("@attribute b10 {0,1}\n")
# arffFile.write("@attribute b11 {0,1}\n")
# arffFile.write("@attribute b12 {0,1}\n")
# arffFile.write("@attribute b13 {0,1}\n")
# arffFile.write("@attribute b14 {0,1}\n")
# arffFile.write("@attribute b15 {0,1}\n")
# arffFile.write("@attribute b16 {0,1}\n")
# arffFile.write("@attribute class {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9}\n")
arffFile.write("@attribute class {-1.0, -0.5, 0.0, 0.5, 1.0}\n")
# arffFile.write("@attribute class NUMERIC\n")


# {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9}
#  --------  --------  --------  --------  --------
arffFile.write("\n")
arffFile.write("@data\n")

# print data
for rhythm in rhythms:
    # writestring = ",".join(rhythm[:]) + "\n"
    beatcount = 0
    for beat in rhythm:
        if (beat == "1"):
            beatcount += 1

    consecutiveBeats = 0
    previousBeat = 0
    for beat in rhythm:
        if (beat == "1"):
            if (previousBeat == 1):
                consecutiveBeats += 1
            previousBeat = 1
        else:
            previousBeat = 0
    writestring = str(beatcount) + "," + str(consecutiveBeats) + "," + rhythm[len(rhythm)-1] + "\n"
    arffFile.write(writestring)
