package com.example.loginapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BMIActivity extends AppCompatActivity {
    private Button userBMICal;
    private EditText userWeight;
    private  EditText userHeight;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_b_m_i );

        userBMICal = (Button)findViewById( R.id.btn );
        userHeight = (EditText)findViewById( R.id.edHeight );
        userWeight = (EditText)findViewById( R.id.edWeight);
        result = (TextView)findViewById( R.id.result );

        userBMICal.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result();
            }
        } );

    }
    private void result(){
        String height = userHeight.getText().toString();
        String weight = userWeight.getText().toString();

        if(height != null && !"".equals(height)&&weight != null && !"".equals(weight)){
            float heightValue = Float.parseFloat(height)/100;
            float weightValue = Float.parseFloat(weight);

            float bmi =weightValue/(heightValue*heightValue);
            displayBMI(bmi);
        }
    }
    private  void  displayBMI(float bmi){
        String bmiLabel = "";

        if(Float.compare(bmi, 15f) <=0){
            bmiLabel = "Very severely underweight";
        }else  if(Float.compare( bmi, 15f ) >0 && Float.compare(bmi, 16f) <=0){
            bmiLabel = "severely underweight";
        }else  if(Float.compare( bmi, 16f ) >0 && Float.compare(bmi, 18.5f) <=0){
            bmiLabel = "underweight";
        }else  if(Float.compare( bmi, 18.5f ) >0 && Float.compare(bmi, 25f) <=0){
            bmiLabel = "normal";
        }else  if(Float.compare( bmi, 25f ) >0 && Float.compare(bmi, 30f) <=0){
            bmiLabel = "overweight";
        }else  if(Float.compare( bmi, 30f ) >0 && Float.compare(bmi, 35f) <=0){
            bmiLabel = "obese class I";
        }else  if(Float.compare( bmi, 35f ) >0 && Float.compare(bmi, 40f) <=0){
            bmiLabel = "obese class II";
        }else {
            bmiLabel = "obese class III";
        }

        bmiLabel = bmi+"\n"+bmiLabel;
        result.setText( bmiLabel );

    }
}