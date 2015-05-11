package com.toddindustries.makeitrain;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Forecast.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Forecast#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Forecast extends Fragment {
	// TODO: Rename parameter arguments, choose names that match
	// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
	private static final String ARG_PARAM1 = "param1";
	private static final String ARG_PARAM2 = "param2";

	// TODO: Rename and change types of parameters
	private String mParam1;
	private String mParam2;

	private OnFragmentInteractionListener mListener;

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @param param1 Parameter 1.
	 * @param param2 Parameter 2.
	 * @return A new instance of fragment Forecast.
	 */
	// TODO: Rename and change types and number of parameters
	public static Forecast newInstance(String param1, String param2) {
		Forecast fragment = new Forecast();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public Forecast() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			mParam1 = getArguments().getString(ARG_PARAM1);
			mParam2 = getArguments().getString(ARG_PARAM2);
		}
	}

	//TableLayout dailyTable;
	//TableLayout weeklyTable;

	String[] hourlyForecastTime = {"","","","",""};
	String[] hourlyForecastTemp = {"","","","",""};
	String[] hourlyForecastCondition = {"","","","",""};

	String[] forecastDates = {"","","","","","",""};
	String[] forecastConditions = {"","","","","","",""};
	String[] forecastPrecip = {"","","","","","",""};
	String[] forecastHighTemp = {"","","","","","",""};
	String[] forecastLowTemp = {"","","","","","",""};


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View rootView = inflater.inflate(R.layout.fragment_forecast, container, false);


		MainWeather a = (MainWeather) getActivity();
		//dailyTable = (TableLayout)rootView.findViewById(R.id.dailyTable);
		//weeklyTable = (TableLayout)rootView.findViewById(R.id.weeklyTable);


		for(int i = 0; i <= 4; i++) {
			hourlyForecastTemp[i] = a.returnArrayData(i, "tempTime");
			hourlyForecastTime[i] = a.returnArrayData(i, "hourlyTime");
			hourlyForecastCondition[i] = a.returnArrayData(i, "hourlyCondition");
		}
		for(int i = 0; i <= 5; i++){
			forecastDates[i] = a.returnArrayData(i, "date");
			forecastConditions[i] = a.returnArrayData(i, "condition");
			forecastPrecip[i] = a.returnArrayData(i, "precip");
			forecastHighTemp[i] = a.returnArrayData(i, "highTemp");
			forecastLowTemp[i] = a.returnArrayData(i, "lowTemp");
		}

		TableLayout forecast = (TableLayout)rootView.findViewById(R.id.weeklyTable);  //sets the table used
		forecast.setStretchAllColumns(true);
		forecast.bringToFront();


		forecast.removeAllViews();




		for(int j = 0; j <6; j++) {

			TableRow tr = new TableRow(getActivity());   //starts new table row

			TextView c1 = new TextView(getActivity());   //creates a text view to hold array value
			c1.setShadowLayer(2, 2, 2, Color.BLACK);
			c1.setTextSize(16);
			c1.setTextColor(Color.WHITE);
			c1.setText(forecastDates[j]);                //sets the text from array Date depending on H value

			ImageView c2 = new ImageView(getActivity()); //sets an image view, creating a new column
			c2.setImageResource(R.drawable.ic_launcher);//sets the image from a drawable
			TextView c3 = new TextView(getActivity());      //new column
			c3.setShadowLayer(2, 2, 2, Color.BLACK);
			c3.setTextSize(16);
			c3.setTextColor(Color.WHITE);
			c3.setText(forecastPrecip[j] + "%");
			TextView c4 = new TextView(getActivity());       //new column
			c4.setShadowLayer(2, 2, 2, Color.BLACK);
			c4.setTextSize(16);
			c4.setTextColor(Color.WHITE);
			//"⇑" + high + "°" + "⇓"
			c4.setText("⇑ " + forecastHighTemp[j] + "°");
			TextView c5 = new TextView(getActivity());       //new column
			c5.setShadowLayer(2, 2, 2, Color.BLACK);
			c5.setTextSize(16);
			c5.setTextColor(Color.WHITE);
			c5.setText("⇓ " + forecastLowTemp[j] + "°");


			tr.addView(c1);
			tr.addView(c2);
			tr.addView(c3);
			tr.addView(c4);
			tr.addView(c5);
			forecast.addView(tr);

		}


		TableLayout daily = (TableLayout)rootView.findViewById(R.id.dailyTable);
		daily.setStretchAllColumns(true);
		daily.bringToFront();
		daily.removeAllViews();
		//  daily.setBackgroundDrawable(gd);
		for(int x = 0; x <1; x++){
			for(int y = 0; y <1; y++) {

				TableRow tr = new TableRow(getActivity());
				TextView c1 = new TextView(getActivity());
				c1.setShadowLayer(2, 2, 2, Color.BLACK);
				c1.setTextSize(16);
				c1.setTextColor(Color.WHITE);
				c1.setText(hourlyForecastTime[0]);
				c1.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c2 = new TextView(getActivity());
				c2.setShadowLayer(2, 2, 2, Color.BLACK);
				c2.setTextSize(16);
				c2.setTextColor(Color.WHITE);
				c2.setText(hourlyForecastTime[1]);
				c2.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c3 = new TextView(getActivity());
				c3.setShadowLayer(2, 2, 2, Color.BLACK);
				c3.setTextSize(16);
				c3.setTextColor(Color.WHITE);
				c3.setText(hourlyForecastTime[2]);
				c3.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c4 = new TextView(getActivity());
				c4.setShadowLayer(2, 2, 2, Color.BLACK);
				c4.setTextSize(16);
				c4.setTextColor(Color.WHITE);
				c4.setText(hourlyForecastTime[3]);
				c4.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c5 = new TextView(getActivity());
				c5.setShadowLayer(2, 2, 2, Color.BLACK);
				c5.setTextSize(16);
				c5.setTextColor(Color.WHITE);
				c5.setText(hourlyForecastTime[4]);
				c5.setGravity(Gravity.CENTER_HORIZONTAL);



				tr.addView(c1);
				tr.addView(c2);
				tr.addView(c3);
				tr.addView(c4);
				tr.addView(c5);
				daily.addView(tr);
			}
			for(int y = 0; y < 1; y++){

				TableRow tr = new TableRow(getActivity());
				ImageView c1 = new ImageView(getActivity());
				c1.setImageResource(R.drawable.ic_launcher);

				ImageView c2 = new ImageView(getActivity());
				c2.setImageResource(R.drawable.ic_launcher);

				ImageView c3 = new ImageView(getActivity());
				c3.setImageResource(R.drawable.ic_launcher);

				ImageView c4 = new ImageView(getActivity());
				c4.setImageResource(R.drawable.ic_launcher);

				ImageView c5 = new ImageView(getActivity());
				c5.setImageResource(R.drawable.ic_launcher);


				tr.addView(c1);
				tr.addView(c2);
				tr.addView(c3);
				tr.addView(c4);
				tr.addView(c5);
				daily.addView(tr);}

			for(int y = 0; y <1; y++) {

				TableRow tr = new TableRow(getActivity());
				TextView c1 = new TextView(getActivity());
				c1.setShadowLayer(2, 2, 2, Color.BLACK);
				c1.setTextSize(16);
				c1.setTextColor(Color.WHITE);
				c1.setText("2" + "°");
				c1.setGravity(Gravity.CENTER_HORIZONTAL);
				//hourlyForecastTime[0]
				TextView c2 = new TextView(getActivity());
				c2.setShadowLayer(2, 2, 2, Color.BLACK);
				c2.setTextSize(16);
				c2.setTextColor(Color.WHITE);
				c2.setText("-2" + "°");
				c2.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c3 = new TextView(getActivity());
				c3.setShadowLayer(2, 2, 2, Color.BLACK);
				c3.setTextSize(16);
				c3.setTextColor(Color.WHITE);
				c3.setText("-1" + "°");
				c3.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c4 = new TextView(getActivity());
				c4.setShadowLayer(2, 2, 2, Color.BLACK);
				c4.setTextSize(16);
				c4.setTextColor(Color.WHITE);
				c4.setText("0" + "°");
				c4.setGravity(Gravity.CENTER_HORIZONTAL);

				TextView c5 = new TextView(getActivity());
				c5.setShadowLayer(2, 2, 2, Color.BLACK);
				c5.setTextSize(16);
				c5.setTextColor(Color.WHITE);
				c5.setText("-2" + "°");
				c5.setGravity(Gravity.CENTER_HORIZONTAL);



				tr.addView(c1);
				tr.addView(c2);
				tr.addView(c3);
				tr.addView(c4);
				tr.addView(c5);
				daily.addView(tr);}



		}

		return rootView;
	}

	// TODO: Rename method, update argument and hook method into UI event
	public void onButtonPressed(Uri uri) {
		if (mListener != null) {
			mListener.onFragmentInteraction(uri);
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		/*try {
			mListener = (OnFragmentInteractionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnFragmentInteractionListener");
		}*/
	}

	@Override
	public void onDetach() {
		super.onDetach();
		mListener = null;
	}

	/**
	 * This interface must be implemented by activities that contain this
	 * fragment to allow an interaction in this fragment to be communicated
	 * to the activity and potentially other fragments contained in that
	 * activity.
	 * <p/>
	 * See the Android Training lesson <a href=
	 * "http://developer.android.com/training/basics/fragments/communicating.html"
	 * >Communicating with Other Fragments</a> for more information.
	 */
	public interface OnFragmentInteractionListener {
		// TODO: Update argument type and name
		public void onFragmentInteraction(Uri uri);
	}

}
