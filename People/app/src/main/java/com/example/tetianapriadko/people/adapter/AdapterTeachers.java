package com.example.tetianapriadko.people.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tetianapriadko.people.R;
import com.example.tetianapriadko.people.structure.Teacher;

import java.util.List;

public class AdapterTeachers extends RecyclerView.Adapter<AdapterTeachers.ViewHolder> {

    private List<Teacher> teachers;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public AdapterTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater
                = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        return new ViewHolder(inflater.inflate(R.layout.item_teacher, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.teacherName.setText(teachers.get(position).getName());
        holder.teacherSurname.setText(teachers.get(position).getSurname());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(holder.teacherName,
                        holder.getAdapterPosition(), teachers.get(position));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClickListener.itemLongClicked(holder.teacherName,
                        holder.getAdapterPosition(), teachers.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachers != null && !teachers.isEmpty() ? teachers.size() : 0;
    }

    public void setData(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherName;
        public TextView teacherSurname;


        ViewHolder(View itemView) {
            super(itemView);
            teacherName = ((TextView) itemView.findViewById(R.id.textView_name));
            teacherSurname = ((TextView) itemView.findViewById(R.id.textView_surname));
        }
    }

    public interface OnItemClickListener {
        void itemClicked(View view, int position, Teacher teacher);
    }

    public interface OnItemLongClickListener {
        void itemLongClicked(View view, int position, Teacher teacher);
    }
}
