package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<ListData> listdata;
    private Context context;

    ListAdapter(Context context, List<ListData> listdata){
        this.listdata = listdata;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listitem = layoutInflater.inflate(R.layout.list_view,parent,false);
        ViewHolder viewHolder = new ViewHolder(listitem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ListAdapter.ViewHolder holder, int position) {
        ListData listData = listdata.get(position);
        holder.lapview.setText(Integer.toString(listData.getLap()));
        holder.stepview.setText(Integer.toString(listData.getStepcount()));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public void updateData(){

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView lapview;
        public TextView stepview;
        public RelativeLayout relativeLayout;

        public ViewHolder( View itemView) {
            super(itemView);
            this.lapview = itemView.findViewById(R.id.textView2);
            this.stepview = itemView.findViewById(R.id.textView3);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
