package com.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lms.model.Category;

@Repository("categoryRepository")
public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByType(String type);
}
