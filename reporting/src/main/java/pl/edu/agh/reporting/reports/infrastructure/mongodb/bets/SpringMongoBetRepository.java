package pl.edu.agh.reporting.reports.infrastructure.mongodb.bets;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.reporting.reports.domain.bet.Bet;

interface SpringMongoBetRepository extends MongoRepository<Bet, String> {
}
