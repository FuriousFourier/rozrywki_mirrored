package pl.edu.agh.reporting.application;

import com.google.common.collect.ImmutableList;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.reporting.domain.bet.Bet;

import java.util.List;

@RestController
public class BetsController {

    @GetMapping("/api/bets")
    public List<Bet> getAllBets() {
        // TODO
        return ImmutableList.of(new Bet("1", "match", "Tom"));
    }

}
