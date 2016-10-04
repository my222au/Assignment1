package dv606.my222au.assignment1;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v13.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import dvs606.my222au.assignment1.R;


public class BeerPager extends AppCompatActivity {

    private TextView mbeerCount;
    public int pos = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beer_pager);


        // Instantiate a ViewPager and a PagerAdapter.
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new BeerPagerAdapter(getFragmentManager());
        pager.setAdapter(pagerAdapter);
        mbeerCount = (TextView) findViewById(R.id.beerCountlabel);
        final Beer beer = new Beer();
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not using
            }

            @Override
            public void onPageSelected(int position) {
                mbeerCount.setText("Beer " + ((position + 1) + " of " + beer.images.length));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


}

class BeerPagerAdapter extends FragmentPagerAdapter {

    public Fragment[] fragments = new Fragment[7];

    Beer beer = new Beer();
    BeerPager beerpager = new BeerPager();


    public BeerPagerAdapter(FragmentManager fm) {
        super(fm);




           for(int i =0;i<fragments.length;i++){
            fragments[i] = BeerPagerFragment.createFragment(beer.images[i], beer.description[i], beer.beerName[i]);}

//            fragments[1] = BeerPagerFragment.createFragment(beer.images[1], beer.description[1], beer.beerName[1]);
//            fragments[2] = BeerPagerFragment.createFragment(beer.images[2], beer.description[2], beer.beerName[2]);
//            fragments[3] = BeerPagerFragment.createFragment(beer.images[3], beer.description[3], beer.beerName[3]);
//            fragments[4] = BeerPagerFragment.createFragment(beer.images[4], beer.description[4], beer.beerName[4]);
//            fragments[5] = BeerPagerFragment.createFragment(beer.images[5], beer.description[5], beer.beerName[5]);
//            fragments[6] = BeerPagerFragment.createFragment(beer.images[6], beer.description[6], beer.beerName[6]);




    }


    @Override
    public Fragment getItem(int position) {

        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }





}










