package dk.kea.miniaimvp.service;

import dk.kea.miniaimvp.model.CardModel;
import dk.kea.miniaimvp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private RestTemplate restTemplate;

    public void fetchAndSaveCards() {
        String url = "https://api.magicthegathering.io/v1/cards";
        Map<String, List<Map<String, Object>>> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> cards = response.get("cards");

        for (Map<String, Object> cardData : cards) {
            String cardName = (String) cardData.get("name");
            if (!cardRepository.existsByName(cardName)) { // Assuming there's a unique name constraint

                CardModel card = new CardModel();
                card.setLayout((String) cardData.get("layout"));
                card.setName(cardName);
                card.setNames((String) cardData.get("names"));
                card.setManaCost((String) cardData.get("manaCost"));
                card.setCmc(cardData.get("cmc") != null ? ((Number) cardData.get("cmc")).doubleValue() : 0.0);
                card.setColors((String) cardData.get("colors"));
                card.setColorIdentity((String) cardData.get("colorIdentity"));
                card.setType((String) cardData.get("type"));
                card.setSupertypes((String) cardData.get("supertypes"));
                card.setTypes((String) cardData.get("types"));
                card.setSubtypes((String) cardData.get("subtypes"));
                card.setRarity((String) cardData.get("rarity"));
                card.setText((String) cardData.get("text"));
                card.setOriginalText((String) cardData.get("originalText"));
                card.setFlavor((String) cardData.get("flavor"));
                card.setArtist((String) cardData.get("artist"));
                card.setNumber((String) cardData.get("number"));
                card.setPower((String) cardData.get("power"));
                card.setToughness((String) cardData.get("toughness"));
                card.setLoyalty((String) cardData.get("loyalty"));
                card.setMultiverseid(cardData.get("multiverseid") != null ? (int) cardData.get("multiverseid") : -1);
                card.setVariations((String) cardData.get("variations"));
                card.setImageName((String) cardData.get("imageName"));
                card.setWatermark((String) cardData.get("watermark"));
                card.setBorder((String) cardData.get("border"));
                card.setTimeshifted(cardData.get("timeshifted") != null && (boolean) cardData.get("timeshifted"));
                card.setHand(cardData.get("hand") != null ? (int) cardData.get("hand") : 0);
                card.setLife(cardData.get("life") != null ? (int) cardData.get("life") : 0);
                card.setReserved(cardData.get("reserved") != null && (boolean) cardData.get("reserved"));
                card.setReleaseDate((String) cardData.get("releaseDate"));
                card.setStarter(cardData.get("starter") != null && (boolean) cardData.get("starter"));
                card.setSet((String) cardData.get("set"));
                card.setSetName((String) cardData.get("setName"));
                card.setPrintings((String) cardData.get("printings"));
                card.setImageUrl((String) cardData.get("imageUrl"));

                cardRepository.save(card);
            }
        }
    }
}