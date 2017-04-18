package com.example.sharonaharoni.todolist;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sharonaharoni on 18/04/2017.
 */

public class ReminderRecyclerAdapter extends RecyclerView.Adapter<ReminderRecyclerAdapter.ViewHolder>{

    private List<Reminder> todoList;
    Context context;

    public ReminderRecyclerAdapter(Context context, List<Reminder> todoList) {
        this.context = context;
        this.todoList = todoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reminder_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Reminder reminder = this.todoList.get(position);

        holder.tvReminderDescription.setText(reminder.getDescription());
        holder.tvReminderDate.setText(reminder.getDay() + "/" + reminder.getMonth() + "/" + reminder.getYear());

    }

    @Override
    public int getItemCount() {
        return this.todoList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvReminderDescription)
        TextView tvReminderDescription;
        @BindView(R.id.tvReminderDate)
        TextView tvReminderDate;
        @BindView(R.id.rlReminderItem)
        RelativeLayout rlReminderItem;
        @BindView(R.id.cvReminder)
        CardView cvReminder;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
