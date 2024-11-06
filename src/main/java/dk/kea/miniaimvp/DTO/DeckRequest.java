package dk.kea.miniaimvp.DTO;

import dk.kea.miniaimvp.model.CardModel;

import java.util.List;

public class DeckRequest {
    private List<String> colors;
    private String deckType;
    private List<CardModel> availableCards;

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
    public List<CardModel> getAvailableCards() {
        return availableCards;
    }
    public void setAvailableCards(List<CardModel> availableCards) {
        this.availableCards = availableCards;
    }
}
