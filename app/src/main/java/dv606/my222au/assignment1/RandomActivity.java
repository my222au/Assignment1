package dv606.my222au.assignment1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import dvs606.my222au.assignment1.R;


public class RandomActivity extends AppCompatActivity {

    private TextView mRandomLabel;
    private Button   mButton;
    private int mRandomNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        mRandomLabel = (TextView)findViewById(R.id.randomNrLabel);
        mButton=(Button)findViewById(R.id.button);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random  rand = new Random();
                mRandomNr=rand.nextInt(101);
                mRandomLabel.setText(mRandomNr+"");
            }
        });




    }


}
