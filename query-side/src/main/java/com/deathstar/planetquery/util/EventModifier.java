package com.deathstar.planetquery.util;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.deathstar.planetevents.events.PlanetAddedEvent;

public class EventModifier {
	
	private static final Logger LOG = LoggerFactory.getLogger(EventModifier.class);

	public static void swapi(PlanetAddedEvent event) {
        try {
    		URIBuilder ub = new URIBuilder("https://swapi.co/api/planets");
    		ub.addParameter("search", event.getName());
    		String URL = ub.toString();
    		RestTemplate restTemplate = new RestTemplate();
            Map<String, String> parameters = new HashMap<>();
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "swapi-Java-SWAPI-JAVA");
            HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = null;
            response = restTemplate.exchange(URL,
                                             HttpMethod.GET,
                                             httpEntity,
                                             Map.class,
                                             parameters);
            JSONObject jsonObj = new JSONObject(response.getBody());
            JSONArray results = (JSONArray) jsonObj.get("results");
            JSONObject rs = (JSONObject) results.get(0);
            JSONArray films = (JSONArray) rs.get("films");
            event.setNumberOfFilms(films.length());      
            LOG.info("EventModifier:swapi | getNumberOfFilms: [{}]", event.getNumberOfFilms());            
        } catch (RestClientException | JSONException | URISyntaxException rce) {
            LOG.info("swapi:RestClientException | JSONException | URISyntaxException: [{}]", rce.getMessage());
        }
	}
}
