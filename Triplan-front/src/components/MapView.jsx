import React, { useEffect } from 'react';
import { MapContainer, TileLayer, Marker, Popup, Polyline, useMap } from 'react-leaflet';
import L from 'leaflet';
import '../styles/MapView.css';
import icon from 'leaflet/dist/images/marker-icon.png';
import iconShadow from 'leaflet/dist/images/marker-shadow.png';

L.Marker.prototype.options.icon = L.icon({
    iconUrl: icon,
    shadowUrl: iconShadow,
    iconSize: [25, 41],
    iconAnchor: [12, 41]
});

const DAY_COLORS = ['#1976d2', '#388e3c', '#d32f2f', '#f57c00', '#7b1fa2'];

function MapBounds({ positions }) {
    const map = useMap();

    useEffect(() => {
        if (positions.length > 0) {
            map.fitBounds(L.latLngBounds(positions), { padding: [50, 50] });
        }
    }, [positions, map]);

    return null;
}

const createNumberedIcon = (number, color) => L.divIcon({
    html: `<div class="numbered-marker" style="background-color:${color}">${number}</div>`,
    className: 'custom-marker-icon',
    iconSize: [30, 30],
    iconAnchor: [15, 30]
});

const MapView = ({ itineraryData, multiDayData }) => {
    let jours = [];

    if (multiDayData && multiDayData.jours.length > 0) {
        jours = multiDayData.jours;
    } else if (itineraryData && itineraryData.itineraire.length > 0) {
        jours = [{ numeroJour: 1, activites: itineraryData.itineraire }];
    }

    const hasItinerary = jours.length > 0;

    const stats = multiDayData || itineraryData;

    const toutesLesPositions = [];
    for (const jour of jours) {
        for (const activite of jour.activites) {
            toutesLesPositions.push([activite.latitude, activite.longitude]);
        }
    }

    return (
        <div className="map-view">
            <h3>Visualisation de l'itinéraire</h3>

            <MapContainer
                center={[46.6034, 1.8883]}
                zoom={6}
                className="map-container"
                scrollWheelZoom={true}
            >
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                {hasItinerary && (
                    <>
                        <MapBounds positions={toutesLesPositions} />

                        {jours.map((jour, index) => {
                            const color = DAY_COLORS[index % DAY_COLORS.length];
                            const positions = jour.activites.map(a => [a.latitude, a.longitude]);

                            return (
                                <React.Fragment key={jour.numeroJour}>
                                    <Polyline positions={positions} color={color} weight={3} opacity={0.7} />

                                    {jour.activites.map(activite => (
                                        <Marker
                                            key={activite.activiteId}
                                            position={[activite.latitude, activite.longitude]}
                                            icon={createNumberedIcon(activite.ordre, color)}
                                        >
                                            <Popup>
                                                <div className="popup-content">
                                                    <strong>Etape {activite.ordre}</strong>
                                                    {multiDayData && <p>Jour {jour.numeroJour}</p>}
                                                    <h4>{activite.nom}</h4>
                                                    <p>Duree de visite : {activite.dureeVisiteMinutes} min</p>
                                                    {activite.distanceDepuisPrecedenteKm && (
                                                        <>
                                                            <p>Distance depuis precedent : {activite.distanceDepuisPrecedenteKm.toFixed(2)} km</p>
                                                            <p>Temps de trajet : {activite.tempsTrajetDepuisPrecedentMinutes} min</p>
                                                        </>
                                                    )}
                                                </div>
                                            </Popup>
                                        </Marker>
                                    ))}
                                </React.Fragment>
                            );
                        })}
                    </>
                )}
            </MapContainer>

            {hasItinerary ? (
                <div className="map-legend">
                    <p>Distance totale : <strong>{stats.distanceTotaleKm} km</strong></p>
                    <p>Durée totale : <strong>{Math.floor(stats.dureeTotaleMinutes / 60)}h {stats.dureeTotaleMinutes % 60}min</strong></p>
                    {multiDayData && <p>Nombre de jours : <strong>{multiDayData.nbJours}</strong></p>}
                </div>
            ) : (
                <div className="map-info">
                    <p>Sélectionnez des activités et calculez l'itinéraire pour voir le parcours sur la carte</p>
                </div>
            )}
        </div>
    );
};

export default MapView;