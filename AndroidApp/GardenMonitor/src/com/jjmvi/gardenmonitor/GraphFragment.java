package com.jjmvi.gardenmonitor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.xmlpull.v1.XmlPullParserException;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
//import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

import com.jjmvi.gardenmonitor.MoteDataXMLParser.Sensor;
import com.jjmvi.gardenmonitor.MoteDataXMLParser.Reading;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


public class GraphFragment extends Fragment
{
//  public static Map<String, ArrayList<Integer>> data = null;

  private String[] tabColors = { "#378FFD", "#FF8700", "#5FD7FE", "#D50929", "#378F2A" };

  private static final String ARG_POSITION = "position";
  private int position;

  public static GraphFragment newInstance(int position)
  {
    GraphFragment fragment = new GraphFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_POSITION, position);
    fragment.setArguments(args);
    return fragment;
  }

  public GraphFragment()
  {
    // Required empty public constructor
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      position = getArguments().getInt(ARG_POSITION);
    }
  }

  // DATA STUFF

  public class MyPair
  {
    private final Integer key;
    private final Integer value;

    public MyPair(Integer aKey, Integer aValue)
    {
      key   = aKey;
      value = aValue;
    }

    public Integer first()   { return key; }
    public Integer second() { return value; }
  }


  // Plot Stuff
  private Boolean showOneDay = true;
//  private Boolean showOneDay = false;

  private Integer min = null;
  private Integer max = null;
  private ArrayList<Integer> dataToPlot = null;

  private ArrayList<Integer> moistureData = null;
  private ArrayList<Integer> lightData = null;
  private ArrayList<Integer> temperatureData = null;
  private ArrayList<Integer> humidityData = null;

  private static ArrayList<Integer> readRawTextFile(Context ctx, int resId)
  {
    ArrayList<Integer> contents = new ArrayList<Integer>();
    InputStream inputStream = ctx.getResources().openRawResource(resId);

    InputStreamReader inputreader = new InputStreamReader(inputStream);
    BufferedReader buffreader = new BufferedReader(inputreader);
    String line;

    try {
      while ((line = buffreader.readLine()) != null) {
        contents.add(Integer.parseInt(line));
      }
    } catch (IOException e) {
        return null;
    }
    return new ArrayList<Integer>(contents);
  }

  private static ArrayList<Integer> readStoredXML(Context ctx, String sensorLabel) throws XmlPullParserException, IOException
  {
    ArrayList<Integer> values = new ArrayList<Integer>();
    List<Sensor> sensors = null;
    MoteDataXMLParser moteDataXMLParser = new MoteDataXMLParser();
    InputStream is = ctx.getResources().openRawResource(R.xml.motedata);

    sensors = moteDataXMLParser.parse(is);
    for (Sensor sensor : sensors) {
      // Find sensor we want
      // TODO: Should probably just read in all of the data...
      if (sensor.label.equals(sensorLabel)) {
        for (Reading reading : sensor.readings) {
          values.add(reading.value);
        }
      }
    }

    return new ArrayList<Integer>(values);
  }

  private void readData()
  {    
/*
    if (moistureData == null)
      moistureData = readRawTextFile(getActivity(), R.raw.moisture_values);
    try {
      temperatureData = readStoredXML(getActivity(), "Temperature");
      lightData = readStoredXML(getActivity(), "Light");
      humidityData = readStoredXML(getActivity(), "Humidity");
    } catch (XmlPullParserException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } */
    if (moistureData == null)
      moistureData = readRawTextFile(getActivity(), R.raw.moisture_values);
    if (temperatureData == null)
      temperatureData = readRawTextFile(getActivity(), R.raw.temperature_values);
    if (lightData == null)
      lightData = readRawTextFile(getActivity(), R.raw.light_values);
    if (humidityData == null)
      humidityData = readRawTextFile(getActivity(), R.raw.humidity_values);
  }


  private void getDataToPlot()
  {
    switch (position) {
      case 0: // Humidity
        dataToPlot = humidityData;
        min = Collections.min(humidityData);
        max = Collections.max(humidityData);
        break;

      case 1: // Light
        dataToPlot = lightData;
        min = Collections.min(lightData);
        max = Collections.max(lightData);        
        break;

      case 2: // Moisture
        dataToPlot = moistureData;
        min = Collections.min(moistureData);
        max = Collections.max(moistureData);
        break;

      case 3: // Temperature
        dataToPlot = temperatureData;
        min = Collections.min(temperatureData);
        max = Collections.max(temperatureData);
        break;
    }
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    FrameLayout fl = new FrameLayout(getActivity());
    fl.setLayoutParams(params);
    fl.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    final View v = inflater.inflate(R.layout.fragment_graph, container, false);
    v.setBackgroundColor(getResources().getColor(android.R.color.transparent));

    Line l = new Line();
    LinePoint p;

    readData();
    getDataToPlot();

    if (showOneDay == true && dataToPlot.size() > 240) {
      Integer x = 0;
      Integer i = 0;
      Integer len = 240;

      for (i = dataToPlot.size() - len; i < dataToPlot.size(); i++) {
        p = new LinePoint(x, dataToPlot.get(i));
        l.addPoint(p);
        x++;
      }
    } else {
      for (Integer x = 0; x < dataToPlot.size(); x++) {
        p = new LinePoint(x, dataToPlot.get(x));
        l.addPoint(p);
      }
    }

    // Don't show points
    l.setShowingPoints(false);

    // Set line color
    l.setColor(Color.parseColor(tabColors[position]));

    LineGraph lG = (LineGraph) v.findViewById(R.id.linegraph);
    lG.addLine(l);

    switch (position) {
      case 0:
        lG.setRangeY(900, 1100);
        break;
      case 1:
        lG.setRangeY(0, 255);
        break;
      case 2:
        lG.setRangeY(0, 255);
        break;
      case 3:
        lG.setRangeY(5000, 7000);
        break;
    }
    lG.setLineToFill(0);
    lG.setFillColor(Color.parseColor(tabColors[position]));
    lG.setFillAlpha(100);
    lG.setFillStrokeWidth(3);
//    lG.showMinAndMaxValues(true);

//    lG.setOnPointClickedListener(new OnPointClickedListener()
//    {
//      @Override
//      public void onClick(int lineIndex, int pointIndex)
//      {
//        // TODO Auto-generated method stub
//        LinePoint p = lG.getLine(lineIndex).getPoint(pointIndex);
//        float x = p.getX();
//        float y = p.getY();
//      }
//    });

    fl.addView(v);

    return fl;
  }

}
