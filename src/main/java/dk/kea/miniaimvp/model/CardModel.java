package dk.kea.miniaimvp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CardModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String layout;
    private String name;
    private String names;
    private String manaCost;
    private double cmc;

    @ElementCollection
    private List<String> colors; // Ændret til liste

    @ElementCollection
    private List<String> colorIdentity; // Ændret til liste

    private String type;

        @ElementCollection
    private List<String> supertypes; // Ændret til liste

    @ElementCollection
    private List<String> types; // Ændret til liste

    @ElementCollection
    private List<String> subtypes; // Ændret til liste

    private String rarity;
    @Column(length = 1000)
    private String text;
    @Column(length = 1000)
    private String originalText;
    private String flavor;
    private String artist;
    private String number;
    private String power;
    private String toughness;
    private String loyalty;
    private int multiverseid = -1;
    private String variations;
    private String imageName;
    private String watermark;
    private String border;
    private boolean timeshifted;
    private int hand;
    private int life;
    private boolean reserved;
    private String releaseDate;
    private boolean starter;

    @Column(name = "card_set") // Brug et andet navn i databasen
    private String set;

    private String setName;

    @ElementCollection
    private List<String> printings; // Ændret til liste

    private String imageUrl;
}