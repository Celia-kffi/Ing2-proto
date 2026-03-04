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
            const response = await axios.post(`${API_BASE_URL}/calculate`, requestBody);
            return response.data;
        } catch (error) {
            console.error('Erreur lors du calcul de l\'itinéraire:', error);
            throw error;
        }
    }
};

export default activitesApi;