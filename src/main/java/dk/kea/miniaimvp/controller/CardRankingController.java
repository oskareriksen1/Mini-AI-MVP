package dk.kea.miniaimvp.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import dk.kea.miniaimvp.DTO.DeckRequest;
import dk.kea.miniaimvp.DTO.MyResponse;
import dk.kea.miniaimvp.model.CardModel;
import dk.kea.miniaimvp.service.OpenAiService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;

/**
 * This class handles fetching a card ranking via the ChatGPT API.
 */

@RestController
@RequestMapping("/api/v1/rank")
@CrossOrigin
public class CardRankingController {

    private final OpenAiService service;

    /**
     * This contains the message to the ChatGPT API, instructing it to rank the provided Magic: The Gathering cards.
     */
    final static String SYSTEM_MESSAGE = "You are a deck-building assistant for Magic: The Gathering. " +
            "The user will specify a type of deck and color preferences. " +
            "Create an optimal deck based on the given strategy and colors.";
   /* final static String SYSTEM_MESSAGE = "You are a ranking assistant for Magic: The Gathering cards. " +
            "The user will provide card data, and you should rank them based on their strategic potential in gameplay.";
*/
    /**
     * Constructor for CardRankingController.
     * @param service the OpenAiService used to send requests to OpenAI API.
     */
    public CardRankingController(OpenAiService service) {
        this.service = service;
    }

    /**
     * Handles ranking requests from the client.
     * @param cards the JSON array containing the card data to be ranked.
     * @return the ranking response from ChatGPT.
     */
    @PostMapping
    public MyResponse getRanking(@RequestBody String cards) {
        // Parse JSON-strengen til en liste af CardModel objekter
        List<CardModel> cardModels = parseCards(cards);

        // Kald makeRequestWithCards med "rank" som deckType og cardModels som kortdata
        return service.makeRequestWithCards("rank", cardModels);
    }

    // Hjælpefunktion til at parse JSON-strengen til en liste af CardModel
    private List<CardModel> parseCards(String cardsJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Konverter JSON-strengen til en liste af CardModel objekter
            return Arrays.asList(objectMapper.readValue(cardsJson, CardModel[].class));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid card data format");
        }
    }

    @PostMapping("/generate")
    public MyResponse generateDeck(@RequestBody DeckRequest deckRequest) {
        // Byg prompt baseret på de tilgængelige kort og brugerens præferencer
        String prompt = createDeckPrompt(deckRequest);
        return service.makeRequest(prompt, SYSTEM_MESSAGE);
    }

    private String createDeckPrompt(DeckRequest deckRequest) {
        String colorList = String.join(", ", deckRequest.getColors());
        String deckType = deckRequest.getDeckType();

        // Tilføj de tilgængelige kort i prompten, så ChatGPT kan vælge blandt dem
        StringBuilder promptBuilder = new StringBuilder("Please create an optimal " + deckType + " deck using the following colors: " + colorList + ". You may only choose from the available cards listed below:\n");

        for (CardModel card : deckRequest.getAvailableCards()) {
            promptBuilder.append("- Name: ").append(card.getName())
                    .append("\n");
        }

        promptBuilder.append("\nChoose the best cards for a ").append(deckType).append(" strategy.");
        return promptBuilder.toString();
    }
}
