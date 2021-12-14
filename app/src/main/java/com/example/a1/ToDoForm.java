package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
// form for adding a todo to the todo database
public class ToDoForm extends AppCompatActivity {
    ToDoManager tdm;
    EditText task;
    EditText location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);
        task = (EditText)findViewById(R.id.task);
        location = (EditText)findViewById(R.id.location);
    }

    public void back(View view)
    {
        Intent intent = new Intent(this, TodoList.class);
        startActivity(intent);
    }

    public void create(View view)
    {
        String sTask = task.getText().toString();
        String sLocation = location.getText().toString();
        tdm = new ToDoManager(this);
        tdm.addRow(sTask, sLocation, "F");
        tdm.close();
        Toast.makeText(this,"to-do added successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TodoList.class);
        startActivity(intent);
    }
}