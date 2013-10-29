package com.jjmvi.smartgarden;

import java.util.Locale;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

public class ItemFragment extends SherlockFragment
{
	public static final String ARG_ITEM_NUMBER = "item_number";

	public ItemFragment()
	{
		// Empty constructor required for fragment subclasses
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
	  View rootView = inflater.inflate(R.layout.fragment_item, container, false);
		int i = getArguments().getInt(ARG_ITEM_NUMBER);
		String item = getResources().getStringArray(R.array.nav_list_titles_array)[i];

		int imageId = getResources().getIdentifier(item.toLowerCase(Locale.getDefault()), "drawable", getActivity().getPackageName());
    ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
		getActivity().setTitle(item);
		return rootView;
	}
}