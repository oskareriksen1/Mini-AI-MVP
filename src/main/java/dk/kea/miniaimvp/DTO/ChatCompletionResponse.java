package dk.kea.miniaimvp.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class ChatCompletionResponse {

    private List<Choice> choices;
    private Usage usage;

    // Getters and Setters

    @Getter
    @Setter
    public static class Choice {
        private Message message;

        // Getters and Setters
        @Getter
        @Setter
        public static class Message {
            private String content;

            // Getters and Setters
        }
    }
    @Setter
    @Getter
    public static class Usage {
        private int total_tokens;

        // Getters and Setters
    }
}
