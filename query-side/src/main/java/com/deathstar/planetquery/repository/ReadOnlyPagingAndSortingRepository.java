package com.deathstar.planetquery.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.deathstar.planetquery.domain.Planet;

@NoRepositoryBean
public interface ReadOnlyPagingAndSortingRepository extends PagingAndSortingRepository<Planet, String> {

	@Override
	@SuppressWarnings("unchecked")
	@RestResource(exported = false) // true means the capability will be offered
	Planet save(Planet entity);

	@Override
	@RestResource(exported = false) // false restricts the capability
	void delete(String aLong);

	@Override
	@RestResource(exported = false)
	void delete(Planet entity);
}
