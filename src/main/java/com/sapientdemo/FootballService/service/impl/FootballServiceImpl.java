package com.sapientdemo.FootballService.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sapientdemo.FootballService.dao.Country;
import com.sapientdemo.FootballService.dao.Leagues;
import com.sapientdemo.FootballService.dao.TeamStanding;
import com.sapientdemo.FootballService.service.FootballService;

@Service
public class FootballServiceImpl implements FootballService{

	@Value("${football.api.service.url}")
	private String footballServiceUrl;
	
	@Value("${football.api.key}")
	private String footballApiKey;
	
	@Autowired
	FootballServiceImpl service;
	
	@Autowired
	RestTemplate template;
	
	private static final String COUNTRIES_ACTION = "get_countries";
	private static final String LEAGUES_ACTION = "get_leagues";
	private static final String STANDINGS_ACTION = "get_standings";
	
	@Override
	
	public TeamStanding getFootballTeam(String contryName, String leagueName, String teamName) {
		
		String countryId = service.getContrieDetails(contryName);
		if(countryId != null) {
			String leagueId = service.getLeague(leagueName, countryId);
			if(leagueId != null) {
				TeamStanding ts = getTeamStanding(countryId, leagueId, teamName);
				if(ts != null) {
					ts.setCountry_name(contryName);
					return ts;
				}
			}
		}
		
		return null;
		
		//https://apiv2.apifootball.com/?action=get_standings&league_id=148&APIkey=xxxxxxxxxxxxxx
		//https://apiv2.apifootball.com/?action=get_leagues&country_id=41&APIkey=xxxxxxxxxxxxxx
	}
	
	public TeamStanding getTeamStanding(String countryId, String leagueId, String teamName) {
		
		URI uri;
		try {
			uri = new URIBuilder(footballServiceUrl)
				      .addParameter("action", STANDINGS_ACTION)
				      .addParameter("league_id", leagueId)
				      .addParameter("APIkey", footballApiKey)
				      .build();
			
			ResponseEntity<List<TeamStanding>> responseEntity = template.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<TeamStanding>>() {});
			if(responseEntity.getStatusCode().is2xxSuccessful()) {
				List<TeamStanding> teamStandings = responseEntity.getBody();
				TeamStanding tsr = teamStandings.stream().filter(teamStanding -> teamStanding.getTeam_name().equals(teamName)).findFirst().orElse(null);
				return tsr;
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Cacheable("countries")
	public String getContrieDetails(String contryName) {
		URI uri;
		try {
			uri = new URIBuilder(footballServiceUrl)
				      .addParameter("action",COUNTRIES_ACTION )
				      .addParameter("APIkey", footballApiKey)
				      .build();
			
			ResponseEntity<List<Country>> responseEntity = template.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Country>>() {});
			if(responseEntity.getStatusCode().is2xxSuccessful()) {
				List<Country> contries = responseEntity.getBody();
				Country ctr = contries.stream().filter(country -> country.getCountry_name().equals(contryName)).findFirst().orElse(null);
				return (ctr == null) ? null : ctr.getCountry_id();
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Cacheable("leagues")
	public String getLeague(String leagueName, String countryId) {
		URI uri;
		try {
			uri = new URIBuilder(footballServiceUrl)
				      .addParameter("action", LEAGUES_ACTION)
				      .addParameter("country_id", countryId)
				      .addParameter("APIkey", footballApiKey)
				      .build();
			
			ResponseEntity<List<Leagues>> responseEntity = template.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<Leagues>>() {});
			if(responseEntity.getStatusCode().is2xxSuccessful()) {
				List<Leagues> leagues = responseEntity.getBody();
				Leagues lg = leagues.stream().filter(league -> league.getLeague_name().equals(leagueName)).findFirst().orElse(null);
				return (lg == null)? null : lg.getLeague_id();
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
