package pl.edu.agh.reporting.reports.domain.online;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface OnlinePlayersRepository {

    Optional<OnlinePlayerReport> findByName(String name);

    void save(OnlinePlayerReport playerFromRepository);

    List<OnlinePlayerReport> findAll();

    List<OnlinePlayerReport> findAllBetween(Instant start, Instant end);
}
