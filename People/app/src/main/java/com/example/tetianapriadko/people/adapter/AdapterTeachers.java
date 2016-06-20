package com.example.tetianapriadko.people.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.tetianapriadko.people.R;
import com.example.tetianapriadko.people.constants.BACK_SETTINGS;
import com.example.tetianapriadko.people.structure.Teacher;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterTeachers extends RecyclerView.Adapter<AdapterTeachers.ViewHolder> {

    private List<Teacher> teachers;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;
    public AQuery aQuery;
    public Context context;
    public CircleImageView teacherImageView;

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

        aQuery = new AQuery(context);
        aQuery.id(teacherImageView).image(
                String.format("%s%s%s%s",
                        BACK_SETTINGS.SERVER_URL,
                        BACK_SETTINGS.FILES,
                        BACK_SETTINGS.TEACHER_AVATAR_STORE_URL,
                        teachers.get(position).getAvatarUrl()),
                false,
                false,
                0,
                R.drawable.icon);


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

    public List<Teacher> getTeachers() {
        return teachers;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacherName;
        public TextView teacherSurname;
        ViewHolder(View itemView) {
            super(itemView);
            teacherName = ((TextView) itemView.findViewById(R.id.textView_name));
            teacherSurname = ((TextView) itemView.findViewById(R.id.textView_surname));
            teacherImageView = (CircleImageView) itemView.findViewById(R.id.imageView_teacher);
        }
    }

    public interface OnItemClickListener {
        void itemClicked(View view, int position, Teacher teacher);
    }

    public interface OnItemLongClickListener {
        void itemLongClicked(View view, int position, Teacher teacher);
    }
}
