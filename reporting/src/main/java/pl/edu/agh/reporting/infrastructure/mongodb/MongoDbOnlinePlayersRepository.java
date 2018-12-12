package pl.edu.agh.reporting.infrastructure.mongodb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pl.edu.agh.reporting.domain.online.OnlinePlayerReport;
import pl.edu.agh.reporting.domain.online.OnlinePlayersRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MongoDbOnlinePlayersRepository implements OnlinePlayersRepository {

    private final SpringMongoOnlinePlayersRepository springMongoOnlinePlayersRepository;

    public MongoDbOnlinePlayersRepository(SpringMongoOnlinePlayersRepository springMongoOnlinePlayersRepository) {
        this.springMongoOnlinePlayersRepository = springMongoOnlinePlayersRepository;
    }

    @Override
    public Optional<OnlinePlayerReport> findByName(String name) {
        return springMongoOnlinePlayersRepository.findById(name);
    }

    @Override
    public void save(OnlinePlayerReport player) {
        springMongoOnlinePlayersRepository.save(player);
        log.info("Player {} saved to db", player);
    }

    @Override
    public List<OnlinePlayerReport> findAll() {
        return springMongoOnlinePlayersRepository.findAll();
    }

    @Override
    public List<OnlinePlayerReport> findAllBetween(LocalDateTime start, LocalDateTime end) {
        return findAll();
    }
}
