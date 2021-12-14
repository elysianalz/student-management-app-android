package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
// home screen
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void students(View view)
    {
        Intent intent = new Intent(this, Students.class);
        startActivity(intent);
    }

    public void todo(View view)
    {
        Intent intent = new Intent(this, TodoList.class);
        startActivity(intent);
    }
}