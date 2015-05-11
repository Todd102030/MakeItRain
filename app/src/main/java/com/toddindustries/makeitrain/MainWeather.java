package com.toddindustries.makeitrain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import org.w3c.dom.Text;


public class MainWeather extends FragmentActivity{
//extends SearchView implements OnQueryTextListener
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	//ViewPager mViewPager;
	ViewPager viewPager;
	TabsPagerAdapter mAdapter;

	Integer[] backgroundWallpaper = {R.drawable.b0, R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4,R.drawable.b5,R.drawable.b6,R.drawable.b7,R.drawable.b8,R.drawable.b9,R.drawable.b10,R.drawable.b11,R.drawable.b12,R.drawable.b13,R.drawable.b14,R.drawable.b15,R.drawable.b16,R.drawable.b17,R.drawable.b18,R.drawable.b19,R.drawable.b20,R.drawable.b21,R.drawable.b22,R.drawable.b23,R.drawable.b24,R.drawable.b25,R.drawable.b26,R.drawable.b27,R.drawable.b28,R.drawable.b29,R.drawable.b30,R.drawable.b31,R.drawable.b32,R.drawable.b33,R.drawable.b34,R.drawable.b35,R.drawable.b36,R.drawable.b37,R.drawable.b38,R.drawable.b39,R.drawable.b41,R.drawable.b41,R.drawable.b42,R.drawable.b43,R.drawable.b44,R.drawable.b45,R.drawable.b46,R.drawable.b47};
	int[] icons = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, R.drawable.i18, 0, 0, 0, 0, 0, 0, 0, 0, 0, R.drawable.i28, 0, 0, R.drawable.i31, R.drawable.i32};
	//icons[18] = R.drawable.i18;
	//Strings for all the json info
	String city = "";
	String region = "";
	String country = "";
	String temp = "--";
	String condition = "";
	String high = "--";
	String low = "--";
	String weatherCode = "31";
	TextView debugTextBox;
	TextView bigTemp;
	TextView conditions;
	TextView location;
	TextView highAndLow;
	TextView updatedLast;
	TableLayout dailyTable;
	TableLayout weeklyTable;

	String[] forecastDay = {"Afternoon", "Evening", "Night", "Late Night", "Early Morning", "Morning"};
	int[] dayTemp = {23, 20, 18, 12, 10, 12};
	String[] Date = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	String[] test = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "d", "t", "e", "q", "p"};
	int[] percentTest = {1,2,4,5,6,7,8,9};
	int[] tempHigh = {22,44,66,12,45,12,65};
	int[] tempLow = {12,54,23,12,9,24};
	int h = 0;
	int z;

	String[] hourlyForecastTime = {"","","","",""};
	String[] hourlyForecastTemp = {"","","","",""};
	String[] hourlyForecastCondition = {"","","","",""};

	String[] forecastDates = {"","","","","","",""};
	String[] forecastConditions = {"","","","","","",""};
	String[] forecastPrecip = {"","","","","","",""};
	String[] forecastHighTemp = {"","","","","","",""};
	String[] forecastLowTemp = {"","","","","","",""};
	int[] imageNumbers = {5, 5, 5, 5, 5, 5, 5};


	RelativeLayout layout;
	//URL Strings
	//String yahoo = "https://query.yahooapis.com/v1/public/yql?";
	//String yqlQuery = "select * from weather.forecast where woeid in (select woeid from geo.places(1) where text=\"peterborough, canada\")";
	//final String yqlURL = yahoo + yqlQuery + "&format=json";
	//"toronto"
	String yahoo = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22";
	String woeid = "peterborough on";
	String woeidFormatted = woeid.replaceAll(" ", "%20");
	String yahooEnd = "%22)&format=json";
	String URL = yahoo + woeidFormatted + yahooEnd;

	String wunderground = "https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20wunderground.forecast%20where%20location%3D'";
	String wundergroundEnd = "'&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";
	String wURL = wunderground + woeidFormatted + wundergroundEnd;

	//Context thiscontext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_weather);

		//Init code
		viewPager = (ViewPager)findViewById(R.id.pager);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		viewPager.setBackgroundResource(backgroundWallpaper[31]);





		new RequestYahoo().execute(URL);
		new RequestWunderground().execute(wURL);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		//mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

		// Set up the ViewPager with the sections adapter.
		//mViewPager = (ViewPager) findViewById(R.id.pager);
		//mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	public int setURL(String woeid){
		String woeidFormatted = woeid.replaceAll(" ", "%20");
		URL = yahoo + woeidFormatted + yahooEnd;
		wURL = wunderground + woeidFormatted + wundergroundEnd;

		return 1;
	}

	public String returnArrayData(int key, String value){
		if(value == "hourlyTime"){
			return hourlyForecastTime[key];
		}
		if(value == "hourlyTemp"){
			return hourlyForecastTemp[key];
		}
		if(value == "hourlyCondition"){
			return hourlyForecastCondition[key];
		}
		if(value == "date"){
			return forecastDates[key];
		}
		if(value == "condition"){
			return forecastConditions[key];
		}
		if(value == "precip"){
			return forecastPrecip[key];
		}
		if(value == "highTemp"){
			return forecastHighTemp[key];
		}
		if(value == "lowTemp"){
			return forecastLowTemp[key];
		}
		else{
			return "-";
		}
	}


	public String returnData(String value){

		if (value == "city"){
			return city;
		}if (value == "region"){
			return region;
		}if (value == "country"){
			return country;
		}if (value == "temp"){
			return temp;
		}if (value == "condition"){
			return condition;
		}if (value == "high"){
			return high;
		}if (value == "low"){
			return low;
		}if (value == "code"){
			return weatherCode;
		}else{
			return "-";
		}

		/*
		* String city = "";
	String region = "";
	String country = "";
	String temp = "";
	String condition = "";
	String high = "";
	String low = "";*/

	}

	class RequestYahoo extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			//debugTextBox.setText("Loading...");

		}
		@Override

		protected String doInBackground(String... url) {
			// constants
			int timeoutSocket = 5000;
			int timeoutConnection = 5000;

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient client = new DefaultHttpClient(httpParameters);

			HttpGet httpget = new HttpGet(url[0]);

			try {
				HttpResponse getResponse = client.execute(httpget);
				final int statusCode = getResponse.getStatusLine().getStatusCode();

				if(statusCode != HttpStatus.SC_OK) {
					Log.w("MyApp", "Download Error: " + statusCode + "| for URL: " + url);
					return null;
				}

				String line = "";
				StringBuilder total = new StringBuilder();

				HttpEntity getResponseEntity = getResponse.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseEntity.getContent()));

				while((line = reader.readLine()) != null) {
					total.append(line);
				}

				line = total.toString();
				//debugTextBox.setText(line);
				Log.e("Output from function: ", line);

				return line;
			} catch (Exception e) {
				Log.w("MyApp", "Download Exception : " + e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// do something with result
			try {
				if(result!=null) {
					//final View rootView = findViewById(R.layout.fragment_overview, container, false);

					Date now = new Date();
					JSONObject json = new JSONObject(result);
					debugTextBox = (TextView)findViewById(R.id.debugText);
					bigTemp = (TextView)findViewById(R.id.bigTemp);
					//bigTemp = (TextView)findViewById(R.id.bigTemp);
					conditions = (TextView)findViewById(R.id.conditions);
					location = (TextView)findViewById(R.id.location);
					highAndLow = (TextView)findViewById(R.id.highAndLowTemps);
					updatedLast = (TextView)findViewById(R.id.updatedLast);
					//JSONObject item = (JSONObject) json.get("item");
					//JSONObject condition = (JSONObject) item.get("condition");


					JSONObject queryJSON = json.getJSONObject("query");
						JSONObject resultsJSON = queryJSON.getJSONObject("results");
							JSONObject channelJSON = resultsJSON.getJSONObject("channel");
								JSONObject locationJSON = channelJSON.getJSONObject("location");
									city = locationJSON.optString("city");
									region = locationJSON.optString("region");
									country = locationJSON.optString("country");
								JSONObject item = channelJSON.getJSONObject("item");
									JSONObject conditionJSON = item.getJSONObject("condition");
										temp = conditionJSON.getString("temp");
										condition = conditionJSON.getString("text");
										weatherCode = conditionJSON.getString("code");
									JSONArray forecast = item.getJSONArray("forecast");
										JSONObject today = forecast.getJSONObject(0);
											high = today.getString("high");
											low = today.getString("low");

					//Log.i("MyActivity", "JSON read");
					//debugTextBox.setText("");

					bigTemp.setText(temp + "°");
					location.setText(city + " " + region + ", " + country);
					conditions.setText(condition);
					highAndLow.setText("⇑" + high + "°" + "⇓" + low + "°");
					updatedLast.setText("Last updated at " + DateFormat.getTimeInstance().format(now));

					viewPager.setBackgroundResource(backgroundWallpaper[Integer.parseInt(weatherCode)]);
					//debugTextBox.setText(weatherCode);

				}
			}catch (JSONException e){
				Log.e("JSON Exception", e.toString());
			}

			//debugTextBox.setText(result);
		}
	}


	class RequestWunderground extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();


			//debugTextBox.setText("Loading...");

		}
		@Override

		protected String doInBackground(String... url) {
			// constants
			int timeoutSocket = 5000;
			int timeoutConnection = 5000;

			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			HttpClient client = new DefaultHttpClient(httpParameters);

			HttpGet httpget = new HttpGet(url[0]);

			try {
				HttpResponse getResponse = client.execute(httpget);
				final int statusCode = getResponse.getStatusLine().getStatusCode();

				if(statusCode != HttpStatus.SC_OK) {
					Log.w("MyApp", "Download Error: " + statusCode + "| for URL: " + url);
					return null;
				}

				String line = "";
				StringBuilder total = new StringBuilder();

				HttpEntity getResponseEntity = getResponse.getEntity();

				BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseEntity.getContent()));

				while((line = reader.readLine()) != null) {
					total.append(line);
				}

				line = total.toString();
				//debugTextBox.setText(line);
				Log.e("Output from function: ", line);

				return line;
			} catch (Exception e) {
				Log.w("MyApp", "Download Exception : " + e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// do something with result
			try {
				if(result!=null) {
					//final View rootView = findViewById(R.layout.fragment_overview, container, false);

					Date now = new Date();
					JSONObject json = new JSONObject(result);

					debugTextBox = (TextView)findViewById(R.id.debugText);

					dailyTable = (TableLayout)findViewById(R.id.dailyTable);
					weeklyTable = (TableLayout)findViewById(R.id.weeklyTable);

					JSONObject queryJSON = json.getJSONObject("query");
						JSONObject resultsJSON = queryJSON.getJSONObject("results");
							JSONObject forecastJSON = resultsJSON.getJSONObject("forecast");
								JSONObject txt_forecastJSON = forecastJSON.getJSONObject("txt_forecast");
									JSONArray hourlyforecastdayJSONArray = txt_forecastJSON.getJSONArray("forecastday");
										for(int i = 0; i <= 4; i++){
											JSONObject currentForecast = hourlyforecastdayJSONArray.getJSONObject(i);
											//hourlyForecastTemp[i] = currentForecast.getString("");
											String fcTimeTemp = currentForecast.getString("title");
											hourlyForecastTime[i] = fcTimeTemp.replaceAll(" ", "\n");
											hourlyForecastCondition[i] = currentForecast.getString("icon");

										}
								JSONObject simpleforecastJSON = forecastJSON.getJSONObject("simpleforecast");
									JSONArray forecastdayJSONArray = simpleforecastJSON.getJSONArray("forecastday");
										for(int i = 0; i <= 5; i++){
											JSONObject currentForecast = forecastdayJSONArray.getJSONObject(i);
												JSONObject dateJSON = currentForecast.getJSONObject("date");
													forecastDates[i] = dateJSON.getString("weekday");
													forecastConditions[i] = currentForecast.getString("conditions");
													forecastPrecip[i] = currentForecast.getString("pop");
												JSONObject highJSON = currentForecast.getJSONObject("high");
													forecastHighTemp[i] = highJSON.getString("celsius");
												JSONObject lowJSON = currentForecast.getJSONObject("low");
													forecastLowTemp[i] = lowJSON.getString("celsius");

											String iconString = currentForecast.getString("icon");
											Log.e("Error on forecast ", i + iconString);
											if (iconString.equals("sleet") == true){
												imageNumbers[i] = 18;
											}
											else if (iconString.equals("chancesleet")){
												imageNumbers[i] = 18;
											}
											else if (iconString.equals("chancesleet")){
												imageNumbers[i] = 18;
											}
											else if (iconString.equals("mostlycloudy")){
												imageNumbers[i] = 28;
											}
											else if (iconString.equals("clear")){
												imageNumbers[i] = 32;
											}
											else{
												imageNumbers[i] = 32;
											}
										}

					TableLayout forecast = (TableLayout)findViewById(R.id.weeklyTable);  //sets the table used
					forecast.setStretchAllColumns(true);
					forecast.bringToFront();


					forecast.removeAllViews();




					for(int j = 0; j <6; j++) {

						TableRow tr = new TableRow(getBaseContext());   //starts new table row

						TextView c1 = new TextView(getBaseContext());   //creates a text view to hold array value
						c1.setShadowLayer(2, 2, 2, Color.BLACK);
						c1.setTextSize(16);
						c1.setTextColor(Color.WHITE);
						c1.setText(forecastDates[j]);                //sets the text from array Date depending on H value

						ImageView c2 = new ImageView(getBaseContext()); //sets an image view, creating a new column
						c2.setImageResource(icons[imageNumbers[j]]);//sets the image from a drawable
						TextView c3 = new TextView(getBaseContext());      //new column
						c3.setShadowLayer(2, 2, 2, Color.BLACK);
						c3.setTextSize(16);
						c3.setTextColor(Color.WHITE);
						c3.setText(forecastPrecip[j] + "%");
						TextView c4 = new TextView(getBaseContext());       //new column
						c4.setShadowLayer(2, 2, 2, Color.BLACK);
						c4.setTextSize(16);
						c4.setTextColor(Color.WHITE);
						//"⇑" + high + "°" + "⇓"
						c4.setText("⇑ " + forecastHighTemp[j] + "°");
						TextView c5 = new TextView(getBaseContext());       //new column
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


					TableLayout daily = (TableLayout)findViewById(R.id.dailyTable);
					daily.setStretchAllColumns(true);
					daily.bringToFront();
					daily.removeAllViews();
					//  daily.setBackgroundDrawable(gd);
					for(int x = 0; x <1; x++){
						for(int y = 0; y <1; y++) {

							TableRow tr = new TableRow(getBaseContext());
							TextView c1 = new TextView(getBaseContext());
							c1.setShadowLayer(2, 2, 2, Color.BLACK);
							c1.setTextSize(16);
							c1.setTextColor(Color.WHITE);
							c1.setText(hourlyForecastTime[0]);
							c1.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c2 = new TextView(getBaseContext());
							c2.setShadowLayer(2, 2, 2, Color.BLACK);
							c2.setTextSize(16);
							c2.setTextColor(Color.WHITE);
							c2.setText(hourlyForecastTime[1]);
							c2.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c3 = new TextView(getBaseContext());
							c3.setShadowLayer(2, 2, 2, Color.BLACK);
							c3.setTextSize(16);
							c3.setTextColor(Color.WHITE);
							c3.setText(hourlyForecastTime[2]);
							c3.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c4 = new TextView(getBaseContext());
							c4.setShadowLayer(2, 2, 2, Color.BLACK);
							c4.setTextSize(16);
							c4.setTextColor(Color.WHITE);
							c4.setText(hourlyForecastTime[3]);
							c4.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c5 = new TextView(getBaseContext());
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

							TableRow tr = new TableRow(getBaseContext());
							ImageView c1 = new ImageView(getBaseContext());
							c1.setImageResource(R.drawable.ic_launcher);

							ImageView c2 = new ImageView(getBaseContext());
							c2.setImageResource(R.drawable.ic_launcher);

							ImageView c3 = new ImageView(getBaseContext());
							c3.setImageResource(R.drawable.ic_launcher);

							ImageView c4 = new ImageView(getBaseContext());
							c4.setImageResource(R.drawable.ic_launcher);

							ImageView c5 = new ImageView(getBaseContext());
							c5.setImageResource(R.drawable.ic_launcher);


							tr.addView(c1);
							tr.addView(c2);
							tr.addView(c3);
							tr.addView(c4);
							tr.addView(c5);
							daily.addView(tr);}

						for(int y = 0; y <1; y++) {

							TableRow tr = new TableRow(getBaseContext());
							TextView c1 = new TextView(getBaseContext());
							c1.setShadowLayer(2, 2, 2, Color.BLACK);
							c1.setTextSize(16);
							c1.setTextColor(Color.WHITE);
							c1.setText("2" + "°");
							c1.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c2 = new TextView(getBaseContext());
							c2.setShadowLayer(2, 2, 2, Color.BLACK);
							c2.setTextSize(16);
							c2.setTextColor(Color.WHITE);
							c2.setText("-2" + "°");
							c2.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c3 = new TextView(getBaseContext());
							c3.setShadowLayer(2, 2, 2, Color.BLACK);
							c3.setTextSize(16);
							c3.setTextColor(Color.WHITE);
							c3.setText("-1" + "°");
							c3.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c4 = new TextView(getBaseContext());
							c4.setShadowLayer(2, 2, 2, Color.BLACK);
							c4.setTextSize(16);
							c4.setTextColor(Color.WHITE);
							c4.setText("0" + "°");
							c4.setGravity(Gravity.CENTER_HORIZONTAL);

							TextView c5 = new TextView(getBaseContext());
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


					//Log.i("MyActivity", "JSON read");
					//debugTextBox.setText("");

/*
					bigTemp.setText(temp + "°");
					location.setText(city + " " + region + ", " + country);
					conditions.setText(condition);
					highAndLow.setText("⇑" + high + "°" + "⇓" + low + "°");
					updatedLast.setText("Last updated at " + DateFormat.getTimeInstance().format(now));

					viewPager.setBackgroundResource(backgroundWallpaper[Integer.parseInt(weatherCode)]);
					*/
					//debugTextBox.setText(weatherCode);

				}
			}catch (JSONException e){
				Log.e("JSON Exception", e.toString());
			}

			//debugTextBox.setText(result);
		}
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_weather, menu);
		//return true;
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
		// Configure the search info and add any event listeners
		searchView.setSubmitButtonEnabled(true);

		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {

		}

		//searchView.setOnSearchClickListener(this);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.refresh) {
			new RequestYahoo().execute(URL);
			new RequestWunderground().execute(wURL);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return PlaceholderFragment.newInstance(position + 1);
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
				case 0:
					return getString(R.string.title_section1).toUpperCase(l);
				case 1:
					return getString(R.string.title_section2).toUpperCase(l);
				case 2:
					return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
								 Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_weather, container, false);
			return rootView;
		}
	}

}
