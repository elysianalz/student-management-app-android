package com.example.a1;

import android.content.Context;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;
// custome adapter for custom lists
public class CustomAdapter extends ArrayAdapter<String> {

    private ArrayList values;
    private Context context;
    private int layout;
    private String which;
    public CustomAdapter(Context context, ArrayList<String> values, int layout, String which)
    {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
        this.layout = layout;
        this.which = which;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(layout, parent,false);
        String[] row = values.get(position).toString().split(", ");
        // adapter option for listing students
        if(which == "students")
        {
            TextView id = (TextView) rowView.findViewById(R.id.studentId);
            TextView firstName = (TextView)rowView.findViewById(R.id.firstName);
            TextView lastName = (TextView)rowView.findViewById(R.id.lastName);
            id.setText(row[0]);
            firstName.setText(row[1]);
            lastName.setText(row[2]);
        }
        // adpater option for listing todos
        else if (which == "todo")
        {
            TextView task = (TextView)rowView.findViewById(R.id.task);
            TextView location = (TextView)rowView.findViewById(R.id.location);
            CheckBox checkBox = (CheckBox)rowView.findViewById(R.id.checkbox);
            task.setText(row[0]);
            location.setText(row[1]);
            if(row[2].equals("T"))
            {
                checkBox.setChecked(true);
            }
        }
        // adapter option for listing exams
        else if (which == "exam")
        {
            ZoneId z = ZoneId.of("Australia/Sydney");
            DateTimeFormatter f = DateTimeFormatter.ofPattern( "dd/MM/uu HH:mm" );

            String instant = Instant.now().atZone(z).format(f);
            LocalDateTime ldt = LocalDateTime.parse(instant, f);
            LocalDateTime examDate = LocalDateTime.parse(row[2] + " " + row[3], f);

            TextView name = (TextView)rowView.findViewById(R.id.exName);
            TextView date = (TextView)rowView.findViewById(R.id.exDate);
            TextView time = (TextView)rowView.findViewById(R.id.exTime);
            TextView location = (TextView)rowView.findViewById(R.id.exLocation);
            TextView status = (TextView)rowView.findViewById(R.id.status);

            name.setText(row[0]);
            date.setText(row[2]);
            time.setText(row[3]);
            location.setText(row[1]);

            if(ldt.compareTo(examDate) > 0)
            {
                status.setText("completed");
            }
            else
            {
                status.setText("not completed");
            }
        }
        return rowView;
    }
}
