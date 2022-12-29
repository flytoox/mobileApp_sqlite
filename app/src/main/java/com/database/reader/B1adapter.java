package com.database.reader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kotlin.jvm.internal.Lambda;

public class B1adapter extends RecyclerView.Adapter<B1adapter.B1ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private ArrayList<B1Model> B1ModelArrayList;

    public B1adapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class B1ViewHolder extends RecyclerView.ViewHolder {
        private TextView TypeTask;
        private TextView Name;
        private TextView idTask;
        private TextView Phone;
        private TextView Task;
        private TextView Place;
        private TextView end_date;
        public B1ViewHolder(@NonNull View itemView) {
            super(itemView);
            TypeTask = itemView.findViewById(R.id.TypeTask);
            Name = itemView.findViewById(R.id.Name);
            idTask = itemView.findViewById(R.id.id_Task);
            Phone = itemView.findViewById(R.id.Phone);
            Task = itemView.findViewById(R.id.Task);
            Place = itemView.findViewById(R.id.Place);
            end_date = itemView.findViewById(R.id.end_date);

        }

    }


    @NonNull
    @Override
    public B1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_button1, parent, false);
        return new B1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull B1ViewHolder holder, int position) {
        if (!mCursor.moveToPosition(position)) {
            return;
        }
         String TypeTask = mCursor.getString(0);
         String Name = mCursor.getString(1);
         String idTask = mCursor.getString(2);
         String Phone = mCursor.getString(3);
         String Task = mCursor.getString(4);
         String Place = mCursor.getString(5);
         String end_date = mCursor.getString(6);

        holder.TypeTask.setText(TypeTask);
        holder.Name.setText(Name);
        holder.idTask.setText(idTask);
        holder.Phone.setText(Phone);
        holder.Task.setText(Task);
        holder.Place.setText(Place);
        holder.end_date.setText(end_date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(mContext, B1_detail.class);
                intent.putExtra("task_id", idTask);
                mContext.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }

        mCursor = newCursor;

        if (newCursor != null) {
            notifyDataSetChanged();
        }
    }
}
