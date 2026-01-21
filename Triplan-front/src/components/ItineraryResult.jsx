import React from 'react';
import '../styles/ItineraryResult.css';

const ItineraryResult = ({ itineraryData }) => {
    if (!itineraryData) {
        return (
            <div className="itinerary-result">
                <h2>Itinéraire Optimal</h2>
                <p>Sélectionnez au moins 2 activités et cliquez sur "Calculer l'itinéraire"</p>
            </div>
        );
    }

    const { itineraire, distanceTotaleKm, dureeTotaleMinutes } = itineraryData;

    const formatDuration = (minutes) => {
        const hours = Math.floor(minutes / 60);
        const mins = minutes % 60;
        return hours > 0 ? `${hours}h ${mins}min` : `${mins}min`;
    };

    return (
        <div className="itinerary-result">
            <h2>Itinéraire Optimal</h2>

            <div className="summary-section">
                <div>Distance totale : {distanceTotaleKm} km</div>
                <div>Durée totale : {formatDuration(dureeTotaleMinutes)}</div>
            </div>

            <h3>Ordre de visite</h3>
            {itineraire.map((etape) => (
                <div key={etape.activiteId} className="etape-item">
                    <div>
                        <span>#{etape.ordre}</span> <strong>{etape.nom}</strong> ({etape.typeActivite})
                    </div>
                    <div>
                        Durée de visite : {etape.dureeVisiteMinutes} min
                        {etape.distanceDepuisPrecedenteKm !== null && (
                            <div>
                                Distance depuis précédent : {etape.distanceDepuisPrecedenteKm.toFixed(2)} km
                                <br />
                                Temps de trajet : {etape.tempsTrajetDepuisPrecedentMinutes} min
                            </div>
                        )}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ItineraryResult;
