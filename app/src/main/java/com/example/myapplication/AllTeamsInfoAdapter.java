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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AllTeamsInfoAdapter extends RecyclerView.Adapter<AllTeamsInfoAdapter.ViewHolder> {
    AllTeamsInfo[] teamsInfo;
    Context context;
    public AllTeamsInfoAdapter(AllTeamsInfo[] teamsInfo, FindTeam activity){
        this.teamsInfo = teamsInfo;
        this.context = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.team_item_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AllTeamsInfo teamsInfoList = teamsInfo[position];
        holder.teamName.setText("Название команды: " + teamsInfoList.getTeamName());
        holder.teamGame.setText("Игра команды: " + teamsInfoList.getTeamGame());
        holder.teamInfo.setText("Описание команды: " + teamsInfoList.getTeamInfo());
        holder.teamRegion.setText("Регион команды: " + teamsInfoList.getTeamRegion());
        holder.teamPlayersCount.setText("Текущее количество игроков: " + teamsInfoList.getTeamPlayersCount().toString());
        holder.requestToTeam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = v.getContext();
                SharedPreferences teamsPref = context.getSharedPreferences("allTeamsInfo",
                        Context.MODE_PRIVATE);
                SharedPreferences sharedPreferences = context.getSharedPreferences("teamsInfoToSettings",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", teamsPref.getString("id", ""));
                editor.putString("name", holder.teamName.getText().toString());
                editor.putString("info", holder.teamInfo.getText().toString());
                editor.putString("game", holder.teamGame.getText().toString());
                editor.putString("region", holder.teamRegion.getText().toString());
                editor.putString("playersCount", holder.teamPlayersCount.getText().toString());
                editor.apply();
                passData(context);
            }
        });
    }

    private void passData(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
        Toast.makeText(context, "Запрос отправлен",Toast.LENGTH_SHORT);
    }

    @Override
    public int getItemCount() {
        return teamsInfo.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView teamId;
        TextView teamName;
        TextView teamGame;
        TextView teamInfo;
        TextView teamRegion;
        TextView teamPlayersCount;
        Button requestToTeam;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teamId = itemView.findViewById(R.id.teamId);
            teamName = itemView.findViewById(R.id.teamName);
            teamGame = itemView.findViewById(R.id.teamGame);
            teamInfo = itemView.findViewById(R.id.teamInfo);
            teamRegion = itemView.findViewById(R.id.teamRegion);
            teamPlayersCount = itemView.findViewById(R.id.teamPlayersCount);
            requestToTeam = itemView.findViewById(R.id.requestToTeam);
        }
    }
}
