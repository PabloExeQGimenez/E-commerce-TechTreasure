package com.mindhub.techTreasure.repositories;

import com.mindhub.techTreasure.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    boolean existsByCode(String code);

}
