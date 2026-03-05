import React from 'react';
import '../styles/MultiDayItineraryResult.css';

const MultiDayItineraryResult = ({ multiDayData }) => {

    if (!multiDayData) {
        return (
            <div className="multiday-result">
                <h2>Planning Multi-jours</h2>
                <div className="empty-state">
                    <p>Selectionnez des activites et calculez pour voir le planning</p>
                </div>
            </div>
        );
    }

    const { nbJours, jours, distanceTotaleKm, dureeTotaleMinutes } = multiDayData;

    const formatDuration = (minutes) => {
        const hours = Math.floor(minutes / 60);
        const mins = minutes % 60;
        return hours > 0 ? `${hours}h ${mins}min` : `${mins}min`;
    };

    const getColorForDay = (dayNumber) => {
        const colors = ['#1976d2', '#388e3c', '#d32f2f', '#f57c00', '#7b1fa2'];
        return colors[(dayNumber - 1) % colors.length];
    };

    return (
        <div className="multiday-result">
            <h2>Planning sur {nbJours} jour{nbJours > 1 ? 's' : ''}</h2>

            <div className="summary-box">
                <div className="summary-item">
                    <span className="label">Distance totale :</span>
                    <span className="value">{distanceTotaleKm} km</span>
                </div>
                <div className="summary-item">
                    <span className="label">Duree totale :</span>
                    <span className="value">{formatDuration(dureeTotaleMinutes)}</span>
                </div>
            </div>

            <div className="days-container">
                {jours.map((jour) => (
                    <div key={jour.numeroJour} className="day-card">
                        <div
                            className="day-header"
                            style={{ borderLeftColor: getColorForDay(jour.numeroJour) }}
                        >
                            <h3>
                <span
                    className="day-badge"
                    style={{ backgroundColor: getColorForDay(jour.numeroJour) }}
                >
                  Jour {jour.numeroJour}
                </span>
                            </h3>
                            <div className="day-info">
                                <span>{formatDuration(jour.dureeTotaleMinutes)}</span>
                                <span className="separator">•</span>
                                <span>{jour.distanceTotaleKm} km</span>
                            </div>
                        </div>

                        <div className="activities-list">
                            {jour.activites.map((activite) => (
                                <div key={activite.activiteId} className="activity-item">
                                    <div className="activity-number">{activite.ordre}</div>
                                    <div className="activity-details">
                                        <h4>{activite.nom}</h4>
                                        <p>Duree : {activite.dureeVisiteMinutes} min</p>
                                        {activite.distanceDepuisPrecedenteKm && (
                                            <p className="travel-info">
                                                {activite.distanceDepuisPrecedenteKm.toFixed(2)} km • {activite.tempsTrajetDepuisPrecedentMinutes} min de trajet
                                            </p>
                                        )}
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MultiDayItineraryResult;