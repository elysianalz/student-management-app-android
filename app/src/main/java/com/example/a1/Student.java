package com.example.a1;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
// viewing information of specific student
public class Student extends AppCompatActivity {
    DataBaseManager dbm;
    ArrayList<String> student;
    String[] columns = new String[] {"StudentID", "FirstName", "LastName", "Age", "Gender", "Address", "Course", "Image"};
    String[] studentInfo;
    TextView sId;
    TextView sFirstName;
    TextView sLastName;
    TextView sGender;
    TextView sAge;
    TextView sCourse;
    TextView sAddress;
    ImageView sImage;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        id = getIntent().getStringExtra("studentId");
        sId = (TextView)findViewById(R.id.sId);
        sFirstName = (TextView)findViewById(R.id.sFirstName);
        sLastName = (TextView)findViewById(R.id.sLastName);
        sGender = (TextView)findViewById(R.id.sGender);
        sAge = (TextView)findViewById(R.id.sAge);
        sCourse = (TextView)findViewById(R.id.sCourse);
        sAddress = (TextView)findViewById(R.id.sAddress);
        sImage = (ImageView)findViewById(R.id.sImage);
        dbm = new DataBaseManager(this);
        dbm.openReadable();
        student = dbm.getRows(columns, id);
        dbm.close();
        studentInfo = student.get(0).split(", ");
        sId.setText(studentInfo[0]);
        sFirstName.setText(studentInfo[1]);
        sLastName.setText(studentInfo[2]);
        sAge.setText(studentInfo[3]);
        sGender.setText(studentInfo[4]);
        sAddress.setText(studentInfo[5]);
        sCourse.setText((studentInfo[6]));
        if(studentInfo[7].equals("HAPPY"))
        {
            sImage.setImageResource(R.drawable.happyface);
        } else
        {
            sImage.setImageResource(R.drawable.sadface);
        }
    }

    public void backStudents(View view)
    {
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
    }

    public void deleteStudent(View view)
    {
        dbm = new DataBaseManager(this);
        dbm.deleteRow(studentInfo[0]);
        dbm.close();
        Toast.makeText(this, "record successfully deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
        finish();
    }

    public void editStudent(View view)
    {
        Intent intent = new Intent(this, StudentForm.class);
        intent.putExtra("Id", studentInfo[0]);
        intent.putExtra("FirstName", studentInfo[1]);
        intent.putExtra("LastName", studentInfo[2]);
        intent.putExtra("Age", studentInfo[3]);
        intent.putExtra("Gender", studentInfo[4]);
        intent.putExtra("Address", studentInfo[5]);
        intent.putExtra("Course", studentInfo[6]);
        intent.putExtra("Image", studentInfo[7]);
        startActivity(intent);
    }

    public void exams(View view)
    {
        Intent intent = new Intent(this, ExamTable.class);
        intent.putExtra("Id", studentInfo[0]);
        startActivity(intent);
    }
}