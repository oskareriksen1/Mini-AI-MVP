// Simuleret kortdata - dette ville normalt blive hentet fra jeres API
const cards = [
    { name: "Green Elf", mana: ["green"], type: "creature", effect: "Add one green mana to your pool." },
    { name: "Forest Bear", mana: ["green"], type: "creature", effect: "This creature gains +1/+1 if you control a forest." },
    { name: "Woodland Sprite", mana: ["green"], type: "creature", effect: "Flying. Gains +1 attack when in forest terrain." },
    { name: "Wild Beast", mana: ["green"], type: "creature", effect: "Trample. When this creature deals damage, gain life equal to its power." },
    { name: "Nature's Guardian", mana: ["green"], type: "creature", effect: "When this creature enters the battlefield, create a 1/1 green saproling token." },
    { name: "Treefolk Elder", mana: ["green"], type: "creature", effect: "Prevent all damage to this creature from red sources." },
    { name: "Emerald Dragon", mana: ["green"], type: "creature", effect: "Flying and trample. Gains +2/+2 if you control two or more forests." },
    { name: "Jungle Cat", mana: ["green"], type: "creature", effect: "First strike. Gains +1 attack against creatures with flying." },
    { name: "Ancient Elk", mana: ["green"], type: "creature", effect: "At the beginning of your upkeep, add one green mana to your pool." },
    { name: "Moss Hydra", mana: ["green"], type: "creature", effect: "This creature has hexproof as long as it's untapped." },
    { name: "Lurking Panther", mana: ["green"], type: "creature", effect: "Flash. When it enters the battlefield, target creature gains hexproof until end of turn." },
    { name: "Vine Strangler", mana: ["green"], type: "creature", effect: "Deathtouch. Gains +2 defense in forest." },
    { name: "Forest Stag", mana: ["green"], type: "creature", effect: "Vigilance. Gains lifelink when attacking." },
    { name: "Overgrown Wurm", mana: ["green"], type: "creature", effect: "Trample. Gains +3/+3 when blocked by a creature with power 3 or less." },
    { name: "Feral Wolf", mana: ["green"], type: "creature", effect: "This creature gains +1/+0 for each other green creature you control." },
    { name: "Nettle Dryad", mana: ["green"], type: "creature", effect: "Reach. Prevents damage from black creatures." },
    { name: "Thorn Elemental", mana: ["green"], type: "creature", effect: "You may have Thorn Elemental deal its combat damage as though it weren't blocked." },
    { name: "Sylvan Beastmaster", mana: ["green"], type: "creature", effect: "When this creature enters the battlefield, create a 2/2 green bear token." },
    { name: "Timber Guardian", mana: ["green"], type: "creature", effect: "This creature gains +1/+1 for each forest you control." },
    { name: "Giant Stag", mana: ["green"], type: "creature", effect: "When this creature attacks, you gain 2 life." },
    { name: "Wild Tracker", mana: ["green"], type: "creature", effect: "Whenever another green creature enters the battlefield under your control, draw a card." },
    { name: "Leaf Sentinel", mana: ["green"], type: "creature", effect: "Defender. Gains +0/+2 for each forest you control." },
    { name: "Jungle Guardian", mana: ["green"], type: "creature", effect: "Hexproof. Prevent all damage to this creature from black creatures." },
    { name: "Sapling Protector", mana: ["green"], type: "creature", effect: "When this creature dies, create a 1/1 green saproling creature token." },
    { name: "Thorn Bearer", mana: ["green"], type: "creature", effect: "Reach. Gains +1/+1 against creatures with flying." },
    { name: "Green Wyvern", mana: ["green"], type: "creature", effect: "Flying. Deals double damage to blue creatures." },
    { name: "Forest Charger", mana: ["green"], type: "creature", effect: "Trample. Gains +2 attack when attacking." },
    { name: "Vine Shambler", mana: ["green"], type: "creature", effect: "Regenerate: Pay 1 green mana to regenerate this creature." },
    { name: "Briar Thorn", mana: ["green"], type: "creature", effect: "Deals 1 damage to any target when it enters the battlefield." },
    { name: "Elvish Scout", mana: ["green"], type: "creature", effect: "Tap: Add one green mana to your mana pool." },
    { name: "Fanged Viper", mana: ["green"], type: "creature", effect: "Deathtouch. When it deals damage to a creature, destroy that creature." },
    { name: "Grove Protector", mana: ["green"], type: "creature", effect: "Other green creatures you control get +0/+1." },
    { name: "Moss Troll", mana: ["green"], type: "creature", effect: "Regenerate. Pay 2 green mana to regenerate this creature." },
    { name: "Herbal Healer", mana: ["green"], type: "creature", effect: "Tap: Gain 1 life for each green creature you control." },
    { name: "Wild Huntress", mana: ["green"], type: "creature", effect: "Reach. Gains +1 attack against creatures with flying." },
    { name: "Elder Grovekeeper", mana: ["green"], type: "creature", effect: "If you control three or more forests, this creature has hexproof." },
    { name: "Nimble Squirrel", mana: ["green"], type: "creature", effect: "When this creature deals damage to a player, draw a card." },
    { name: "Woodland Predator", mana: ["green"], type: "creature", effect: "Trample. Gains +2/+2 if you control a mountain." },
    { name: "Treefolk Shaman", mana: ["green"], type: "creature", effect: "Whenever a creature dies, you gain 1 life." },
    { name: "Ancient Sage", mana: ["green"], type: "creature", effect: "Draw a card each time you cast a green spell." },
    { name: "Thicket Warrior", mana: ["green"], type: "creature", effect: "First strike. Gains +2 attack in forest." },
    { name: "Vine Lurker", mana: ["green"], type: "creature", effect: "Can only be blocked by creatures with power 3 or greater." },
    { name: "Bramble Creeper", mana: ["green"], type: "creature", effect: "Reach. Gains +1 defense for each forest you control." },
    { name: "Oakheart Guardian", mana: ["green"], type: "creature", effect: "When this creature enters the battlefield, gain 2 life." },
    { name: "Hollow Grove Spirit", mana: ["green"], type: "creature", effect: "This creature is unblockable if you control a forest." },
    { name: "Timber Stalker", mana: ["green"], type: "creature", effect: "Deathtouch. When it kills a creature, gain 1 life." },
    { name: "Elven Pathfinder", mana: ["green"], type: "creature", effect: "When this creature attacks, reveal the top card of your library." },
    { name: "Woodland Enforcer", mana: ["green"], type: "creature", effect: "Trample. Whenever this creature deals combat damage, you gain that much life." },
    { name: "Barkhide Troll", mana: ["green"], type: "creature", effect: "Regenerate. Pay 1 green mana to regenerate this creature." },
    { name: "Forest Elemental", mana: ["green"], type: "creature", effect: "Gains +1/+1 for each green creature on the battlefield." },
    { name: "Grizzly Oak", mana: ["green"], type: "creature", effect: "Whenever this creature attacks, you gain 1 life." },
];

function applyFilters() {
    // Hent brugerens filtervalg
    const selectedMana = document.getElementById("mana-filter").value;
    const selectedType = document.getElementById("type-filter").value;
    const effectKeyword = document.getElementById("effect-filter").value.toLowerCase();

    // Filtrer kortene
    const filteredCards = cards.filter(card => {
        // Mana-filter: Tjek om kortets mana indeholder den valgte mana-type, eller hvis "All Mana" er valgt
        const manaMatch = !selectedMana || card.mana.includes(selectedMana);

        // Type-filter: Tjek om kortets type matcher den valgte type, eller hvis "All Types" er valgt
        const typeMatch = !selectedType || card.type === selectedType;

        // Effekt-filter: Tjek om effektbeskrivelsen indeholder det indtastede nøgleord
        const effectMatch = !effectKeyword || card.effect.toLowerCase().includes(effectKeyword);

        return manaMatch && typeMatch && effectMatch;
    });

    // Opdater visningen
    displayCards(filteredCards);
}

function displayCards(cards) {
    const container = document.getElementById("cards-container");
    container.innerHTML = ""; // Ryd tidligere visning

    if (cards.length === 0) {
        container.innerHTML = "<p>No cards match your criteria.</p>";
        return;
    }

    cards.forEach(card => {
        const cardElement = document.createElement("div");
        cardElement.classList.add("card");
        cardElement.innerHTML = `
            <h3>${card.name}</h3>
            <p>Mana: ${card.mana.join(", ")}</p>
            <p>Type: ${card.type}</p>
            <p>Effect: ${card.effect}</p>
        `;
        container.appendChild(cardElement);
    });
}
