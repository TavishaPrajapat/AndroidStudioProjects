package com.tavisha.assig2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Student770 extends AppCompatActivity {

    private EditText studentNameInput;
    private EditText studentIDInput;
    private EditText courseInput;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing views
        studentNameInput = findViewById(R.id.studentname770);
        studentIDInput = findViewById(R.id.studentid770);
        courseInput = findViewById(R.id.studentcourse770);
        submitButton = findViewById(R.id.submit770);

        // Setting up the Submit button click listener
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Collecting the input data
                String studentName = studentNameInput.getText().toString();
                String studentID = studentIDInput.getText().toString();
                String course = courseInput.getText().toString();

                // Creating a Student770 object
                Student student = new Student(studentName, studentID, course);

                // Displaying the student details in a Toast
                Toast.makeText(Student770.this,
                        "Student Name: " + student.getStudentName() + "\n" +
                                "Student ID: " + student.getStudentID() + "\n" +
                                "Course: " + student.getCourse(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
