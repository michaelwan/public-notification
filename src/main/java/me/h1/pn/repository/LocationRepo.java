package me.h1.pn.repository;

import me.h1.pn.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepo extends JpaRepository<Location, Integer> {
    Optional<Location> findByName(String name);
}
