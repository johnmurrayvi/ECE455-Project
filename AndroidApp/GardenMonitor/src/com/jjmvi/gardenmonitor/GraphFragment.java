package com.jjmvi.gardenmonitor;

import com.echo.holographlibrary.Line;
import com.echo.holographlibrary.LineGraph;
import com.echo.holographlibrary.LineGraph.OnPointClickedListener;
import com.echo.holographlibrary.LinePoint;

import android.app.ActionBar.LayoutParams;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    LinePoint p = new LinePoint(0, 5);
    p.setX(0);
    p.setY(5);
    l.addPoint(p);
    p = new LinePoint(8, 8);
    p.setX(8);
    p.setY(8);
    l.addPoint(p);
    p = new LinePoint(10, 4);
    p.setX(10);
    p.setY(4);
    l.addPoint(p);

    // Set line color
    l.setColor(Color.parseColor(tabColors[position]));
        
    final LineGraph lG = (LineGraph) v.findViewById(R.id.linegraph);
    lG.addLine(l);
    lG.setRangeY(0, 10);
    lG.setLineToFill(0);
    lG.setFillColor(Color.parseColor(tabColors[position]));
    lG.setFillAlpha(100);
    lG.setFillStrokeWidth(3);

    lG.setOnPointClickedListener(new OnPointClickedListener()
    {
      @Override
      public void onClick(int lineIndex, int pointIndex)
      {
        // TODO Auto-generated method stub
        LinePoint p = lG.getLine(lineIndex).getPoint(pointIndex);
        float x = p.getX();
        float y = p.getY();
      }
    });

    fl.addView(v);

    return fl;
  }

}
