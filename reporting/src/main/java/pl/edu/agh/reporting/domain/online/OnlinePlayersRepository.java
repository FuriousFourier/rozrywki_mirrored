package pl.edu.agh.reporting.domain.online;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OnlinePlayersRepository {

    Optional<OnlinePlayerReport> findByName(String name);

    void save(OnlinePlayerReport playerFromRepository);

    List<OnlinePlayerReport> findAll();

    List<OnlinePlayerReport> findAllBetween(LocalDateTime start, LocalDateTime end);
}
