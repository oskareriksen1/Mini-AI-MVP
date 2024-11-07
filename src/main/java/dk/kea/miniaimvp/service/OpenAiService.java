package dk.kea.miniaimvp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.kea.miniaimvp.DTO.ChatCompletionRequest;
import dk.kea.miniaimvp.DTO.ChatCompletionResponse;
import dk.kea.miniaimvp.DTO.MyResponse;
import dk.kea.miniaimvp.model.CardModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.List;

@Service
public class OpenAiService {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiService.class);

    @Value("${app.api-key}")
    private String API_KEY;

    @Value("${app.url}")
    public String URL;

    @Value("${app.model}")
    public String MODEL;

    @Value("${app.temperature}")
    public double TEMPERATURE;

    @Value("${app.max_tokens}")
    public int MAX_TOKENS;

    @Value("${app.frequency_penalty}")
    public double FREQUENCY_PENALTY;

    @Value("${app.presence_penalty}")
    public double PRESENCE_PENALTY;

    @Value("${app.top_p}")
    public double TOP_P;

    private WebClient client;

    public OpenAiService() {
        this.client = WebClient.create();
    }

    public MyResponse makeRequestWithCards(String deckType, List<CardModel> cards) {
        StringBuilder userPrompt = new StringBuilder("Byg et effektivt " + deckType + " deck med de følgende kort:\n");

        for (CardModel card : cards) {
            userPrompt.append("- Navn: ").append(card.getName()).append(", ");
//            userPrompt.append("Farver: ").append(card.getColors() != null ? card.getColors().toString() : "N/A").append(", ");
//            userPrompt.append("Mana-kost: ").append(card.getManaCost() != null ? card.getManaCost() : "N/A").append(", ");
//            userPrompt.append("Power: ").append(card.getPower() != null ? card.getPower() : "N/A").append(", ");
//            userPrompt.append("Toughness: ").append(card.getToughness() != null ? card.getToughness() : "N/A").append(", ");
//            userPrompt.append("Effekt: ").append(card.getText() != null ? card.getText() : "N/A").append("\n");
        }

        userPrompt.append("\nVælg de bedste kort for at skabe et hurtigt, aggressivt deck med høj angrebsstyrke og lav mana-kost.");

        // Log prompten for at se, hvordan den ser ud
        logger.info("User prompt: " + userPrompt.toString());

        String systemMessage = "Du er en erfaren Magic: The Gathering deck-builder.";

        ChatCompletionRequest requestDto = new ChatCompletionRequest();
        requestDto.setModel(MODEL);
        requestDto.setTemperature(TEMPERATURE);
        requestDto.setMax_tokens(MAX_TOKENS);
        requestDto.setTop_p(TOP_P);
        requestDto.setFrequency_penalty(FREQUENCY_PENALTY);
        requestDto.setPresence_penalty(PRESENCE_PENALTY);
        requestDto.getMessages().add(new ChatCompletionRequest.Message("system", systemMessage));
        requestDto.getMessages().add(new ChatCompletionRequest.Message("user", userPrompt.toString()));

        // Send request og få respons
        try {
            ChatCompletionResponse response = client.post()
                    .uri(new URI(URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestDto))
                    .retrieve()
                    .bodyToMono(ChatCompletionResponse.class)
                    .block();

            String responseMsg = response.getChoices().get(0).getMessage().getContent();
            int tokensUsed = response.getUsage().getTotal_tokens();
            logger.info("Tokens used: " + tokensUsed);

            return new MyResponse(responseMsg);
        } catch (WebClientResponseException e) {
            logger.error("Error response status code: " + e.getRawStatusCode());
            logger.error("Error response body: " + e.getResponseBodyAsString());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed request to OpenAI API");
        } catch (Exception e) {
            logger.error("Exception", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }
    public MyResponse makeRequest(String userPrompt, String systemMessage) {
        ChatCompletionRequest requestDto = new ChatCompletionRequest();
        requestDto.setModel(MODEL);
        requestDto.setTemperature(TEMPERATURE);
        requestDto.setMax_tokens(MAX_TOKENS);
        requestDto.setTop_p(TOP_P);
        requestDto.setFrequency_penalty(FREQUENCY_PENALTY);
        requestDto.setPresence_penalty(PRESENCE_PENALTY);
        requestDto.getMessages().add(new ChatCompletionRequest.Message("system", systemMessage));
        requestDto.getMessages().add(new ChatCompletionRequest.Message("user", userPrompt));

        try {
            ChatCompletionResponse response = client.post()
                    .uri(new URI(URL))
                    .header("Authorization", "Bearer " + API_KEY)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(requestDto))
                    .retrieve()
                    .bodyToMono(ChatCompletionResponse.class)
                    .retryWhen(
                            Retry.backoff(3, Duration.ofSeconds(2))  // Op til 3 forsøg med eksponentiel backoff
                                    .filter(throwable -> throwable instanceof WebClientResponseException.TooManyRequests)  // Kun retry på 429-fejl
                                    .doBeforeRetry(retrySignal -> logger.warn("Retrying request to OpenAI API due to 429 Too Many Requests"))
                    )
                    .block();

            String responseMsg = response.getChoices().get(0).getMessage().getContent();
            return new MyResponse(responseMsg);
        } catch (WebClientResponseException.TooManyRequests e) {
            logger.error("Reached rate limit with OpenAI API. Try again later.", e);
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Too many requests to OpenAI API. Please try again later.");
        } catch (Exception e) {
            logger.error("Error communicating with OpenAI API", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred while communicating with OpenAI");
        }
    }


}
