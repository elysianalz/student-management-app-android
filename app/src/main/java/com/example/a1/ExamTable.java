package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
// view all exams for specific student
public class ExamTable extends AppCompatActivity {
    ListView examList;
    ExamManager em;
    ArrayList<String> exams;
    Context context;
    String[] deleted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_table);
        context = this;
        examList = (ListView)findViewById(R.id.exams);
        em = new ExamManager(this);
        em.openReadable();
        exams = em.getRows(getIntent().getStringExtra("Id"));
        CustomAdapter adapter = new CustomAdapter(this, exams, R.layout.examrow, "exam");
        examList.setAdapter(adapter);
        em.close();
        deleted = new String[exams.size()];
        examList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)examList.getAdapter().getItem(i);
                String[] values = item.split(", ");
                CheckBox check = view.findViewById(R.id.checkBox);
                if(check.isChecked())
                {
                    check.setChecked(false);
                    deleted[i] = null;

                } else
                {
                    check.setChecked(true);
                    deleted[i] = values[0];
                    System.out.println(values[0]);
                }
            }
        });
    }

    public void addExam(View view)
    {
        Intent intent = new Intent(this, ExamForm.class);
        intent.putExtra("Id", getIntent().getStringExtra("Id"));
        startActivity(intent);
    }

    public void deleteExam(View view)
    {
        em = new ExamManager(this);
        em.deleteSelected(deleted, Integer.parseInt(getIntent().getStringExtra("Id")));
        em.close();
        Toast.makeText(this, "Successfully deleted exams", Toast.LENGTH_SHORT).show();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void backToStudent(View view)
    {
        finish();
    }
}