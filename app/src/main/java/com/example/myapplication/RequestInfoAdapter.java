package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RequestInfoAdapter extends  RecyclerView.Adapter<RequestInfoAdapter.ViewHolder>{
    RequestInfo[] requestInfo;
    Context context;
    public RequestInfoAdapter(RequestInfo[] requestInfo, Requests activity){
        this.requestInfo = requestInfo;
        this.context = activity;
    }

    @NonNull
    public RequestInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_request_item, parent, false);
        return new RequestInfoAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull RequestInfoAdapter.ViewHolder holder, int position) {
        final RequestInfo requestInfoList = this.requestInfo[position];
        holder.teamName.setText(requestInfoList.getTeamName());
        holder.applyToTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                SharedPreferences teamInfo = context.getSharedPreferences("teamInfo",
                        Context.MODE_PRIVATE);
                SharedPreferences sharedPreferences = context.getSharedPreferences("requestData",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tournament_id", requestInfoList.getTournamentId().toString());
                editor.putString("teamName", holder.teamName.getText().toString());
                editor.putInt("team_id", teamInfo.getInt("id", 0));
                editor.apply();
                holder.linearLayout.removeViewAt(0);
                passDataApply(context);
            }
        });
    }

    private void passDataApply(Context context) {
        Intent intent = new Intent(context, ApplyProcess.class);
        context.startActivity(intent);
    }

    public int getItemCount() {
        return requestInfo.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamName;
        Button applyToTournament;
        LinearLayout linearLayout;
        @SuppressLint("ResourceType")
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear);
            teamName = itemView.findViewById(R.id.teamName);
            applyToTournament = itemView.findViewById(R.id.applyToTournament);
        }
    }
}
