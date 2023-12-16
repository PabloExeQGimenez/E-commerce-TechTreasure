package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
