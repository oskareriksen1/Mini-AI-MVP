package dk.kea.miniaimvp.service;
import dk.kea.miniaimvp.model.CardModel;
import dk.kea.miniaimvp.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            if (!cardRepository.existsByName(cardName)) {

                CardModel card = new CardModel();
                card.setLayout((String) cardData.get("layout"));
                card.setName(cardName);

                // Konverter "names" fra liste til streng, hvis nødvendigt
                card.setNames(convertListToString(cardData.get("names")));

                card.setManaCost((String) cardData.get("manaCost"));
                card.setCmc(cardData.get("cmc") != null ? ((Number) cardData.get("cmc")).doubleValue() : 0.0);

                // Cast korrekt til List<String>
                card.setColors((List<String>) cardData.get("colors"));
                card.setColorIdentity((List<String>) cardData.get("colorIdentity"));
                card.setSupertypes((List<String>) cardData.get("supertypes"));
                card.setTypes((List<String>) cardData.get("types"));
                card.setSubtypes((List<String>) cardData.get("subtypes"));
                card.setPrintings((List<String>) cardData.get("printings"));

                card.setType((String) cardData.get("type"));
                card.setRarity((String) cardData.get("rarity"));
                card.setText((String) cardData.get("text"));
                card.setOriginalText((String) cardData.get("originalText"));
                card.setFlavor((String) cardData.get("flavor"));
                card.setArtist((String) cardData.get("artist"));
                card.setNumber((String) cardData.get("number"));
                card.setPower((String) cardData.get("power"));
                card.setToughness((String) cardData.get("toughness"));
                card.setLoyalty((String) cardData.get("loyalty"));

                // Brug convertToInt til at undgå typefejl
                card.setMultiverseid(convertToInt(cardData.get("multiverseid")));
                card.setHand(convertToInt(cardData.get("hand")));
                card.setLife(convertToInt(cardData.get("life")));

                // Konverter "variations" fra liste til streng, hvis nødvendigt
                card.setVariations(convertListToString(cardData.get("variations")));

                card.setImageName((String) cardData.get("imageName"));
                card.setWatermark((String) cardData.get("watermark"));
                card.setBorder((String) cardData.get("border"));
                card.setTimeshifted(cardData.get("timeshifted") != null && (boolean) cardData.get("timeshifted"));
                card.setReserved(cardData.get("reserved") != null && (boolean) cardData.get("reserved"));
                card.setReleaseDate((String) cardData.get("releaseDate"));
                card.setStarter(cardData.get("starter") != null && (boolean) cardData.get("starter"));
                card.setSet((String) cardData.get("set"));
                card.setSetName((String) cardData.get("setName"));
                card.setImageUrl((String) cardData.get("imageUrl"));

                cardRepository.save(card);
            }
        }
    }

    private int convertToInt(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return -1; // Eller en anden standardværdi
            }
        }
        return -1; // Standardværdi, hvis typen ikke kan håndteres
    }

    private String convertListToString(Object value) {
        if (value instanceof List) {
            return ((List<?>) value).stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
        } else if (value instanceof String) {
            return (String) value;
        }
        return null; // Eller en standardværdi, hvis ingen af delene
    }
}