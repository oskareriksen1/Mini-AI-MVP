package dk.kea.miniaimvp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
public class ChatCompletionRequest {

    private String model;
    private double temperature;
    private int max_tokens;
    private double top_p;
    private double frequency_penalty;
    private double presence_penalty;
    private List<Message> messages = new ArrayList<>();

    // Getters and Setters
    @Getter
    @Setter
    public static class Message {
        private String role;
        private String content;

        public Message(String role, String content) {
            this.role = role;
            this.content = content;
        }

        // Getters and Setters
    }
}
