package com.jjmvi.gardenmonitor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

import android.R.integer;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class GraphFragment extends Fragment
{
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

  
  private Integer min = null;
  private Integer max = null;
  private ArrayList<Integer> dataToPlot = null;

  private ArrayList<Integer> moistureData = null;
//  private Integer moistureMin = null;
//  private Integer moistureMax = null;

  private static ArrayList<Integer> readRawTextFile(Context ctx, int resId)
  {
    ArrayList<Integer> contents = new ArrayList<Integer>();
    InputStream inputStream = ctx.getResources().openRawResource(resId);

    InputStreamReader inputreader = new InputStreamReader(inputStream);
    BufferedReader buffreader = new BufferedReader(inputreader);
    String line;
    StringBuilder text = new StringBuilder();

    try {
      while ((line = buffreader.readLine()) != null) {
        contents.add(Integer.parseInt(line));
      }
    } catch (IOException e) {
        return null;
    }
    return contents;
  }

  private void readData()
  {
    switch (position) {
      case 0: // Humidity
          


      case 1: // Light
        


      case 2: // Moisture
//        if (moistureData == null)
          moistureData = readRawTextFile(getActivity(), R.raw.moisture_values);
        min = Collections.min(moistureData);
        max = Collections.max(moistureData);
        dataToPlot = moistureData;
        break;

      case 3: // Temperature


      default:
        if (moistureData == null)
          moistureData = readRawTextFile(getActivity(), R.raw.moisture_values);
        min = Collections.min(moistureData);
        max = Collections.max(moistureData);
        dataToPlot = moistureData;
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
    Integer x = 0;
    for (Integer y = 0; y < dataToPlot.size(); y++) {
      p = new LinePoint(x, y);
      l.addPoint(p);
      x += 6;      
    }

    // Don't show points
    l.setShowingPoints(false);

    // Set line color
    l.setColor(Color.parseColor(tabColors[position]));

    final LineGraph lG = (LineGraph) v.findViewById(R.id.linegraph);
    lG.addLine(l);
//    lG.setRangeY(0, 255);
    lG.setLineToFill(0);
    lG.setFillColor(Color.parseColor(tabColors[position]));
    lG.setFillAlpha(100);
    lG.setFillStrokeWidth(3);
    lG.showMinAndMaxValues(true);

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
