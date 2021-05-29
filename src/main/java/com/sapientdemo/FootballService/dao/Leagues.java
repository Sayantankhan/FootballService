package com.sapientdemo.FootballService.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Leagues {

	private String country_id;
	private String country_name;
	private String league_id;
	private String league_name;
}
