package com.rental.video.store.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GenericSpecification<T> implements Specification<T> {

    private List<SpecificationCriteria> criteria;

    public GenericSpecification() {
        this.criteria = new ArrayList<>();
    }

    public void add(SpecificationCriteria specificationCriteria) {
        this.criteria.add(specificationCriteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        CriteriaBuilder.In<String> inClause = criteriaBuilder.in(root.get("name"));
        criteria.forEach(specificationCriteria -> {
            inClause.value(specificationCriteria.getValue().toString());
//            inClause.value(specificationCriteria.getValue().toString());
//            predicates.add(criteriaBuilder.equal(
//                    root.get(specificationCriteria.getKey()), specificationCriteria.getValue().toString()
//            ));
        });
        predicates.add(criteriaBuilder.in(root.get("name")).value(inClause));

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
