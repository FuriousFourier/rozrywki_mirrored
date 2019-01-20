package pl.edu.agh.reporting.reports.infrastructure.mongodb.online;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.springframework.stereotype.Repository;
import pl.edu.agh.reporting.events.ReportingEvent;
import pl.edu.agh.reporting.reports.domain.site.SiteReport;
import pl.edu.agh.reporting.reports.domain.site.SiteReportRepository;
import pl.edu.agh.reporting.reports.infrastructure.mongodb.MongoEventStore;
import pl.edu.agh.reporting.reports.infrastructure.mongodb.PersistentEvent;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Repository
@Slf4j
class MongoEventSourcedSiteReportsRepository implements SiteReportRepository {

    private static final String AGGREGATE_TYPE_NAME = SiteReport.class.getSimpleName();

    private final MongoEventStore eventStore;

    public MongoEventSourcedSiteReportsRepository(MongoEventStore eventStore) {
        this.eventStore = eventStore;
    }

    @Override
    public Optional<SiteReport> findBySiteName(String name) {
        final List<PersistentEvent> events = eventStore.findByAggregateId(name);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        final List<ReportingEvent> reportingEvents = unwrap(events);
        final SiteReport report = recreateReport(name, reportingEvents);
        return Optional.of(report);
    }

    private SiteReport recreateReport(String name, List<ReportingEvent> events) {
        return SiteReport.recreate(name, CurrencyUnit.USD, events);
    }

    @Override
    public void save(SiteReport player) {
        final List<PersistentEvent> events = player.getEventsAndFlush()
                .stream()
                .map(event -> new PersistentEvent(player.getName(), event, SiteReport.class))
                .collect(toList());
        eventStore.saveAll(events);
        log.info("Player {} saved to db", player);
    }

    @Override
    public List<SiteReport> findAll() {
        final Map<String, List<PersistentEvent>> eventsPerPlayerName = eventStore.findByAggregateType(AGGREGATE_TYPE_NAME)
                .stream()
                .collect(Collectors.groupingBy(PersistentEvent::getAggregateId));
        return eventsPerPlayerName.entrySet().stream()
                .map(eventsForPlayer -> recreateReport(eventsForPlayer.getKey(), unwrap(eventsForPlayer.getValue())))
                .collect(toList());
    }

    @Override
    public List<SiteReport> findAllBetween(Instant start, Instant end) {
        final Map<String, List<PersistentEvent>> eventsPerPlayerName =
                eventStore.findAllByTimestampBetweenAndAggregateType(start, end, AGGREGATE_TYPE_NAME)
                        .stream()
                        .collect(Collectors.groupingBy(PersistentEvent::getAggregateId));
        return eventsPerPlayerName.entrySet().stream()
                .map(eventsForPlayer -> recreateReport(eventsForPlayer.getKey(), unwrap(eventsForPlayer.getValue())))
                .collect(toList());
    }

    private static List<ReportingEvent> unwrap(List<PersistentEvent> persistentEvents) {
        return persistentEvents.stream()
                .map(PersistentEvent::getPayload)
                .collect(toList());
    }
}
