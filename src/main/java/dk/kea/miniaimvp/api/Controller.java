package dk.kea.miniaimvp.api;

import dk.kea.miniaimvp.service.CardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
public class Controller {

    private final CardService cardService;

    public Controller(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/fetch")
    public String fetchCards() {
        cardService.fetchAndSaveCards();
        return "Cards fetched and saved to the database";
    }
}