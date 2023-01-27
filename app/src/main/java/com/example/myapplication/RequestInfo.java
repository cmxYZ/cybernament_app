package com.example.myapplication;

import android.widget.Button;

public class RequestInfo {
    private Integer tournamentId;
    private String teamName;
    private Button applyTeam;

    public RequestInfo(Integer tournamentId, String teamName, Button applyTeam) {
        this.tournamentId = tournamentId;
        this.teamName = teamName;
        this.applyTeam = applyTeam;
    }

    public Integer getTournamentId() { return tournamentId; }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Button getApplyTeam() {
        return applyTeam;
    }

    public void setApplyTeam(Button applyTeam) {
        this.applyTeam = applyTeam;
    }
}
