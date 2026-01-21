import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/OptimalItinerary.css';
import api from '../services/api';
import ActivitesList from './ActivitesList';
import ItineraryResult from './ItineraryResult';
import { MESSAGES } from '../constants/config';

function OptimalItinerary() {
    const navigate = useNavigate();
    const [activites, setActivites] = useState([]);
    const [selectedActivites, setSelectedActivites] = useState([]);
    const [selectedPointDepart, setSelectedPointDepart] = useState(null);
    const [itineraryData, setItineraryData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    useEffect(() => {
        loadActivites();
    }, []);

    const loadActivites = async () => {
        try {
            setLoading(true);
            const data = await api.getAllActivites();
            setActivites(data);
            setError(null);
        } catch (err) {
            setError(MESSAGES.ERROR_LOADING);
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleToggleActivite = (id) => {
        if (selectedActivites.includes(id)) {
            setSelectedActivites(selectedActivites.filter(actId => actId !== id));
        } else {
            setSelectedActivites(selectedActivites.concat(id))
        }
    };

    const handleCalculateItinerary = async () => {
        if (selectedActivites.length < 2) {
            alert(MESSAGES.MIN_ACTIVITIES);
            return;
        }
        try {
            setLoading(true);
            setError(null);
            const pointDepart = selectedPointDepart || selectedActivites[0];
            const data = await api.calculateItinerary(selectedActivites, pointDepart);
            setItineraryData(data);
        } catch (err) {
            setError(MESSAGES.ERROR_CALCULATING);
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    const handleReset = () => {
        setSelectedActivites([]);
        setSelectedPointDepart(null);
        setItineraryData(null);
        setError(null);
    };

    return (
        <div className="optimal-itinerary">
            <button
                className="back-button"
                onClick={() => navigate('/')}
            >
                Retour
            </button>

            <header className="header">
                <h1>Planifiez vos Activites</h1>
                <p>Organisez votre itineraire de maniere optimale</p>
            </header>

            {error && <div className="error-banner">{error}</div>}

            <ActivitesList
                activites={activites}
                selectedActivites={selectedActivites}
                onToggleActivite={handleToggleActivite}
            />

            <div className="start-point-selector">
                <label htmlFor="point-depart">Point de depart :</label>
                <select
                    id="point-depart"
                    value={selectedPointDepart || ""}
                    onChange={(e) => setSelectedPointDepart(e.target.value ? parseInt(e.target.value) : null)}
                >
                    <option value="">Par defaut (premiere activite)</option>
                    {activites
                        .filter((act) => selectedActivites.includes(act.id))
                        .map((activite) => (
                            <option key={activite.id} value={activite.id}>
                                {activite.nom}
                            </option>
                        ))}
                </select>
            </div>

            <div className="action-buttons">
                <button
                    className="btn-primary"
                    onClick={handleCalculateItinerary}
                    disabled={loading || selectedActivites.length < 2}
                >
                    {loading ? 'Calcul en cours...' : 'Calculer l\'itineraire'}
                </button>
                <button
                    className="btn-secondary"
                    onClick={handleReset}
                    disabled={loading}
                >
                    Reinitialiser
                </button>
            </div>

            <ItineraryResult itineraryData={itineraryData}/>
        </div>
    );
}

export default OptimalItinerary;