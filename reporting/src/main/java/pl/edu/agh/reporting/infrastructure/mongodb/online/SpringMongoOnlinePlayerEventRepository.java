package pl.edu.agh.reporting.infrastructure.mongodb.online;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

interface SpringMongoOnlinePlayerEventRepository extends MongoRepository<OnlinePlayerEvent, String> {

    List<OnlinePlayerEvent> findByName(String name);

    List<OnlinePlayerEvent> findAllByTimestampBetween(Instant start, Instant end);
}
