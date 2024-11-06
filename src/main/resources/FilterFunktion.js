async function loadAllCards() {
    try {
        // Sørg for, at URL'en peger på den korrekte backend-port
        const response = await fetch("http://localhost:8080/api/cards/all");
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        const cards = await response.json();
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

// Hent alle kort, når siden indlæses
window.onload = loadAllCards;
