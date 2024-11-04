package dk.kea.miniaimvp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import dk.kea.miniaimvp.repository.CardRepository;
import dk.kea.miniaimvp.service.CardService;

@Component
@Order(3)
public class CardInitData implements CommandLineRunner {

    private final CardRepository cardRepository;
    private final CardService cardService;

    public CardInitData(CardRepository cardRepository, CardService cardService) {
        this.cardRepository = cardRepository;
        this.cardService = cardService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (cardRepository.findAll().isEmpty()) {
            cardService.fetchAndSaveCards();
        }
    }
}
