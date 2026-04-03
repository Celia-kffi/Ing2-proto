import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import '../styles/TripBudget.css';

function TripBudget() {
    const navigate = useNavigate();
    const location = useLocation();

    const { ville, nbJours, activitesSelectionnees, distanceTotale, hebergementChoisi } = location.state || {};
    const [activitesLocales, setActivitesLocales] = useState(activitesSelectionnees || []);
    const [budget, setBudget] = useState('');

    const handleSupprimer = (id) => {
        setActivitesLocales(prev => prev.filter(a => a.id !== id));
    };

    // Calcul coût activités
    const coutActivites = activitesLocales.reduce((total, a) => total + (a.prix || 0), 0);

    // Calcul coût hébergement
    const coutHebergement = hebergementChoisi
        ? hebergementChoisi.prixNuit * nbJours
        : 0;

    // Coût total
    const coutTotal = coutActivites + coutHebergement;

    // Comparaison budget
    const budgetNum = parseFloat(budget) || 0;
    const reste = budgetNum - coutTotal;

    return (
        <div className="cout-voyage">
            <button className="back-button" onClick={() => navigate(-1)}>Retour</button>

            <header className="header">
                <h1>Coût estimé du voyage</h1>
                {ville && <p>Ville : <strong>{ville}</strong> | {nbJours} jour{nbJours > 1 ? 's' : ''}</p>}
            </header>

            <div className="cout-container">

                <div className="cout-card">
                    <h2>Activités</h2>
                    <div className="cout-liste">
                        {activitesLocales.map(a => (
                            <div key={a.id} className="cout-ligne">
                                <span>{a.nom}</span>
                                <span>{a.prix ? `${a.prix} €` : 'Gratuit'}</span>
                                <button
                                    className="btn-supprimer-activite"
                                    onClick={() => handleSupprimer(a.id)}
                                    title="Retirer cette activité"
                                >
                                    ✕
                                </button>
                            </div>
                        ))}
                    </div>
                    <div className="cout-sous-total">
                        <strong>Sous-total : {coutActivites} €</strong>
                    </div>
                </div>

                <div className="cout-card">
                    <h2>Hébergement</h2>
                    {hebergementChoisi ? (
                        <div className="hebergement-detail">
                            <p><strong>{hebergementChoisi.nom}</strong></p>
                            <p>{hebergementChoisi.prixNuit} € × {nbJours} nuits = <strong>{coutHebergement} €</strong></p>
                        </div>
                    ) : (
                        <p className="no-hebergement">Aucun hébergement choisi</p>
                    )}
                    <div className="cout-sous-total">
                        <strong>Sous-total : {coutHebergement} €</strong>
                    </div>
                </div>
            </div>

            <div className="cout-repartition">
                <h2>Répartition des coûts</h2>
                <div className="repartition-lignes">
                    <div className="repartition-ligne">
                        <span>Activités</span>
                        <div className="barre-container">
                            <div
                                className="barre activites"
                                style={{ width: coutTotal > 0 ? `${(coutActivites / coutTotal) * 100}%` : '0%' }}
                            />
                        </div>
                        <span>{coutActivites} €</span>
                    </div>
                    <div className="repartition-ligne">
                        <span>Hébergement</span>
                        <div className="barre-container">
                            <div
                                className="barre hebergement"
                                style={{ width: coutTotal > 0 ? `${(coutHebergement / coutTotal) * 100}%` : '0%' }}
                            />
                        </div>
                        <span>{coutHebergement} €</span>
                    </div>
                </div>
                <div className="cout-total">
                    Total estimé : <strong>{coutTotal} €</strong>
                </div>
            </div>

            <div className="cout-budget">
                <h2>Comparer avec mon budget</h2>
                <div className="budget-input">
                    <label>Mon budget :</label>
                    <input
                        type="number"
                        value={budget}
                        onChange={(e) => setBudget(e.target.value)}
                        placeholder="ex: 500"
                    />
                    <span>€</span>
                </div>

                {budgetNum > 0 && (
                    <div className={`budget-resultat ${reste >= 0 ? 'positif' : 'negatif'}`}>
                        <p>Coût estimé : <strong>{coutTotal} €</strong></p>
                        <p>Votre budget : <strong>{budgetNum} €</strong></p>
                        <p className="reste">
                            {reste >= 0
                                ? ` Il vous reste ${reste.toFixed(2)} €`
                                : ` Dépassement de ${Math.abs(reste).toFixed(2)} €`
                            }
                        </p>
                    </div>
                )}
            </div>

            <div className="carbon">
                <button
                    className="btn-carbone"
                    onClick={() => navigate(`/carbon?distance=${distanceTotale}`)}
                    disabled={!distanceTotale}
                >
                     Calculer l'empreinte carbone de mon voyage
                </button>
            </div>
        </div>
    );
}

export default TripBudget;