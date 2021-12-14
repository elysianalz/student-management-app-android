package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.Instant;
// form for adding an exam to the exam data base
public class ExamForm extends AppCompatActivity {
    ExamManager em;
    EditText examName;
    EditText examLocation;
    EditText examDate;
    EditText examTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_form);
        examName = (EditText)findViewById(R.id.examName);
        examLocation = (EditText)findViewById(R.id.examLocation);
        examDate = (EditText)findViewById(R.id.examDate);
        examTime = (EditText)findViewById(R.id.examTime);
        em = new ExamManager(this);
    }

    public void createExam(View view)
    {
        int id = Integer.parseInt(getIntent().getStringExtra("Id"));
        String name = examName.getText().toString();
        String location = examLocation.getText().toString();
        String date = examDate.getText().toString();
        String time = examTime.getText().toString();
        em = new ExamManager(this);
        em.addRow(id, name, date, time, location);
        em.close();
        Toast.makeText(this,"exam successfully added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ExamTable.class);
        startActivity(intent);
    }

    public void backToExams(View view)
    {
        finish();
    }
}