package com.example.myapplication;

import android.widget.Button;

public class AllTeamsInfo {
    private Integer teamId;
    private String teamName;
    private String teamGame;
    private String teamInfo;
    private String teamRegion;
    private Integer teamPlayersCount;
    private Button requestToTeam;

    public AllTeamsInfo(Integer teamId, String teamName, String teamGame, String teamInfo, String teamRegion, Integer teamPlayersCount, Button requestToTeam) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamGame = teamGame;
        this.teamInfo = teamInfo;
        this.teamRegion = teamRegion;
        this.teamPlayersCount = teamPlayersCount;
        this.requestToTeam = requestToTeam;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamInfo() {
        return teamInfo;
    }

    public void setTeamInfo(String teamInfo) {
        this.teamInfo = teamInfo;
    }

    public String getTeamGame() {
        return teamGame;
    }

    public void setTeamGame(String teamGame) {
        this.teamGame = teamGame;
    }

    public String getTeamRegion() {
        return teamRegion;
    }

    public void setTeamRegion(String teamRegion) {
        this.teamRegion = teamRegion;
    }

    public Integer getTeamPlayersCount() {
        return teamPlayersCount;
    }

    public void setTeamPlayersCount(Integer teamPlayersCount) {
        this.teamPlayersCount = teamPlayersCount;
    }

    public Button getRequestToTeam() { return requestToTeam; }

    public void setRequestToTeam(Button requestToTeam) {
        this.requestToTeam = requestToTeam;
    }
}

