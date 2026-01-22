const API_BASE_URL = "http://172.31.253.128:8081/api";

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
