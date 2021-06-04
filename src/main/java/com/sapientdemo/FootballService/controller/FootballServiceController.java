package com.sapientdemo.FootballService.controller;


import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapientdemo.FootballService.dao.TeamStanding;
import com.sapientdemo.FootballService.service.FootballService;

@RestController
public class FootballServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(FootballServiceController.class);

	@Autowired
	FootballService service;

	@GetMapping("/fbs/getTeam")
	@ResponseBody
	public ResponseEntity<TeamStanding> getTeam(@RequestParam(name = "countryName") String countryName,
			@RequestParam(name = "leagueName") String leagueName, @RequestParam(name = "teamName") String teamName) {

		logger.info(MessageFormat.format("Request || countryname : {0}, leaguename: {1}, teamname: {2}", countryName, leagueName,teamName));
		TeamStanding standing = service.getFootballTeam(countryName, leagueName, teamName);
		return (standing == null) ? new ResponseEntity<>(standing, HttpStatus.NOT_FOUND)
				: new ResponseEntity<>(standing, HttpStatus.CREATED);

	}

}
