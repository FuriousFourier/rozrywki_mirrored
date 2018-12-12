package pl.edu.agh.reporting.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

interface SpringMongoOnlinePlayerEventRepository extends MongoRepository<OnlinePlayerEvent, String> {

    List<OnlinePlayerEvent> findByName(String name);

    List<OnlinePlayerEvent> findAllByTimestampAfterAndTimestampBefore(LocalDateTime start, LocalDateTime end);
}
