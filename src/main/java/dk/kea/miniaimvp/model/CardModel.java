package dk.kea.miniaimvp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String colors;
    private String colorIdentity;
    private String type;
    private String supertypes;
    private String types;
    private String subtypes;
    private String rarity;
    private String text;
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
    private String set;
    private String setName;
    private String printings;
    private String imageUrl;
}