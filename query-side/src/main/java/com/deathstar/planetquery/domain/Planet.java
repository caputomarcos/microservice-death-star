package com.deathstar.planetquery.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Planet {

	@Id
	private String id;

	private String name;
	private String climate;
	private String terrain;
	private Integer numberOfFilms;
	private boolean destroyable;

	public Planet() {
	}

	public Planet(String id, String name, String climate, String terrain, Integer numberOfFilms, boolean destroyable) {
		this.setId(id);
		this.setName(name);
		this.setClimate(climate);
		this.setTerrain(terrain);
		this.setNumberOfFilms(numberOfFilms);
		this.setDestroyable(destroyable);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClimate() {
		return climate;
	}

	public void setClimate(String climate) {
		this.climate = climate;
	}

	public String getTerrain() {
		return terrain;
	}

	public void setTerrain(String terrain) {
		this.terrain = terrain;
	}

	public boolean isDestroyable() {
		return destroyable;
	}

	public void setDestroyable(boolean destroyable) {
		this.destroyable = destroyable;
	}

	public Integer getNumberOfFilms() {
		return numberOfFilms;
	}

	public void setNumberOfFilms(Integer numberOfFilms) {
		this.numberOfFilms = numberOfFilms;
	}
}
