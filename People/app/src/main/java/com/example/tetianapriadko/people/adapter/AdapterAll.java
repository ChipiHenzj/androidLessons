package com.example.tetianapriadko.people.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tetianapriadko.people.R;
import com.example.tetianapriadko.people.structure.Student;
import com.example.tetianapriadko.people.structure.Teacher;

import java.util.ArrayList;
import java.util.List;

public class AdapterAll extends RecyclerView.Adapter<AdapterAll.ViewHolder> {

    private List<Object> allList;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener itemLongClickListener;

    public AdapterAll(List<Object> allList) {
        this.allList = allList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater
                = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        switch (viewType) {
            case 0:
                return new ViewHolder(inflater.inflate(R.layout.item_student, parent, false));
            case 1:
                return new ViewHolder(inflater.inflate(R.layout.item_teacher, parent, false));
            default:
                return new ViewHolder(inflater.inflate(R.layout.item_student, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (allList != null && !allList.isEmpty()) {
            if (allList.get(position) instanceof Student) {
                return 0;
            } else if (allList.get(position) instanceof Teacher) {
                return 1;
            }
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (allList.get(position) instanceof Student) {
            holder.studentName.setText(((Student) allList.get(position)).getName());
            holder.studentSurname.setText(((Student) allList.get(position)).getSurname());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClicked(holder.studentName, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.itemLongClicked(holder.studentName,
                            holder.getAdapterPosition());
                    return false;
                }
            });

        } else if (allList.get(position) instanceof Teacher) {
            holder.teacherName.setText(((Teacher) allList.get(position)).getName());
            holder.teacherSurname.setText(((Teacher) allList.get(position)).getSurname());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemClicked(holder.teacherName, holder.getAdapterPosition());
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemLongClickListener.itemLongClicked(holder.teacherName,
                            holder.getAdapterPosition());
                    return false;
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return allList != null && !allList.isEmpty() ? allList.size() : 0;
    }

    public void setData(List<?> all) {
        if (allList == null) {
            allList = new ArrayList<>();
        }
        for (int i = 0; i < all.size(); ++i) {
            allList.add(all.get(i));
        }
    }

    public List<Object> getAllList() {
        return allList;
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
        public TextView teacherName;
        public TextView teacherSurname;


        ViewHolder(View itemView) {
            super(itemView);
            studentName = ((TextView) itemView.findViewById(R.id.textView_st_name));
            studentSurname = ((TextView) itemView.findViewById(R.id.textView_st_surname));
            teacherName = ((TextView) itemView.findViewById(R.id.textView_name));
            teacherSurname = ((TextView) itemView.findViewById(R.id.textView_surname));
        }
    }

    public interface OnItemClickListener {
        void itemClicked(View view, int position);
    }

    public interface OnItemLongClickListener {
        void itemLongClicked(View view, int position);
    }
}
