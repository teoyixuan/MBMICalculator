package sg.edu.rp.c346.mbmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight, etHeight;
    Button btnCalculate, btnReset;
    TextView tvDate, tvBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvDate = findViewById(R.id.textViewCalculateDate);
        tvBMI = findViewById(R.id.textViewCalculateBMI);
        etWeight.requestFocus();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);

                tvDate.setText("Last Calculate Date: " + datetime);
                float weight = Float.parseFloat(etWeight.getText().toString());
                float height = Float.parseFloat(etHeight.getText().toString());

                float bmi = weight / (height * height);
                String result = String.format("Last Calculate BMI: %.3f",bmi);

                if(bmi < 18.5){
                    result += "\n you are underweight";
                }
                else if(bmi < 24.9){
                    result += "\n Your BMI is normal";
                }
                else if(bmi < 29.9){
                    result += "\n You are overweight";
                }
                else{
                    result += "\n you are obese";
                }


                tvBMI.setText(result);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvBMI.setText("Last Calculate BMI: ");
                tvDate.setText("Last Calculate Date: ");
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();

        // Step 1a: Get hte user input from the EditText and store it in a variable
        String date = tvDate.getText().toString();
        String bmi = tvBMI.getText().toString();


        // Step 1b: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 1c: Obtain an instance of the SharedPreference Editor for update later
        SharedPreferences.Editor prefEdit = prefs.edit();

        // Step 1d: Add the key-value pair
        prefEdit.putString("date",date);
        prefEdit.putString("bmi",bmi);

        //Step 1d: Call commit() to save the changes into SharedPreferences
        prefEdit.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Step 2c: Retrieve the saved data from the SharedPreference object
        String msg = prefs.getString("date", "Last Calculate Date: ");
        String msg1 = prefs.getString("bmi", "Last Calculate BMI: ");

        tvBMI.setText(msg1);
        tvDate.setText(msg);
    }
}
