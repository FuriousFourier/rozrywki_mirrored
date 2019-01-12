package pl.edu.agh.reporting.infrastructure.mongodb.bets;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
class MongoDbBetRepository implements BetRepository {

    private final SpringMongoBetRepository springMongoBetRepository;

    public MongoDbBetRepository(SpringMongoBetRepository springMongoBetRepository) {
        this.springMongoBetRepository = springMongoBetRepository;
    }

    @Override
    public void save(Bet bet) {
        springMongoBetRepository.save(bet);
        log.info("Saved {} to db", bet);
    }

    @Override
    public Optional<Bet> findById(String id) {
        return springMongoBetRepository.findById(id);
    }

    @Override
    public List<Bet> findAll() {
        return springMongoBetRepository.findAll();
    }
}
