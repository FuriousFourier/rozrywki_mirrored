package pl.edu.agh.reporting.reports.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.reports.domain.bet.Bet;
import pl.edu.agh.reporting.reports.domain.bet.BetRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
public class BetsController {

    private final BetRepository betRepository;

    public BetsController(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @GetMapping("/api/bets")
    public List<Bet> getAllBets() {
        return betRepository.findAll()
                .stream()
                .filter(Bet::isLive)
                .collect(toList());
    }

}
