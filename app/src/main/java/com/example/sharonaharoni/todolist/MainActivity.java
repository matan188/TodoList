package com.example.sharonaharoni.todolist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.rvTodoList)
    RecyclerView rvTodoList;
    // TODO dialog, FAB add button, Task(DatePicker and EditText), Positive\Negative to confirm or cancel reminder
    // Popup for when reminder is clicked, If reminder is started with call and then number then should start dialer with the
    // the number to call
    private List<Reminder> reminderList;
    private  ReminderRecyclerAdapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String reminderDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dView = getLayoutInflater().inflate(R.layout.dialog_new_reminder, null);
                final EditText etDescription = (EditText) dView.findViewById(R.id.etDialogReminderDescription);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        reminderDescription = etDescription.getText().toString();
                        Calendar cal = Calendar.getInstance();
                        int year = cal.get(Calendar.YEAR);
                        int month = cal.get(Calendar.MONTH);
                        int day = cal.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog dateDialog = new DatePickerDialog(
                                MainActivity.this,
                                android.R.style.Theme_Holo_Light_Dialog,
                                mDateSetListener,
                                year, month, day);

                        dateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dateDialog.show();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setView(dView);
                AlertDialog descriptionDialog = builder.create();
                descriptionDialog.show();



            }
        });


        initializeData();
        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());

        rvTodoList.setHasFixedSize(true);
        rvTodoList.setLayoutManager(llm);

        adapter = new ReminderRecyclerAdapter(this.getBaseContext(), reminderList);
        rvTodoList.setAdapter(adapter);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (view.isShown()){
                    Reminder reminder = new Reminder(reminderDescription, year, month, dayOfMonth);
                    reminderList.add(reminder);
                    adapter.notifyItemInserted(reminderList.size()-1);
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeData() {
        this.reminderList = new ArrayList<>();
        reminderList.add(new Reminder("See movie", 2018, 1, 10));
        reminderList.add(new Reminder("See Friend", 2018, 4, 15));
        reminderList.add(new Reminder("Homework", 2017, 5, 23));
        reminderList.add(new Reminder("Vacation", 2018, 11, 1));
        reminderList.add(new Reminder("Check thing", 2018, 1, 10));
    }
}
