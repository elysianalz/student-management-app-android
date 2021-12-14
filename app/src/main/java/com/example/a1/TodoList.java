package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;

// view all todos in todo database
public class TodoList extends AppCompatActivity {
    ListView todoList;
    ToDoManager tdm;
    ArrayList<String> todos;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);
        context = this;
        todoList = (ListView)findViewById(R.id.todoList);
        tdm = new ToDoManager(this);
        tdm.openReadable();
        todos = tdm.getRows();
        CustomAdapter adapter = new CustomAdapter(this, todos, R.layout.todorow, "todo");
        todoList.setAdapter(adapter);
        tdm.close();
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String)todoList.getAdapter().getItem(i);
                CheckBox check = (CheckBox)view.findViewById(R.id.checkbox);
                String[] values = item.split(", ");
                String task = values[0];
                String completed = values[2];
                if(completed.equals("F"))
                {
                    completed = "T";
                    check.setChecked(true);
                } else {
                    completed = "F";
                    check.setChecked(false);
                }
                tdm = new ToDoManager(context);
                tdm.updateRow(task, completed);
                tdm.close();
            }
        });
    }

    public void addTodo(View view)
    {
        Intent intent = new Intent(this, ToDoForm.class);
        startActivity(intent);
    }

    public void clear(View view)
    {
        tdm = new ToDoManager(this);
        tdm.clearTodos();
        tdm.close();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void backHome(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}