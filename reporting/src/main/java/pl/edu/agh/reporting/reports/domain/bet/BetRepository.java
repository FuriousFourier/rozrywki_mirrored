package pl.edu.agh.reporting.reports.domain.bet;

import java.util.List;
import java.util.Optional;

public interface BetRepository {

    void save(Bet bet);

    Optional<Bet> findById(String id);

    List<Bet> findAll();
}
