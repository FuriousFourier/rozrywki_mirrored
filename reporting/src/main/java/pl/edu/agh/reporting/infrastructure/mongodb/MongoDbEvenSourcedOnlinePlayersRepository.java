package pl.edu.agh.reporting.infrastructure.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;
import pl.edu.agh.reporting.events.ReportingEvent;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

//@Repository
@Slf4j
public class MongoDbEvenSourcedOnlinePlayersRepository implements OnlinePlayersRepository {

    private final SpringMongoOnlinePlayerEventRepository springMongoOnlinePlayersRepository;

    public MongoDbEvenSourcedOnlinePlayersRepository(SpringMongoOnlinePlayerEventRepository springMongoOnlinePlayersRepository) {
        this.springMongoOnlinePlayersRepository = springMongoOnlinePlayersRepository;
    }

    @Override
    public Optional<OnlinePlayerReport> findByName(String name) {
        final List<OnlinePlayerEvent> events = springMongoOnlinePlayersRepository.findByName(name);
        if (events.isEmpty()) {
            return Optional.empty();
        }
        final OnlinePlayerReport report = recreateReport(name, events);
        return Optional.of(report);
    }

    private OnlinePlayerReport recreateReport(String name, List<OnlinePlayerEvent> events) {
        final List<ReportingEvent> reportingEvents = events.stream().map(OnlinePlayerEvent::getPayload).collect(Collectors.toList());
        return OnlinePlayerReport.recreate(name, CurrencyUnit.USD, reportingEvents);
    }

    @Override
    public void save(OnlinePlayerReport player) {
        final List<OnlinePlayerEvent> events = player.getEventsAndFlush()
                .stream()
                .map(event -> new OnlinePlayerEvent(player.getName(), event))
                .collect(Collectors.toList());
        springMongoOnlinePlayersRepository.saveAll(events);
        log.info("Player {} saved to db", player);
    }

    @Override
    public List<OnlinePlayerReport> findAll() {
        final Map<String, List<OnlinePlayerEvent>> eventsPerPlayerName = springMongoOnlinePlayersRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(OnlinePlayerEvent::getName));
        return eventsPerPlayerName.entrySet().stream()
                .map(eventsForPlayer -> recreateReport(eventsForPlayer.getKey(), eventsForPlayer.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OnlinePlayerReport> findAllBetween(LocalDateTime start, LocalDateTime end) {
        final Map<String, List<OnlinePlayerEvent>> eventsPerPlayerName =
                springMongoOnlinePlayersRepository.findAllByTimestampAfterAndTimestampBefore(start, end)
                        .stream()
                        .collect(Collectors.groupingBy(OnlinePlayerEvent::getName));
        return eventsPerPlayerName.entrySet().stream()
                .map(eventsForPlayer -> recreateReport(eventsForPlayer.getKey(), eventsForPlayer.getValue()))
                .collect(Collectors.toList());
    }
}
