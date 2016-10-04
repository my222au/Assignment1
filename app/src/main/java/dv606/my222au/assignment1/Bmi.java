package dv606.my222au.assignment1;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

import dvs606.my222au.assignment1.R;


public class Bmi extends AppCompatActivity {
    private TextView mTitleLabel;
    private TextView mBmiValueLabel;
    private EditText mLenghtInput;
    private EditText mWeightInput;
    private Button mCalcButton;
    private Button mResetButton;
    private double mBmiValue;
    private TextView mSummary;
    private boolean isCalulated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);


        mBmiValueLabel = (TextView) findViewById(R.id.bmiLabel);
        mSummary = (TextView) findViewById(R.id.summaryLabel);
        mLenghtInput = (EditText) findViewById(R.id.heightInput);
        mWeightInput = (EditText) findViewById(R.id.weightInput);
        mCalcButton = (Button) findViewById(R.id.calcButton);
        mResetButton = (Button) findViewById(R.id.resetButton);


        mBmiValueLabel.setVisibility(View.INVISIBLE);// not shown in the screen oncreate.
        mSummary.setVisibility(View.INVISIBLE);


        mCalcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mLenghtInput.getText().toString().isEmpty()) {
                    mLenghtInput.setError("Must enter length  to Calculate");
                } else if (mWeightInput.getText().toString().isEmpty()) {
                    mWeightInput.setError("Must enter weight to Calculate");
                } else if (checkLenght()) {
                    mLenghtInput.setError("Please insert a porprate length");
                } else if (checkWeight()) {
                    mWeightInput.setError("Please insert a porprate wieght");
                } else {


                    InputMethodManager inManger= (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    inManger.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    mBmiValueLabel.setText(getBmiValue());



                    mBmiValueLabel.setVisibility(View.VISIBLE);
                    mSummary.setVisibility(View.VISIBLE);
                    mCalcButton.setEnabled(false);
                    changeColor();


                }


            }
        });

        changeColor();// changs the calcButton when is clicked

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                mCalcButton.setEnabled(true);
                changeColor();


            }
        });


    }

    private double parseLength() {
        double lengthValue = 0;
        String lengthString = mLenghtInput.getText().toString();
        if (lengthString.length() > 0) {

            lengthValue = Double.parseDouble(lengthString);
        }

        return lengthValue;
    }

    private double parseWieght() {
        double weightValue = 0;
        String weightString = mWeightInput.getText().toString();
        if (weightString.length() > 0) {

            weightValue = Double.parseDouble(weightString);
        }

        return weightValue;
    }

    private String getBmiValue() {
        String nomral = getResources().getString(R.string.bmi_summary_normal);
        String overweight = getResources().getString(R.string.bmi_summary_overweight);
        String underweight = getResources().getString(R.string.bmi_summary_underweight);
        double length = parseLength();
        double weight = parseWieght();
        DecimalFormat dFormat = new DecimalFormat("0.0");

        mBmiValue = ((weight) / (length * length)) * 10000;

        if (mBmiValue < 18.5) {
            mSummary.setText(underweight);
        } else if (mBmiValue >= 18.5 && mBmiValue <= 25)
            mSummary.setText(nomral);
        else if (mBmiValue > 25) {
            mSummary.setText(overweight);
        }


        String bmi = dFormat.format(mBmiValue);
        isCalulated = true;

        return bmi;
    }


    private boolean checkLenght() {
        if (parseLength() > 250 || parseLength() < 45) {
            return true;
        }
        return false;
    }

    private boolean checkWeight() {
        if (parseWieght() > 300 || parseWieght() <= 10) {
            return true;
        }
        return false;

    }


    private void reset() {
        mBmiValue = 0;
        mSummary.setText("");
        mBmiValueLabel.setText("");
        mLenghtInput.setText("");
        mWeightInput.setText("");


    }

    private void changeColor() {
        if (!mCalcButton.isEnabled()) {
            mCalcButton.setBackgroundColor(Color.GRAY);
            mCalcButton.setTextColor(Color.WHITE);
        } else

            mCalcButton.setBackgroundColor(getColor(R.color.colorPrimary));
    }


}

