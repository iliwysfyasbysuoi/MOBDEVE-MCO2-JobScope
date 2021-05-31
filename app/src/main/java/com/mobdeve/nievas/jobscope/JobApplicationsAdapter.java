package com.mobdeve.nievas.jobscope;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class JobApplicationsAdapter extends RecyclerView.Adapter<JobApplicationsAdapter.MyViewHolder>{


    private ArrayList<UserObject> orderArrayList;
    private OnOrderListener mOnOrderListener;

    public JobApplicationsAdapter(JobDetailsActivity findJobActivity, ArrayList<UserObject> data, OnOrderListener onOrderListener){
        this.orderArrayList = data;
        this.mOnOrderListener = onOrderListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Create the ViewGroup by inflating the layout in our activity's context
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_applications, parent, false);
        //return an instance of the ViewHolder
        return new MyViewHolder(view, mOnOrderListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setName(this.orderArrayList.get(position).getName());
        holder.setEmail(this.orderArrayList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return orderArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView  name, email  ;
        OnOrderListener onOrderListener;

        public MyViewHolder(@NonNull View itemView, OnOrderListener mOnOrderListener) {
            super(itemView);

            //instantiate the views
            this.name = itemView.findViewById(R.id.tvName);
            this.email = itemView.findViewById(R.id.tvEmail);

            // sets onclick listener to the whole itemView
            itemView.setOnClickListener(this);
        }

        //setters so we can add in data
        public void setName(String name){
            this.name.setText("Name: " + name);
        }
        public void setEmail(String email){
            this.email.setText("Email: " + email);
        }


        @Override
        public void onClick(View v) {

            onOrderListener.onOrderClick(getAdapterPosition());


        }
    }

    public interface OnOrderListener{
        void onOrderClick(int position);
    }


}
