const API_BASE_URL = "http://localhost:8080/api";

// Récupérer tous les profils
export async function getProfils() {
    const response = await fetch(`${API_BASE_URL}/profils`);
    return response.json();
}

// Récupérer les recommandations pour un profil
export async function getRecommendations(profilId) {
    const response = await fetch(
        `${API_BASE_URL}/recommendations?profilId=${profilId}`
    );
    return response.json();
}
