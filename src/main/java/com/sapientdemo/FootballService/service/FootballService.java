package com.sapientdemo.FootballService.service;

import com.sapientdemo.FootballService.dao.TeamStanding;

public interface FootballService {

	public TeamStanding getFootballTeam(String contryName, String leagueName, String teamName);
}
