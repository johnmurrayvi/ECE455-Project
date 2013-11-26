#include "parse_to_xml.h"

#include <algorithm>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <list>
#include <set>
#include <map>
#include <cstdio>


void Reading::printReading()
{
  printf("\t\t<reading>\n");
  printf("\t\t\t<value>%d</value>\n", value);
  printf("\t\t\t<date>%s</date>\n", date.c_str());
  printf("\t\t\t<time>%s</time>\n", time.c_str());
  printf("\t\t</reading>\n");
}


void MoteSensor::printSensorData()
{
  int i;

  printf("\t<sensor label=\"%s\">\n", sensor.c_str());
  for (i = 0; i < (int)readings.size(); i++)
    readings[i].printReading();
  printf("\t</sensor>\n");
}


// NOTE: THIS IS CURRENTLY OFFSET BY 6 MINUTES
// I'm just reading the first value in the array,
// but the time stamp is when the packet is received (~6 mins later)
void DataParser::readFile(std::string file)
{
  std::ifstream f;
  std::string line;
  std::string curRDate, curRTime;
  std::stringstream ss;

  light = new MoteSensor("Light");
  temperature = new MoteSensor("Temperature");
  humidity = new MoteSensor("Humidity");

  Reading *areading;

  f.open(file.c_str());

  int lineNum = 0;

  while (getline(f, line)) {
    ss.clear();
    ss.str("");
    ss.str(line);

    switch (lineNum % 5) {
      case 0:
        // DATE + TIME

        // scope for vars
        {
          std::string dow, month, day, time, year;

          ss >> dow >> month >> day >> time >> year;

          // Need to adjust this if to get data other than Nov
          std::string rdate;
          if (month == "Nov")
            rdate = "11/";
          rdate += day + "/" + year;

          curRDate = rdate;
          curRTime = time;
        }

        break;

      case 1:
        // Number of samples
        // For right now, with packets every 6 mins, just read the first
        // value of each sensor, then we have 10 per hour anyways
        break;

      case 2:
        // Light data
        areading = new Reading;
        areading->date = curRDate;
        areading->time = curRTime;
        ss >> areading->value;
        light->readings.push_back(*areading);
        break;

      case 3:
        // Temperature data
        areading = new Reading;
        areading->date = curRDate;
        areading->time = curRTime;
        ss >> areading->value;
        temperature->readings.push_back(*areading);
        break;

      case 4:
        // Humidity data
        areading = new Reading;
        areading->date = curRDate;
        areading->time = curRTime;
        ss >> areading->value;
        humidity->readings.push_back(*areading);
        break;
    }

    lineNum++;
  }

  f.close();
}


void DataParser::printXMLHeader()
{
  printf("<?xml version=\"1.0\"?>\n");
  printf("<MoteData>\n");
}


void DataParser::printXMLFooter()
{
  printf("</MoteData>\n");
}


void DataParser::printXML()
{
  printXMLHeader();
  humidity->printSensorData();
  light->printSensorData();
  temperature->printSensorData();
  printXMLFooter();
}


int main(int argc, char **argv)
{
  if (argc != 2) {
    std::cout << "./parse_to_xml filename\n";
    exit(1);
  }

  DataParser p;

  p.readFile(argv[1]);

  p.printXML();

  return 0;
}
