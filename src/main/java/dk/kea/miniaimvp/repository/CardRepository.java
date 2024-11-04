package dk.kea.miniaimvp.repository;

import dk.kea.miniaimvp.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardModel, Long> {
    boolean existsByName(String name);
}