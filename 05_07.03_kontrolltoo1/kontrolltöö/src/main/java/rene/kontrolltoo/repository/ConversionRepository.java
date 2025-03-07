package rene.kontrolltoo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rene.kontrolltoo.entity.ConversionEntity;

import java.util.List;

public interface ConversionRepository extends JpaRepository<ConversionEntity, Long> {
    List<ConversionEntity> findByConversionType(String conversionType);
}
