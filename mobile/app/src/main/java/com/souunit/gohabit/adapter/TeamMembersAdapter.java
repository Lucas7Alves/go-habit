package com.souunit.gohabit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.souunit.gohabit.R;
import com.souunit.gohabit.model.TeamMember;

import java.util.List;

public class TeamMembersAdapter extends RecyclerView.Adapter<TeamMembersAdapter.MemberViewHolder> {

    private List<TeamMember> members;

    public TeamMembersAdapter(List<TeamMember> members) {
        this.members = members;
    }

    public void updateMembers(List<TeamMember> newMembers) {
        this.members = newMembers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        TeamMember member = members.get(position);
        holder.name.setText(member.getName());
        holder.position.setText(member.getPosition());
        holder.points.setText(String.valueOf(member.getPoints()));
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {
        TextView name, position, points;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.member_name);
            position = itemView.findViewById(R.id.member_position);
            points = itemView.findViewById(R.id.member_points);
        }
    }
}