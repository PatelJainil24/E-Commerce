package com.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.app.models.Product;

import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {

	
	public static Specification<Product> filterByAtributes(
			String name,
			String category,
			Double price,
			Boolean available,
			String brand
	){
		return(root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			
			if(name != null && !name.isEmpty()) {
				predicates.add(criteriaBuilder.like(root.get("name"),"%" + name + "%" ));
			}
			
			if(category !=null && !category.isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("category"), category));
			}
			
			if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }
            if (available != null) {
                predicates.add(criteriaBuilder.equal(root.get("available"), available));
            }
            if (brand != null) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
            }
			
			return  criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		
	}
}
