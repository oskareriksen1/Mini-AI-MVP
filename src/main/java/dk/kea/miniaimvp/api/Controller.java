package dk.kea.miniaimvp.api;

import dk.kea.miniaimvp.model.CardModel;
import dk.kea.miniaimvp.service.CardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
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
    @GetMapping("/all")
    public List<CardModel> getAllCards() {
        return cardService.getAllCardsWithMappedColors();
    }
    @GetMapping("/filter")
    public List<CardModel> filterCards(
            @RequestParam(required = false) String mana,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String effect) {
        return cardService.filterCards(mana, type, effect);
    }

}