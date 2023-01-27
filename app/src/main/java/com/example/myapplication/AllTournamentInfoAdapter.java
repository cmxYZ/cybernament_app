package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllTournamentInfoAdapter extends RecyclerView.Adapter<AllTournamentInfoAdapter.ViewHolder> {
    AllTournamentInfo[] tournamentInfo;
    Context context;
    public AllTournamentInfoAdapter(AllTournamentInfo[] tournamentInfo, MainActivity activity){
        this.tournamentInfo = tournamentInfo;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.all_tournament_item_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AllTournamentInfo tournamentInfoList = tournamentInfo[position];
        holder.tournamentName.setText(tournamentInfoList.getTournamentName());
        holder.tournamentInfo.setText(tournamentInfoList.getTournamentInfo());
        holder.tournamentGame.setText(tournamentInfoList.getTournamentGame());
        holder.tournamentGameMode.setText(tournamentInfoList.getTournamentGameMode());
        holder.tournamentFormat.setText(tournamentInfoList.getTournamentFormat());
        holder.tournamentStartDate.setText(tournamentInfoList.getTournamentStartDate());
        holder.tournamentTeamsCount.setText(tournamentInfoList.getTournamentTeamsCount().toString());
        holder.tournamentSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                SharedPreferences pref = context.getSharedPreferences("allTournamentInfo", context.MODE_PRIVATE);
                SharedPreferences sharedPreferences = context.getSharedPreferences("tournamentInfoToSettings",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("id", pref.getInt("id",0));
                editor.putString("name", holder.tournamentName.getText().toString());
                editor.putString("info", holder.tournamentInfo.getText().toString());
                editor.putString("game", holder.tournamentGame.getText().toString());
                editor.putString("gameMode", holder.tournamentGameMode.getText().toString());
                editor.putString("format", holder.tournamentFormat.getText().toString());
                editor.putString("startDate", holder.tournamentStartDate.getText().toString());
                editor.putString("teamsCount", holder.tournamentTeamsCount.getText().toString());
                editor.apply();
                passData(context);
            }
        });
    }
    private void passData(Context context) {
        SharedPreferences teamInfo = context.getSharedPreferences("teamInfo", Context.MODE_PRIVATE);
        int teamId = teamInfo.getInt("id", 0);
        Intent intent;
        if (teamId!=0){
            intent = new Intent(context, TournamentRegistration.class);
        }else{
            intent = new Intent(context, NoTeam.class);
        }
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return tournamentInfo.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tournamentId;
        TextView tournamentName;
        TextView tournamentInfo;
        TextView tournamentGame;
        TextView tournamentGameMode;
        TextView tournamentStartDate;
        TextView tournamentFormat;
        TextView tournamentTeamsCount;
        Button tournamentSettings;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tournamentId = itemView.findViewById(R.id.tournamentId);
            tournamentName = itemView.findViewById(R.id.tournamentName);
            tournamentInfo = itemView.findViewById(R.id.tournamentInfo);
            tournamentGame = itemView.findViewById(R.id.tournamentGame);
            tournamentGameMode = itemView.findViewById(R.id.tournamentGameMode);
            tournamentStartDate = itemView.findViewById(R.id.tournamentStartDate);
            tournamentFormat = itemView.findViewById(R.id.tournamentFormat);
            tournamentTeamsCount = itemView.findViewById(R.id.tournamentTeamsCount);
            tournamentSettings = itemView.findViewById(R.id.tournamentRegistration);
        }
    }
}
