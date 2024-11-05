package dk.kea.miniaimvp.controller;

import dk.kea.miniaimvp.DTO.MyResponse;
import dk.kea.miniaimvp.model.CardModel;
import dk.kea.miniaimvp.service.CardService;
import dk.kea.miniaimvp.service.OpenAiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/deck")
public class DeckController {

    private final OpenAiService openAiService;

    private final CardService cardService;

    public DeckController(OpenAiService openAiService, CardService cardService) {
        this.openAiService = openAiService;
        this.cardService = cardService;
    }

    @PostMapping("/suggest")
    public ResponseEntity<MyResponse> getDeckSuggestion(@RequestBody Map<String, String> request) {
        String deckType = request.get("deckType");

        // Hent kort baseret på deckType
        List<CardModel> cards;
        if ("rush".equals(deckType)) {
            cards = cardService.getCardsForRushDeck();
        } else {
            // Her kan du tilføje flere deck-typer eller returnere en fejlmeddelelse
            cards = new ArrayList<>();
        }

        // Send prompt og kortdata til OpenAI API’en
        MyResponse response = openAiService.makeRequestWithCards(deckType, cards);
        return ResponseEntity.ok(response);
    }
}
