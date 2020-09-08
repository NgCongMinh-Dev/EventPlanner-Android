package com.htw.project.eventplanner.ViewController.Element;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.htw.project.eventplanner.Model.User;
import com.htw.project.eventplanner.R;

import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder> {

    private LayoutInflater inflater;

    private List<User> participants;

    public ParticipantAdapter(Context context, List<User> participants) {
        this.inflater = LayoutInflater.from(context);
        this.participants = participants;
    }

    @NonNull
    @Override
    public ParticipantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.element_participant, parent, false);
        return new ParticipantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ParticipantViewHolder holder, int position) {
        User participant = participants.get(position);

        holder.firstNameTextView.setText(participant.getFirstName());
        holder.lastNameTextView.setText(participant.getLastName());
        holder.usernameTextView.setText(participant.getUsername());
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ParticipantViewHolder extends RecyclerView.ViewHolder {

        TextView firstNameTextView;

        TextView lastNameTextView;

        TextView usernameTextView;

        public ParticipantViewHolder(@NonNull View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.element_participant_first_name);
            lastNameTextView = itemView.findViewById(R.id.element_participant_last_name);
            usernameTextView = itemView.findViewById(R.id.element_participant_username);
        }

    }

}
