package dv606.my222au.assignment1;

import android.app.Fragment;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import dvs606.my222au.assignment1.R;


public class BeerPagerFragment extends Fragment {

    private View mView;
    private static final String BODY_TEXT ="header_text";

    private static final String BODY_IMAGE=   "body_Image";
    private static  final String BODY_NAME = "body_name";




    public static  BeerPagerFragment  createFragment(int bodyImage, String bodyText,int beerName){

        BeerPagerFragment beerPagerFragment = new BeerPagerFragment();
        Bundle args = new Bundle();
        args.putString(BODY_TEXT, bodyText);
        args.putInt(BODY_IMAGE, bodyImage);
        args.putInt(BODY_NAME, beerName);
        beerPagerFragment.setArguments(args);
        return beerPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView =(ViewGroup) inflater.inflate(R.layout.beer_fragment_layout,container,false);
       TextView beerNameLabel = (TextView)mView.findViewById(R.id.beerNameLabel);


        return mView;

    }

    @Override
    public void onStart() {
        super.onStart();

        String headerText;
      int body,bodyName;
        Bundle args = getArguments();

        if(args!=null){
            headerText = args.getString(BODY_TEXT);
            body  = args.getInt(BODY_IMAGE);
            bodyName=args.getInt(BODY_NAME);

        }
        else {
            headerText = "Default Tex";
            body = 0;
            bodyName = 0;
        }


        ((ImageView)mView.findViewById(R.id.beerImageView)).setImageResource(body);
        ((TextView)mView.findViewById(R.id.descriptionTextView)).setText(headerText);
        ((TextView)mView.findViewById(R.id.beerNameLabel)).setText(bodyName);


    }
}
