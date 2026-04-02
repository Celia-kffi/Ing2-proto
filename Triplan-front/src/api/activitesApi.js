import axios from 'axios';
import { API_BASE_URL } from '../constants/config';

const activitesApi = {
    getAllActivites: async () => {
        try {
            const response = await axios.get(`${API_BASE_URL}/activites`);
            return response.data;
        } catch (error) {
            console.error('Erreur lors de la récupération des activités:', error);
            throw error;
        }
    },

    calculateItinerary: async (activiteIds, pointDepartId = null) => {
        try {
            const requestBody = {
                activiteIds: activiteIds,
                pointDepartId: pointDepartId
            };
            console.log("Body: ", requestBody);
            const response = await axios.post(`${API_BASE_URL}/calculate`, requestBody);
            return response.data;
        } catch (error) {
            console.error('Erreur lors du calcul de l\'itinéraire:', error);
            throw error;
        }
    },

    calculateMultiDayItinerary: async (activiteIds, pointDepartId = null, nbJours = 1) => {
        try {
            const requestBody = {
                activiteIds: activiteIds,
                pointDepartId: pointDepartId,
                nbJours: nbJours
            };
            const response = await axios.post(`${API_BASE_URL}/calculate-multi-days`, requestBody);
            return response.data;
        } catch (error) {
            console.error('Erreur lors du calcul de l\'itinéraire multi-jours:', error);
            throw error;
        }
    },
    getDerniereVilleRecommandee: async (clientId) => {
        const response = await axios.get(`${API_BASE_URL}/recommandations/derniere-ville/${clientId}`);
        return response.data;
    },

    getActivitesByVille: async (ville) => {
        const response = await axios.get(`${API_BASE_URL}/activites/ville/${ville}`);
        return response.data;
    },
};

export default activitesApi;