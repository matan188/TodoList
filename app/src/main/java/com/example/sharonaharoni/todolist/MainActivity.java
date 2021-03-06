package com.example.sharonaharoni.todolist;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
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
    private List<Reminder> reminderList;
    private  ReminderRecyclerAdapter adapter;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String reminderDescription;
    private  static String REMINDERS_KEY= "REMINDERS";
    private DBHelper db = new DBHelper(this);


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
                // Prepare Dialog to add new reminders
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                View dView = getLayoutInflater().inflate(R.layout.dialog_new_reminder, null);
                final EditText etDescription = (EditText) dView.findViewById(R.id.etDialogReminderDescription);

                // Positive button adds new reminder and then offers a DatePicker for the reminder
                builder.setPositiveButton(R.string.dialog_add, new DialogInterface.OnClickListener() {
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
                // Negative button closes the dialog without changing the list
                builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
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


        /* Check if there's a saved instance */
        if (savedInstanceState == null || !savedInstanceState.containsKey(REMINDERS_KEY)) {
            reminderList = db.allRemindrs();
        } else {
            reminderList = savedInstanceState.getParcelableArrayList(REMINDERS_KEY);
        }



        LinearLayoutManager llm = new LinearLayoutManager(this.getBaseContext());

        rvTodoList.setHasFixedSize(true);
        rvTodoList.setLayoutManager(llm);

        adapter = new ReminderRecyclerAdapter(MainActivity.this, reminderList, db);
        rvTodoList.setAdapter(adapter);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if (view.isShown()){
                    Reminder reminder = new Reminder(reminderDescription, year, month+1, dayOfMonth);
                    reminderList.add(reminder);
                    db.insertReminder(reminder);
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(REMINDERS_KEY, (ArrayList<? extends Parcelable>) reminderList);
        super.onSaveInstanceState(outState);
    }
}
