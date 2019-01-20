package pl.edu.agh.reporting.reports.infrastructure.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface MongoEventStore extends MongoRepository<PersistentEvent, String> {

    List<PersistentEvent> findByAggregateId(String aggregateId);

    List<PersistentEvent> findAllByTimestampBetweenAndAggregateType(Instant start, Instant end, String aggregateType);

    List<PersistentEvent> findByAggregateType(String aggregateType);
}
