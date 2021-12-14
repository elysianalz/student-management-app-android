package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;
// form for adding student to student database
public class StudentForm extends AppCompatActivity {
    DataBaseManager dbm;
    EditText id;
    EditText firstName;
    EditText lastName;
    EditText age;
    EditText address;
    RadioButton male;
    RadioButton female;
    EditText course;
    Button submit;
    Button edit;
    RadioButton happy;
    RadioButton sad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_form);
        id = (EditText)findViewById(R.id.studentId);
        firstName = (EditText)findViewById(R.id.firstName);
        lastName = (EditText)findViewById(R.id.lastName);
        age = (EditText)findViewById(R.id.age);
        address = (EditText)findViewById(R.id.address);
        male = (RadioButton)findViewById(R.id.male);
        female = (RadioButton)findViewById(R.id.female);
        course = (EditText)findViewById(R.id.course);
        submit = (Button)findViewById(R.id.submit);
        edit = (Button)findViewById(R.id.edit);
        happy = (RadioButton)findViewById(R.id.happy);
        sad = (RadioButton)findViewById(R.id.sad);
        if(getIntent().getStringExtra("Id") != null)
        {
            id.setText(getIntent().getStringExtra("Id"));
            firstName.setText(getIntent().getStringExtra("FirstName"));
            lastName.setText(getIntent().getStringExtra("LastName"));
            age.setText(getIntent().getStringExtra("Age"));
            address.setText(getIntent().getStringExtra("Address"));
            System.out.println(getIntent().getStringExtra("Gender"));
            String gender = getIntent().getStringExtra("Gender");
            String image = getIntent().getStringExtra("Image");
            if(gender.equals("Male"))
            {
                male.setChecked(true);
            }
            else
            {
                female.setChecked(true);
            }
            if(image.equals("HAPPY"))
            {
                happy.setChecked(true);
            } else {
                sad.setChecked(true);
            }
            course.setText(getIntent().getStringExtra("Course"));
            submit.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else
        {
            submit.setVisibility(View.VISIBLE);
            edit.setVisibility(View.INVISIBLE);
        }
    }

    public void updateStudent(View view)
    {
        System.out.println("clicked");
        Integer sId = parseInt(id.getText().toString());
        String sFirstName = firstName.getText().toString();
        String sLastName = lastName.getText().toString();
        String sAge = age.getText().toString();
        String sAddress = address.getText().toString();
        String sGender = "";
        if(male.isChecked())
        {
            sGender = "Male";
        } else if (female.isChecked())
        {
            sGender = "Female";
        }
        String sImage = "";
        if(happy.isChecked())
        {
            sImage = "HAPPY";
        } else if (sad.isChecked())
        {
            sImage = "SAD";
        }
        String sCourse = course.getText().toString();
        dbm = new DataBaseManager(this);
        dbm.updateRow(sId, sFirstName, sLastName, sAge, sGender, sCourse, sAddress, sImage);
        dbm.close();
        Toast.makeText(this, "record updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
    }

    public void submit(View view)
    {
        Integer sId = parseInt(id.getText().toString());
        String sFirstName = firstName.getText().toString();
        String sLastName = lastName.getText().toString();
        Integer sAge = parseInt(age.getText().toString());
        String sAddress = address.getText().toString();
        String sGender = "";
        if(male.isChecked())
        {
            sGender = "Male";
        } else if (female.isChecked())
        {
            sGender = "Female";
        }
        String sImage = "";
        if(happy.isChecked())
        {
            sImage = "HAPPY";
        } else if (sad.isChecked())
        {
            sImage = "SAD";
        }
        String sCourse = course.getText().toString();
        dbm = new DataBaseManager(this);
        dbm.addRow(sId,sFirstName,sLastName,sAge,sGender,sAddress,sCourse, sImage);
        dbm.close();
        Toast.makeText(this, "record added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
    }
}