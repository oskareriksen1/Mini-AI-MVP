package dk.kea.miniaimvp.controller;


import dk.kea.miniaimvp.DTO.MyResponse;
import dk.kea.miniaimvp.service.OpenAiService;
import org.springframework.web.bind.annotation.*;

/**
 * This class handles fetching a card ranking via the ChatGPT API.
 */
@RestController
@RequestMapping("/api/v1/rank")
@CrossOrigin(origins = "*")
public class CardRankingController {

    private final OpenAiService service;

    /**
     * This contains the message to the ChatGPT API, instructing it to rank the provided Magic: The Gathering cards.
     */
    final static String SYSTEM_MESSAGE = "You are a ranking assistant for Magic: The Gathering cards. " +
            "The user will provide card data, and you should rank them based on their strategic potential in gameplay.";

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
        return service.makeRequest(cards, SYSTEM_MESSAGE);
    }
}
