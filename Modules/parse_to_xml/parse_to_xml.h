#ifndef _PARSE_TO_XML_H_
#define _PARSE_TO_XML_H_

#include <iostream>
#include <vector>
#include <string>

class Reading
{
  public:
//    Reading(int v, std::string d, std::string t) { value = v; date = d; time = t; };

    void printReading();
    int value;
    std::string date;
    std::string time;
};

class MoteSensor
{
    public:
        MoteSensor();
        MoteSensor(std::string name) { sensor = name; };

        void printSensorData();

//        int numSamp;
        std::string sensor;
        std::vector<Reading> readings;
};

class DataParser
{
    public:
        void readFile(std::string file);

        void printXMLHeader();
        void printXMLFooter();
        void printXML();

        MoteSensor *light;
        MoteSensor *temperature;
        MoteSensor *humidity;
};

#endif // _PARSE_TO_XML_H_
