package com.deathstar.planetquery.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.deathstar.planetquery.domain.Planet;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "planets", path = "planets")
public interface PlanetRepository extends ReadOnlyPagingAndSortingRepository {
	public List<Planet> findByDestroyable(@Param("destroyable") boolean destroyable);

	public Planet findById(@Param("id") String id);

	public Planet findByName(@Param("name") String name);
}