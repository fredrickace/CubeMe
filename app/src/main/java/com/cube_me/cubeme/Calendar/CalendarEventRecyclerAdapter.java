package com.cube_me.cubeme.Calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cube_me.cubeme.R;

import java.util.List;

/**
 * Created by FredrickCyril on 9/3/16.
 */
public class CalendarEventRecyclerAdapter extends RecyclerView.Adapter<CalendarEventRecyclerAdapter.EventViewHolder> {

    LayoutInflater inflater;
    List<CalendarEvent> data;
    Context context;

    public CalendarEventRecyclerAdapter(List<CalendarEvent> data, Context context) {
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.calendar_dailyevent_row,parent,false);
        EventViewHolder holder = new EventViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        CalendarEvent current = data.get(position);
        holder.eventDate.setText(current.eventDate.toString());
        holder.eventTitle.setText(current.eventTitle);
        holder.eventClientName.setText(current.eventClientName);
        holder.eventDescription.setText(current.eventDescription);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{

        TextView eventDate;
        TextView eventClientName;
        TextView eventTitle;
        TextView eventDescription;
        TextView eventReport;

        public EventViewHolder(View itemView) {
            super(itemView);

            eventDate = (TextView) itemView.findViewById(R.id.calendarEventRow_eventDate);
            eventClientName = (TextView) itemView.findViewById(R.id.calendarEventRow_eventClientName);
            eventTitle = (TextView)itemView.findViewById(R.id.calendarEventRow_eventTitle);
            eventDescription = (TextView)itemView.findViewById(R.id.calendarEventRow_eventDescription);
            eventReport = (TextView)itemView.findViewById(R.id.calendarEventRow_eventReport);
        }
    }
}
