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

public class TournamentInfoAdapter extends RecyclerView.Adapter<TournamentInfoAdapter.ViewHolder> {
    TournamentInfo[] tournamentInfo;
    Context context;
    public TournamentInfoAdapter(TournamentInfo[] tournamentInfo, MyTournaments activity){
        this.tournamentInfo = tournamentInfo;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.tournament_item_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final TournamentInfo tournamentInfoList = tournamentInfo[position];
        holder.teamName.setText("Название турнира: " + tournamentInfoList.getTournamentName());
        holder.tournamentInfo.setText("Описание турнира: " + tournamentInfoList.getTournamentInfo());
        holder.tournamentGame.setText("Игра турнира: " + tournamentInfoList.getTournamentGame());
        holder.tournamentGameMode.setText("Режим турнира: " + tournamentInfoList.getTournamentGameMode());
        holder.tournamentFormat.setText("Формат турнира: " + tournamentInfoList.getTournamentFormat());
        holder.tournamentStartDate.setText("Дата турнира: " + tournamentInfoList.getTournamentStartDate());
        holder.tournamentTeamsCount.setText("Количество игроков в турнире: " + tournamentInfoList.getTournamentTeamsCount().toString());
        holder.applyToTournament.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                SharedPreferences sharedPreferences = context.getSharedPreferences("tournamentInfoToSettings",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", holder.requestId.getText().toString());
                editor.putString("name", holder.teamName.getText().toString());
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
        Intent intent = new Intent(context,TournamentSettings.class);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return tournamentInfo.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView requestId;
        TextView teamName;
        TextView tournamentInfo;
        TextView tournamentGame;
        TextView tournamentGameMode;
        TextView tournamentStartDate;
        TextView tournamentFormat;
        TextView tournamentTeamsCount;
        Button applyToTournament;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            requestId = itemView.findViewById(R.id.tournamentId);
            teamName = itemView.findViewById(R.id.tournamentName);
            tournamentInfo = itemView.findViewById(R.id.tournamentInfo);
            tournamentGame = itemView.findViewById(R.id.tournamentGame);
            tournamentGameMode = itemView.findViewById(R.id.tournamentGameMode);
            tournamentStartDate = itemView.findViewById(R.id.tournamentStartDate);
            tournamentFormat = itemView.findViewById(R.id.tournamentFormat);
            tournamentTeamsCount = itemView.findViewById(R.id.tournamentTeamsCount);
            applyToTournament = itemView.findViewById(R.id.tournamentRegistration);
        }
    }
}
