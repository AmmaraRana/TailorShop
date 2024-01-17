package com.example.adminpanel.Tailor.TailorAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adminpanel.R;
import com.example.adminpanel.Tailor.TailorModel.FeedbackModel;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {
    List<FeedbackModel> list;
    Context context;

    public FeedbackAdapter(List<FeedbackModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FeedbackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feedback_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackAdapter.ViewHolder holder, int position) {
        FeedbackModel feedbackModel = list.get(position);

        // Set values for the views in the ViewHolder
        holder.userNameTextView.setText(feedbackModel.getCustomer());
        holder.ratingBar.setRating(feedbackModel.getRating());
        holder.feedbackTextView.setText(feedbackModel.getFeedbackText());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userNameTextView;
        public RatingBar ratingBar;
        public TextView feedbackTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userNameTextView = itemView.findViewById(R.id.feedbackUserName);
            ratingBar = itemView.findViewById(R.id.feedbackRatingBar);
            feedbackTextView = itemView.findViewById(R.id.feedbackText);

        }
    }
}
