import React, { useState, useEffect } from 'react';
import '../styles/OptimalItinerary.css';
import activitesApi from '../api/activitesApi';
import ActivitesList from './ActivitesList';
import ItineraryResult from './ItineraryResult';
import MultiDayItineraryResult from './MultiDayItineraryResult';
import MapView from "./MapView";
import { MESSAGES } from '../constants/config';
import { useNavigate, useSearchParams } from 'react-router-dom';

function OptimalItinerary() {
    const navigate = useNavigate();
    const [activites, setActivites] = useState([]);
    const [selectedActivites, setSelectedActivites] = useState([]);
    const [selectedPointDepart, setSelectedPointDepart] = useState(null);
    const [itineraryData, setItineraryData] = useState(null);
    const [multiDayData, setMultiDayData] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [isMultiDay, setIsMultiDay] = useState(false);
    const [nbJours, setNbJours] = useState(2);
    const [villeRecommandee, setVilleRecommandee] = useState(null);
    const [searchParams] = useSearchParams();

    useEffect(() => {
        loadVilleEtActivites();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const loadVilleEtActivites = async () => {
        try {
            setLoading(true);
            const data = await activitesApi.getAllActivites();
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

    const handleSupprimerActivite = (id) => {
        setActivites(prev => prev.filter(a => a.id !== id));
        setSelectedActivites(prev => prev.filter(actId => actId !== id));
        setItineraryData(null);
        setMultiDayData(null);
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

            if (isMultiDay) {
                const data = await activitesApi.calculateMultiDayItinerary(
                    selectedActivites, pointDepart, nbJours
                );
                setMultiDayData(data);
                setItineraryData(null);
            } else {
                const data = await activitesApi.calculateItinerary(selectedActivites, pointDepart);
                setItineraryData(data);
                setMultiDayData(null);
            }
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
        setMultiDayData(null);
        setError(null);
    };

    const distanceTotale = itineraryData?.distanceTotaleKm || multiDayData?.distanceTotaleKm;

    return (
        <div className="optimal-itinerary">
            <button className="back-button" onClick={() => navigate('/')}>Retour</button>
            <header className="header">
                <h1>Planifiez vos Activites</h1>
                <p>Organisez votre itineraire de maniere optimale</p>
                {villeRecommandee && (
                    <div className="ville-recommandee-banner">
                        <div>
                            <p className="ville-label">Ville recommandée</p>
                            <h2 className="ville-nom">{villeRecommandee}</h2>
                        </div>
                    </div>
                )}
            </header>

            {error && <div className="error-banner">{error}</div>}

            <div className="top-container">
                <div className="left-panel">
                    <ActivitesList
                        activites={activites}
                        selectedActivites={selectedActivites}
                        onToggleActivite={handleToggleActivite}
                        onSupprimerActivite={handleSupprimerActivite}
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

                    <div className="multi-day-section">
                        <label className="checkbox-label">
                            <input
                                type="checkbox"
                                checked={isMultiDay}
                                onChange={(e) => setIsMultiDay(e.target.checked)}
                            />
                            Planning sur plusieurs jours
                        </label>

                        {isMultiDay && (
                            <div className="nb-jours-input">
                                <label htmlFor="nb-jours">Duree du sejour (jours) :</label>
                                <input
                                    type="number"
                                    id="nb-jours"
                                    min="1"
                                    max="7"
                                    value={nbJours}
                                    onChange={(e) => setNbJours(parseInt(e.target.value) || 1)}
                                />
                            </div>
                        )}
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
                        <button
                            className="btn-carbone"
                            onClick={() => navigate(`/carbon?distance=${distanceTotale}`)}
                            disabled={!distanceTotale}
                        >
                             Calculer l'empreinte carbone
                        </button>
                    </div>
                </div>

                <div className="right-panel">
                    <MapView
                        itineraryData={itineraryData}
                        multiDayData={multiDayData}
                    />
                </div>
            </div>


            {(itineraryData || multiDayData) && (
                <div className="bottom-container">
                    {isMultiDay && multiDayData ? (
                        <MultiDayItineraryResult multiDayData={multiDayData}/>
                    ) : (
                        <ItineraryResult itineraryData={itineraryData}/>
                    )}
                </div>
            )}
        </div>
    );
}

export default OptimalItinerary;