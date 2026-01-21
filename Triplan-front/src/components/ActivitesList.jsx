import React from 'react';
import '../styles/ActivitesList.css';

const ActivitesList = ({ activites, selectedActivites, onToggleActivite }) => {
    return (
        <div className="activites-list">
            <h2>Activités disponibles</h2>
            <p className="selection-count">{selectedActivites.length} sélectionnée(s)</p>

            <div className="activites-container">
                {activites.map((activite) => {
                    const isSelected = selectedActivites.includes(activite.id);

                    return (
                        <div
                            key={activite.id}
                            className={`activite-item ${isSelected ? 'selected' : ''}`}
                            onClick={() => onToggleActivite(activite.id)}
                        >
                            <input
                                type="checkbox"
                                checked={isSelected}
                                onChange={() => {}}
                                className="activite-checkbox"
                            />
                            <div className="activite-info">
                                <strong>{activite.nom}</strong>
                                <div className="activite-details">
                                    <span>{activite.dureeVisiteMinutes} min</span>
                                    <span className="separator">•</span>
                                    <span>{activite.typeActivite}</span>
                                </div>
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default ActivitesList;
