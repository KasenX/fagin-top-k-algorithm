package cz.cvut.fit.vwm.fagintopkalgorithm.server;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FaginJpaRepository extends JpaRepository<Food, Long> {
}
