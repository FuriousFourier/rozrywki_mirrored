package pl.edu.agh.reporting.ui;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.domain.bet.Bet;
import pl.edu.agh.reporting.domain.bet.BetRepository;

import java.util.List;

@RestController
public class BetsController {

    private final BetRepository betRepository;

    public BetsController(BetRepository betRepository) {
        this.betRepository = betRepository;
    }

    @GetMapping("/api/bets")
    public List<Bet> getAllBets() {
        return betRepository.findAll();
    }

}
