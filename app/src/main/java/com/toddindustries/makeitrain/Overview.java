package com.toddindustries.makeitrain;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.service.media.MediaBrowserService;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Overview.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Overview#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Overview extends Fragment {
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
	 * @return A new instance of fragment Overview.
	 */
	// TODO: Rename and change types and number of parameters
	public static Overview newInstance(String param1, String param2) {
		Overview fragment = new Overview();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

	public Overview() {
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

	TextView debugTextBox;
	TextView bigTemp;
	TextView conditions;
	TextView location;
	TextView highAndLow;
	TextView updatedLast;
	String URLOutput = "0";
	Integer[] backgroundWallpaper = {R.drawable.b0, R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10,R.drawable.b11,R.drawable.b12,R.drawable.b13,R.drawable.b14,R.drawable.b15,R.drawable.b16,R.drawable.b17,R.drawable.b18,R.drawable.b19,R.drawable.b20,R.drawable.b21,R.drawable.b22,R.drawable.b23,R.drawable.b24,R.drawable.b25,R.drawable.b26,R.drawable.b27,R.drawable.b28,R.drawable.b29,R.drawable.b30,R.drawable.b31,R.drawable.b32,R.drawable.b33,R.drawable.b34,R.drawable.b35,R.drawable.b36,R.drawable.b37,R.drawable.b38,R.drawable.b39,R.drawable.b41,R.drawable.b41,R.drawable.b42,R.drawable.b43,R.drawable.b44,R.drawable.b45,R.drawable.b46,R.drawable.b47};
	String weatherCode = "31";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		final View rootView = inflater.inflate(R.layout.fragment_overview, container, false);
		Calendar c = Calendar.getInstance();
		int seconds = c.get(Calendar.SECOND);
		int minutes = c.get(Calendar.MINUTE);
		int hours = c.get(Calendar.HOUR);
		//JSONObject json = new JSONObject(readUrl("http://api.wunderground.com" +"/api/106c4dee47162999/history_20060405/q/CA/San_Francisco.json"));
		MainWeather a = (MainWeather) getActivity();
		//debugTextBox = (TextView)getActivity().findViewById(R.id.debugText);
		debugTextBox = (TextView)rootView.findViewById(R.id.debugText);
		bigTemp = (TextView)rootView.findViewById(R.id.bigTemp);
		conditions = (TextView)rootView.findViewById(R.id.conditions);
		location = (TextView)rootView.findViewById(R.id.location);
		highAndLow = (TextView)rootView.findViewById(R.id.highAndLowTemps);
		updatedLast = (TextView)rootView.findViewById(R.id.updatedLast);

		String temp = a.returnData("temp");
		//String temp = a.returnData("temp");
		String city = a.returnData("city");
		String region = a.returnData("region");
		String country = a.returnData("country");
		String condition = a.returnData("condition");
		String high = a.returnData("high");
		String low = a.returnData("low");
		Integer weatherCode = Integer.parseInt(a.returnData("code"));
		bigTemp.setText(temp + "°");
		location.setText(city + " " + region + ", " + country);
		conditions.setText(condition);
		highAndLow.setText("⇑" + high + "°" + "⇓" + low + "°");
		updatedLast.setText("Last Updated At " + hours + ":" + minutes + ":" + seconds);
		//rootView.setBackgroundResource(backgroundWallpaper[weatherCode]);
		//Log.e("JSON Output", jsoncode);
		//a.setWallpaper(weatherCode);
		//debugTextBox.setText(weatherCode.toString());
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
