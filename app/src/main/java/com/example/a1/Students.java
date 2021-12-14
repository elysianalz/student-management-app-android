package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
// view all students in student database
public class Students extends AppCompatActivity {
    ListView list;
    DataBaseManager dbm;
    ArrayList<String> students;
    Intent intent;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        list = (ListView)findViewById(R.id.studentList);
        dbm = new DataBaseManager(this);
        dbm.openReadable();
        students = dbm.getRows();
        CustomAdapter adapter = new CustomAdapter(this, students, R.layout.rowlayout, "students");
        list.setAdapter(adapter);
        dbm.close();
        context = this;
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)list.getAdapter().getItem(i);
                String[] values = item.split(", ");
                String id = values[0];
                intent = new Intent(context, Student.class);
                intent.putExtra("studentId", id);
                startActivity(intent);
            }
        });

    }

    public void home(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void addStudent(View view)
    {
        Intent intent = new Intent(this, StudentForm.class);
        startActivity(intent);
        finish();
    }
}