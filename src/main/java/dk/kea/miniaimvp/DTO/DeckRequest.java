package dk.kea.miniaimvp.DTO;

import java.util.List;

public class DeckRequest {
    private List<String> colors;
    private String deckType;

    // Getters og Setters
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public String getDeckType() {
        return deckType;
    }

    public void setDeckType(String deckType) {
        this.deckType = deckType;
    }
}
