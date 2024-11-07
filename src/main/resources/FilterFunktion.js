async function loadAllCards() {
    try {
        const response = await fetch("http://localhost:8080/api/cards/all");
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const cards = await response.json();
        allCards = cards;  // Gemmer alle kort i en variabel
        displayCards(cards);
    } catch (error) {
        console.error("Error fetching all cards:", error);
    }
}


async function applyFilters() {
    const selectedMana = document.getElementById("mana-filter").value;
    const selectedType = document.getElementById("type-filter").value;
    const effectKeyword = document.getElementById("effect-filter").value;

    const queryParams = new URLSearchParams();
    if (selectedMana) queryParams.append("mana", selectedMana);
    if (selectedType) queryParams.append("type", selectedType);
    if (effectKeyword) queryParams.append("effect", effectKeyword);

    try {
        // Opdater URL'en til at pege på backend-serveren
        const response = await fetch(`http://localhost:8080/api/cards/filter?${queryParams.toString()}`);
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const cards = await response.json();
        displayCards(cards);
    } catch (error) {
        console.error("Error fetching filtered cards:", error);
    }
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
            <img src="${card.imageUrl}" alt="${card.name}" class="card-image" /> <!-- Billede -->
            <h3>${card.name}</h3>
            <p>Mana: ${card.colors.join(", ")}</p>
            <p>Type: ${card.type}</p>
            <p>Effect: ${card.text}</p>
        `;
        container.appendChild(cardElement);
    });

}
function toggleDropdown() {
    document.getElementById("color-menu").classList.toggle("show");
}

// Klik uden for dropdown for at lukke den
window.onclick = function(event) {
    if (!event.target.matches('#color-dropdown')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        for (var i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}

async function generateDeck() {

    const loadingIcon = document.getElementById("loadingIcon");
    loadingIcon.style.display = "inline-block"; // Vis spinneren
    document.body.style.cursor = "wait"; // Skift muse-ikon


    // Hent de valgte farver fra checkboxes
    const selectedColors = Array.from(document.querySelectorAll('.color-option:checked')).map(cb => cb.value);
    const deckType = document.getElementById("deck-type").value;

    // Deck request med specifikke kort fra databasen
    const deckRequest = {
        colors: selectedColors,
        deckType: deckType,
        availableCards: allCards  // Sender hele listen af kort til backend
    };

    try {
        const response = await fetch("http://localhost:8080/api/v1/rank/generate", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(deckRequest)
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const deck = await response.json();
        displayDeck(deck.content); // Brug deck.content for at vise resultatet
    } catch (error) {
        console.error("Error generating deck:", error);
    } finally {
        // Skjul spinneren og nulstil muse-ikon
        loadingIcon.style.display = "none";
        document.body.style.cursor = "default";
    }
}

function displayDeck(deckContent) {
    const deckResult = document.getElementById("deck-result");
    deckResult.innerHTML = `<p>${deckContent}</p>`;
}
// Hent alle kort, når siden indlæses
window.onload = loadAllCards;
