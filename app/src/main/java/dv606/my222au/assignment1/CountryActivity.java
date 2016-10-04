package dv606.my222au.assignment1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import dvs606.my222au.assignment1.R;


public class CountryActivity extends AppCompatActivity {
    private ListView mListView;
    private ArrayList<String> countryList = new ArrayList<String>();
    private ArrayList<String> yearList = new ArrayList<String>();
    private String mCountry;  // chagne name to country and name
    private String mYear;
    private TextView mWelcomTv;
    private ImageView mImageView;
    private LinearLayout mLinearLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        mListView = (ListView) findViewById(R.id.list);
        ListAdapter adpt = new CountryAdpter(this);
        mListView.setAdapter(adpt);

        mWelcomTv = (TextView) findViewById(R.id.welcomeLabel);
        mWelcomTv.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Dosis-Regular.ttf"));
        mImageView = (ImageView) findViewById(R.id.iconImageView);
        mLinearLayout = (LinearLayout) findViewById(R.id.myLinear);

        mLinearLayout.setVisibility(View.INVISIBLE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ic_menu_addbutton:
                Intent intent = new Intent(this, AddCountry.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 1);

                return true;

            default:
                return super.onOptionsItemSelected(item);


        }
    }


    // gets result back form add contry activity  and hides the welcomtext and icon
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == Activity.RESULT_OK) {
            mCountry = data.getStringExtra("result");
            mYear = data.getStringExtra("result2");


            countryList.add(mCountry);
            yearList.add(mYear);
            mImageView.setVisibility(View.INVISIBLE);
            mWelcomTv.setVisibility(View.INVISIBLE);
            mLinearLayout.setVisibility(View.VISIBLE);

        }


    }

    /***
     * CLass conrty adpater for the list,
     */
    class CountryAdpter extends ArrayAdapter<String> {
        private TextView mYearView;
        private TextView mCountryview;


        public CountryAdpter(Context context) {
            super(context, R.layout.country_item_list, countryList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View row;

            if (convertView == null) {    // Create new row view object
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.country_item_list, parent, false);
            } else
                row = convertView;


            mCountryview = (TextView) row.findViewById(R.id.country);
            mCountryview.setText(countryList.get(position));
            mYearView = (TextView) row.findViewById(R.id.yearDate);
            mYearView.setText(yearList.get(position));


            return row;
        }

    }


}


