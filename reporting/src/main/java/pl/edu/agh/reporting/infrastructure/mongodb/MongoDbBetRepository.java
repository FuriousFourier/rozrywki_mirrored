package pl.edu.agh.reporting.infrastructure.mongodb;

import org.springframework.stereotype.Repository;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;

import java.util.List;

@Repository
class MongoDbBetRepository implements BetRepository {

    private final SpringMongoBetRepository springMongoBetRepository;

    public MongoDbBetRepository(SpringMongoBetRepository springMongoBetRepository) {
        this.springMongoBetRepository = springMongoBetRepository;
    }

    @Override
    public void save(Bet bet) {
        springMongoBetRepository.save(bet);
    }

    @Override
    public List<Bet> findAll() {
        return springMongoBetRepository.findAll();
    }
}
