package rene.kontrolltoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rene.kontrolltoo.entity.NumberEntity;

public interface NumberRepository extends JpaRepository<NumberEntity, Integer> {
}
