package br.com.growdev.growdevers.repositories.specifications;

import br.com.growdev.growdevers.enums.EStatus;
import br.com.growdev.growdevers.models.Growdever;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;

public class GrowdeverSpecification {
    public static Specification<Growdever> filterByNameAndStatus(String name, EStatus status) {
        return (root, query, criteriaBuilder) -> {
            var conditions = new ArrayList<Predicate>();

            if(name != null && !name.isEmpty()) {
                conditions.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%")
                );
            }

            if(status != null) {
                conditions.add(
                        criteriaBuilder.equal(root.get("status"), status)
                );
            }

            return criteriaBuilder.and(conditions.toArray(new Predicate[0]));
        };
    }


}
