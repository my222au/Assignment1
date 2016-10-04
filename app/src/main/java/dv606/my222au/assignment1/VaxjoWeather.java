/**
 * VaxjoWeather.java
 * Created: May 9, 2010
 * Jonas Lundberg, LnU
 */

package dv606.my222au.assignment1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import dvs606.my222au.assignment1.R;

/**
 * This is a first prototype for a weather app. It is currently
 * only downloading weather data for Växjö.
 * <p/>
 * This activity downloads weather data and constructs a WeatherReport,
 * a data structure containing weather data for a number of periods ahead.
 * <p/>
 * The WeatherHandler is a SAX parser for the weather reports
 * (forecast.xml) produced by www.yr.no. The handler constructs
 * a WeatherReport containing meta data for a given location
 * (e.g. city, country, last updated, next update) and a sequence
 * of WeatherForecasts.
 * Each WeatherForecast represents a forecast (weather, rain, wind, etc)
 * for a given time period.
 * <p/>
 * The next task is to construct a list based GUI where each row
 * displays the weather data for a single period.
 *
 * @author jlnmsi
 */

public class VaxjoWeather extends AppCompatActivity {
    public static String TAG = "dv606.weather";

    private InputStream input;
    private WeatherReport report = null;
    List<WeatherForecast> mForecastList;
    private ListView mListView;
    WeahterAdapter adapter;
    private TextView mNetworkLabel;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the layout
        setContentView(R.layout.main);
        // Initialize the toolbar

        mNetworkLabel = (TextView)findViewById(R.id.noNetwoklabel);
        mForecastList = new ArrayList<WeatherForecast>();
        mListView = (ListView) findViewById(R.id.weatherListView);
        adapter = new WeahterAdapter(this, mForecastList);
        mListView.setAdapter(adapter);

     if(isNetworkAvailable()) {
         try {

             URL url = new URL("http://www.yr.no/sted/Sverige/Kronoberg/V%E4xj%F6/forecast.xml");
             AsyncTask task = new WeatherRetriever().execute(url);

           mNetworkLabel.setVisibility(View.INVISIBLE);
         } catch (IOException ioe) {
             Log.e(TAG,"Excepiton cought",ioe);


         }catch (RuntimeException e){
            Log.e(TAG,"Exception caught",e);
         }
     }else {
       mNetworkLabel.setVisibility(View.VISIBLE);

     }

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.weather_menu, menu);
        return true;
    }

    private void printReportToLog() {
        if (this.report != null) {
            /*Print some meta data to the UI for the testing purposes*/


        	/* Print location meta data */
            Log.i(TAG, report.toString());

        	/* Print forecasts */
            int count = 0;
            for (WeatherForecast forecast : report) {
                count++;
                Log.i(TAG, "Forecast #" + count);
                Log.i(TAG, forecast.toString() + "");
                mForecastList.add(forecast);
            }
            adapter.notifyDataSetChanged();

        } else {
            Log.e(TAG, "Weather report has not been loaded.");
        }
    }

    private class WeatherRetriever extends AsyncTask<URL, Void, WeatherReport> {
        protected WeatherReport doInBackground(URL... urls) {

            try {
                return WeatherHandler.getWeatherReport(urls[0]);

            } catch (Exception e) {
                throw new RuntimeException();
            }


        }

        protected void onProgressUpdate(Void... progress) {


        }

        protected void onPostExecute(WeatherReport result) {
            Toast.makeText(getApplicationContext(), "WeatherRetriever task finished", Toast.LENGTH_LONG).show();


            report = result;
            printReportToLog();

        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;

        }

        return isAvailable;
    }

    public class WeahterAdapter extends ArrayAdapter {


        private List<WeatherForecast> mForecasts;

        public WeahterAdapter(Context context, List<WeatherForecast> forecasts) {
            super(context, R.layout.weather_list_item, forecasts);
            mForecasts = forecasts;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row;

            if (convertView == null) {
                LayoutInflater inflator = getLayoutInflater();
                row = inflator.inflate(R.layout.weather_list_item, parent, false);
            } else
                row = convertView;

            TextView tempeteratureLabel = (TextView) row.findViewById(R.id.temperatureLabel);
            TextView dateLabel = (TextView) row.findViewById(R.id.dayLabel);
            TextView timeLabel = (TextView) row.findViewById(R.id.timeLable);
            TextView weatherIcon = (TextView) row.findViewById(R.id.weatherIcon);
            TextView summaryLabel = (TextView) row.findViewById(R.id.summaryLabel);
            TextView dayNumberlabel = (TextView) row.findViewById(R.id.dayNumberLabel);
            TextView rainLabel = (TextView) row.findViewById(R.id.rainLabel);


            char degree = 0x00B0; // Shows the deegree char;
            WeatherForecast weatherForecast = mForecasts.get(position);
            tempeteratureLabel.setText(weatherForecast.getTemperature() + "" + degree);
            dateLabel.setText(weatherForecast.getstarttday());
            timeLabel.setText(weatherForecast.getStartHHMM() + "-" + weatherForecast.getEndHHMM() + "");

            weatherIcon.setTypeface(Typeface.createFromAsset(getAssets(), "weatherfonts/weathericons-regular-webfont.ttf")); // shows the weather icon
            dayNumberlabel.setText(weatherForecast.getStartYYMMDD());
            rainLabel.setText(weatherForecast.getRain() + "mm");


            updateWeather(weatherIcon, weatherForecast, summaryLabel);


            return row;
        }


        /***
         * @param weatherIcon
         * @param weatherForecast
         * @param summaryLabel
         * Uppdates the viwe depending on the weather, The icons is font icon from https://erikflowers.github.io/weather-icons/
            which are in value Folder
         */

        private void updateWeather(TextView weatherIcon, WeatherForecast weatherForecast, TextView summaryLabel) {
            int weatheCode = weatherForecast.getWeatherCode();

            if (weatheCode == 1) {
                weatherIcon.setText(R.string.wi_day_sunny);
                summaryLabel.setText(R.string.summary_sunny);
                if (weatherForecast.getPeriodCode() == 3 || weatherForecast.getPeriodCode() == 0) {
                    weatherIcon.setText(R.string.wi_night_clear);
                }


            }
            if (weatheCode == 3) {
                weatherIcon.setText(R.string.wi_day_cloudy_gusts);
                summaryLabel.setText(R.string.summary_partly_cloudy);
                if (weatherForecast.getPeriodCode() == 3 || weatherForecast.getPeriodCode() == 0) {
                    weatherIcon.setText(R.string.wi_night_alt_cloudy_gusts);
                }

            }
            if (weatheCode == 4) {
                weatherIcon.setText(R.string.wi_cloudy);
                summaryLabel.setText(R.string.summary_cloudy);
                if (weatherForecast.getPeriodCode() == 3 || weatherForecast.getPeriodCode() == 0) {
                    weatherIcon.setText(R.string.wi_night_cloudy);
                }


            }
            if (weatheCode == 10 || weatheCode == 41 || weatheCode == 6 || weatheCode == 9 || weatheCode == 5) {
                weatherIcon.setText(R.string.wi_showers);
                summaryLabel.setText(R.string.summary_rainy);
                if (weatherForecast.getPeriodCode() == 3 || weatherForecast.getPeriodCode() == 0) {
                    weatherIcon.setText(R.string.wi_night_rain);
                }

            }


            if (weatheCode == 50) {
                weatherIcon.setText(R.string.wi_snow);
                summaryLabel.setText(R.string.summary_snow);

                if (weatherForecast.getPeriodCode() == 3 || weatherForecast.getPeriodCode() == 0) {
                    weatherIcon.setText(R.string.wi_night_snow);
                }
            }


        }


    }


}