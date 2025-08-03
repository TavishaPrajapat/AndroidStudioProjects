package com.example.mytestappone;

import android.os.Bundle;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText weightEditText, heightEditText;
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Finding views by their ID
        weightEditText = findViewById(R.id.editText1); // EditText for weight in kilos
        heightEditText = findViewById(R.id.editText2); // EditText for height in meters
        btn = findViewById(R.id.btn); // Button for calculating BMI
        textView = findViewById(R.id.textview); // TextView to display the result

        // Setting up the button's onClick listener
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting user input for weight and height
                String weightInput = weightEditText.getText().toString();
                String heightInput = heightEditText.getText().toString();

                // Checking if the input fields are not empty
                if (!weightInput.isEmpty() && !heightInput.isEmpty()) {
                    try {
                        // Parsing the user input into doubles
                        double weight = Double.parseDouble(weightInput);
                        double height = Double.parseDouble(heightInput);

                        // Calculating BMI
                        double bmi = calculateBMI(weight, height);

                        // Displaying the BMI result
                        textView.setText(String.format("Your BMI is: %.2f", bmi));
                    } catch (NumberFormatException e) {
                        textView.setText("Please enter valid numbers");
                    }
                } else {
                    textView.setText("Please fill in both fields");
                }
            }
        });
    }

    // Method to calculate BMI using the formula: BMI = weight / (height^2)
    public double calculateBMI(double weight, double height) {
        return weight / (height * height);
    }
}