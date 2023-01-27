package com.example.myapplication;

import android.widget.Button;

public class TournamentInfo {
    private Integer tournamentId;
    private String tournamentName;
    private String tournamentInfo;
    private String tournamentGame;
    private String tournamentGameMode;
    private String tournamentStartDate;
    private String tournamentFormat;
    private Integer tournamentTeamsCount;
    private Button tournamentSettings;

    public TournamentInfo(Integer tournamentId,String tournamentName, String tournamentInfo, String tournamentGame, String tournamentGameMode, String tournamentStartDate, String tournamentFormat, Integer tournamentTeamsCount, Button tournamentSettings) {
        this.tournamentId = tournamentId;
        this.tournamentName = tournamentName;
        this.tournamentInfo = tournamentInfo;
        this.tournamentGame = tournamentGame;
        this.tournamentGameMode = tournamentGameMode;
        this.tournamentStartDate = tournamentStartDate;
        this.tournamentFormat = tournamentFormat;
        this.tournamentTeamsCount = tournamentTeamsCount;
        this.tournamentSettings = tournamentSettings;
    }

    public Integer getTournamentId() { return tournamentId; }

    public void setTournamentId(Integer tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentInfo() {
        return tournamentInfo;
    }

    public void setTournamentInfo(String tournamentInfo) {
        this.tournamentInfo = tournamentInfo;
    }

    public String getTournamentGame() {
        return tournamentGame;
    }

    public void setTournamentGame(String tournamentGame) {
        this.tournamentGame = tournamentGame;
    }

    public String getTournamentGameMode() {
        return tournamentGameMode;
    }

    public void setTournamentGameMode(String tournamentGameMode) {
        this.tournamentGameMode = tournamentGameMode;
    }

    public String getTournamentStartDate() {
        return tournamentStartDate;
    }

    public void setTournamentStartDate(String tournamentStartDate) {
        this.tournamentStartDate = tournamentStartDate;
    }

    public String getTournamentFormat() {
        return tournamentFormat;
    }

    public void setTournamentFormat(String tournamentFormat) {
        this.tournamentFormat = tournamentFormat;
    }

    public Integer getTournamentTeamsCount() {
        return tournamentTeamsCount;
    }

    public void setTournamentTeamsCount(Integer tournamentTeamsCount) {
        this.tournamentTeamsCount = tournamentTeamsCount;
    }

    public Button getTournamentSettings() { return tournamentSettings; }

    public void setTournamentSettings(Button tournamentSettings) {
        this.tournamentSettings = tournamentSettings;
    }
}
