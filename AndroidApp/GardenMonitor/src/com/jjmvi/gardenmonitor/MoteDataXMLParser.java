package com.jjmvi.gardenmonitor;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MoteDataXMLParser
{
  private static final String ns = null;

  public List<Sensor> parse(InputStream in) throws XmlPullParserException, IOException
  {
    Log.v("XML", "In parse");
    try {
      XmlPullParser parser = Xml.newPullParser();
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
      parser.setInput(in, null);
      Log.v("XML", "In parse - set input");
//      parser.nextTag();
      Log.v("XML", "In parse - about to return");
      return readFeed(parser);
    } finally {
      in.close();
    }
  }


  private List<Sensor> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    List<Sensor> sensors = new ArrayList<Sensor>();

    parser.require(XmlPullParser.START_TAG, ns, "motedata");
    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.getEventType() != XmlPullParser.START_TAG) {
        continue;
      }
      String name = parser.getName();
      // Starts by looking for the entry tag
      if (name.equals("sensor")) {
        sensors.add(readSensor(parser));
      } else {
        skip(parser);
      }
    }
    return sensors;
  }


  public static class Sensor
  {
    public final String label;
    public List<Reading> readings;

    private Sensor(String label, List<Reading> readings)
    {
      this.label = label;
      this.readings = readings;
    }
  }


  public static class Reading
  {
    public final int value;
    public final String date;
    public final String time;

    private Reading(int value, String date, String time)
    {
      this.value = value;
      this.date = date;
      this.time = time;
    }
  }


  private Sensor readSensor(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    parser.require(XmlPullParser.START_TAG, ns, "sensor");
    String label = null;
    List<Reading> readings = new ArrayList<MoteDataXMLParser.Reading>();

    label = parser.getAttributeValue(0);

    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.getEventType() != XmlPullParser.START_TAG) {
        continue;
      }

      String name = parser.getName();
      if (name.equals("reading")) {
        readings.add(readReading(parser));
      } else {
        skip(parser);
      }
    }
    return new Sensor(label, readings);
  }

  private Reading readReading(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    parser.require(XmlPullParser.START_TAG, ns, "reading");
    int value = 0;
    String date = null;
    String time = null;

    while (parser.next() != XmlPullParser.END_TAG) {
      if (parser.getEventType() != XmlPullParser.START_TAG) {
        continue;
      }
      String name = parser.getName();
      if (name.equals("value")) {
        value = readValue(parser);
      } else if (name.equals("date")) {
        date = readDate(parser);
      } else if (name.equals("time")) {
        time = readTime(parser);
      } else {
        skip(parser);
      }
    }
    
    return new Reading(value, date, time);
  }


  private int readValue(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    parser.require(XmlPullParser.START_TAG, ns, "value");
    int value = readInt(parser);
    parser.require(XmlPullParser.END_TAG, ns, "value");
    return value;
  }


  private String readDate(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    parser.require(XmlPullParser.START_TAG, ns, "date");
    String date = readText(parser);
    parser.require(XmlPullParser.END_TAG, ns, "date");
    return date;
  }


  private String readTime(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    parser.require(XmlPullParser.START_TAG, ns, "time");
    String time = readText(parser);
    parser.require(XmlPullParser.END_TAG, ns, "time");
    return time;
  }


  private int readInt(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    int result = 0;
    if (parser.next() == XmlPullParser.TEXT) {
      result = Integer.parseInt(parser.getText());
      parser.nextTag();
    }
    return result;
  }


  private String readText(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    String result = "";
    if (parser.next() == XmlPullParser.TEXT) {
      result = parser.getText();
      parser.nextTag();
    }
    return result;
  }
  

  private void skip(XmlPullParser parser) throws XmlPullParserException, IOException
  {
    if (parser.getEventType() != XmlPullParser.START_TAG) {
      throw new IllegalStateException();
    }
    int depth = 1;
    while (depth != 0) {
      switch (parser.next()) {
      case XmlPullParser.END_TAG:
        depth--;
        break;
      case XmlPullParser.START_TAG:
        depth++;
        break;
      }
    }
  }

}
