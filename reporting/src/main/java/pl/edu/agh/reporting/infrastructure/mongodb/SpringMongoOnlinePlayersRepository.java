package pl.edu.agh.reporting.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;

interface SpringMongoOnlinePlayersRepository extends MongoRepository<OnlinePlayerReport, String> {
}
