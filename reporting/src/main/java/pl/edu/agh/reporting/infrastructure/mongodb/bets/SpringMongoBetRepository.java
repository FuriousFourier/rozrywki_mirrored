package pl.edu.agh.reporting.infrastructure.mongodb.bets;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.reporting.domain.bet.Bet;

interface SpringMongoBetRepository extends MongoRepository<Bet, String> {
}
