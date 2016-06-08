package com.example.tetianapriadko.people.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.tetianapriadko.people.R;
import com.example.tetianapriadko.people.structure.Student;

import java.util.List;

public class AdapterStudents extends RecyclerView.Adapter<AdapterStudents.ViewHolder> {

    private List<Student> students;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public AdapterStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater
                = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        return new ViewHolder(inflater.inflate(R.layout.item_student, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.studentName.setText(students.get(position).getName());
        holder.studentSurname.setText(students.get(position).getSurname());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClicked(holder.studentName,
                        holder.getAdapterPosition(), students.get(position));
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemLongClickListener.itemLongClicked(holder.studentName,
                        holder.getAdapterPosition(), students.get(position));
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return students != null && !students.isEmpty() ? students.size() : 0;
    }

    public void setData(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentName;
        public TextView studentSurname;
        ViewHolder(View itemView) {
            super(itemView);
            studentName = ((TextView) itemView.findViewById(R.id.textView_st_name));
            studentSurname = ((TextView) itemView.findViewById(R.id.textView_st_surname));
        }
    }

    public interface OnItemClickListener {
        void itemClicked(View view, int position, Student student);
    }

    public interface OnItemLongClickListener {
        void itemLongClicked(View view, int position, Student student);
    }
}
