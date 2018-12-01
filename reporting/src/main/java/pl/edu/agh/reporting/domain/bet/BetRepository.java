package pl.edu.agh.reporting.domain.bet;

import java.util.List;

public interface BetRepository {

    void save(Bet bet);

    List<Bet> findAll();
}
