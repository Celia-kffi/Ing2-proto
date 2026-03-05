import { useState } from "react";
import "../styles/ProfilForm.css";
import { useNavigate } from "react-router-dom";

const API_BASE_URL = "http://172.31.253.128:8081";

function ProfilForm({ onRetour }) {

    const [environnement, setEnvironnement] = useState("");
    const [activite, setActivite] = useState("");
    const [budget, setBudget] = useState("");
    const [duree, setDuree] = useState("");
    const [experience, setExperience] = useState("");
    const [saison, setSaison] = useState("");
    const [compagnie, setCompagnie] = useState("");
    const [confort, setConfort] = useState("");

    const [voyages, setVoyages] = useState([]);
    const [loading, setLoading] = useState(false);
    const [erreur, setErreur] = useState(null);
    const [voyageSelectionne, setVoyageSelectionne] = useState(null);

    const [hebergements, setHebergements] = useState([]);
    const [hebergementsVoyage, setHebergementsVoyage] = useState(null);
    const [loadingHebergements, setLoadingHebergements] = useState(false);

    const navigate = useNavigate();

    async function handleSubmit(e) {
        e.preventDefault();
        setLoading(true);
        setErreur(null);

        const profilData = {
            environnement,
            activite,
            budget,
            duree,
            experience,
            saison,
            compagnie,
            confort
        };

        try {
            const response = await fetch(API_BASE_URL + "/api/recommandations", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(profilData)
            });

            if (!response.ok) {
                throw new Error("Erreur " + response.status);
            }

            const data = await response.json();
            setVoyages(data);
        } catch (err) {
            setErreur("Erreur lors de la recuperation des recommandations.");
        } finally {
            setLoading(false);
        }
    }

    function selectionnerVoyage(voyage) {
        setVoyageSelectionne(voyage);
        fetch(API_BASE_URL + "/api/recommandations/selectionner", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ voyageId: voyage.id })
        });
    }

    async function voirHebergements(voyage) {
        setHebergementsVoyage(voyage);
        setLoadingHebergements(true);
        setHebergements([]);

        try {
            const ville = voyage.destination || voyage.ville || voyage.pays;
            const response = await fetch(API_BASE_URL + "/api/hebergements?ville=" + encodeURIComponent(ville));

            if (response.ok) {
                const data = await response.json();
                setHebergements(data);
            }
        } catch (err) {
            console.error("Erreur hebergements", err);
        } finally {
            setLoadingHebergements(false);
        }
    }


    const fields = [environnement, activite, budget, duree, experience, saison, compagnie, confort];
    const filled = fields.filter(f => f !== "").length;
    const progress = Math.round((filled / fields.length) * 100);

    return (
        <div className="form-wrapper">

            <div className="form-header">
                <h2>Profil du voyageur</h2>
                <p>Repondez aux questions pour obtenir une recommandation personnalisee</p>

                <div className="progress-bar-wrap">
                    <div className="progress-bar-fill" style={{ width: progress + "%" }} />
                </div>
                <span className="progress-label">{filled} / {fields.length} reponses</span>
            </div>

            <form onSubmit={handleSubmit} className="profil-form">

                <Question number="1" title="Environnement prefere"
                          value={environnement} setValue={setEnvironnement}
                          options={[
                              { value: "Montagne" },
                              { value: "Mer" },
                              { value: "Ville" }
                          ]} />

                <Question number="2" title="Activite favorite"
                          value={activite} setValue={setActivite}
                          options={[
                              { value: "Sports" },
                              { value: "Musees" },
                              { value: "Spa" }
                          ]} />

                <Question number="3" title="Budget"
                          value={budget} setValue={setBudget}
                          options={[
                              { value: "Petit", label: "Petit budget" },
                              { value: "Moyen", label: "Budget moyen" },
                              { value: "Gros", label: "Gros budget" }
                          ]} />

                <Question number="4" title="Duree du voyage"
                          value={duree} setValue={setDuree}
                          options={[
                              { value: "Weekend", label: "Week-end" },
                              { value: "Semaine", label: "1 semaine" },
                              { value: "Long", label: "2 semaines ou +" }
                          ]} />

                <Question number="5" title="Type d'experience"
                          value={experience} setValue={setExperience}
                          options={[
                              { value: "Aventure" },
                              { value: "Relaxation" },
                              { value: "Culture" }
                          ]} />

                <Question number="6" title="Saison preferee"
                          value={saison} setValue={setSaison}
                          options={[
                              { value: "Ete", label: "Ete" },
                              { value: "Hiver" },
                              { value: "Printemps", label: "Printemps / Automne" }
                          ]} />

                <Question number="7" title="Vous voyagez"
                          value={compagnie} setValue={setCompagnie}
                          options={[
                              { value: "Seul", label: "Seul(e)" },
                              { value: "Couple", label: "En couple" },
                              { value: "Famille", label: "En famille / amis" }
                          ]} />

                <Question number="8" title="Niveau de confort"
                          value={confort} setValue={setConfort}
                          options={[
                              { value: "Simple" },
                              { value: "Confort" },
                              { value: "Luxe" }
                          ]} />

                <div className="form-actions">
                    <button type="button" className="btn-retour" onClick={onRetour}>
                        Retour
                    </button>
                    <button type="submit" className="btn-valider" disabled={filled < fields.length || loading}>
                        {loading ? "Chargement..." : "Valider"}
                    </button>
                </div>

            </form>

            {erreur && <p className="reco-erreur">{erreur}</p>}

            {voyages.length > 0 && (
                <div className="reco-section">
                    <h2 className="reco-titre">Voyages recommandes</h2>

                    <div className="reco-grid">
                        {voyages.map((voyage) => (
                            <div key={voyage.id}
                                 className={"reco-card " + (voyageSelectionne?.id === voyage.id ? "selected" : "")}
                                 onClick={() => selectionnerVoyage(voyage)}>

                                {voyageSelectionne?.id === voyage.id &&
                                    <div className="reco-badge">Selectionne</div>}

                                <div className="reco-theme-tag">{voyage.theme}</div>
                                <h3 className="reco-nom">{voyage.destination}</h3>
                                <p className="reco-pays">{voyage.pays}</p>

                                {voyage.prix && <div className="reco-prix">{voyage.prix} euros / pers.</div>}

                                <div className="reco-actions">
                                    <button type="button" className="btn-hebergements"
                                            onClick={(e) => { e.stopPropagation(); voirHebergements(voyage); }}>
                                        Hebergements
                                    </button>
                                    <button
                                        type="button"
                                        className="btn-activites"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            const ville = voyage.destination || voyage.ville || voyage.pays;
                                            navigate(`/itinerary?ville=${ville}`);
                                        }}>
                                        Planifier les activités
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            {hebergementsVoyage && (
                <div className="modal-overlay" onClick={() => setHebergementsVoyage(null)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <button className="modal-close" onClick={() => setHebergementsVoyage(null)}>X</button>

                        <h2>Hebergements a {hebergementsVoyage.destination || hebergementsVoyage.ville}</h2>

                        {loadingHebergements && <p className="loading-text">Chargement...</p>}

                        {!loadingHebergements && hebergements.length === 0 && (
                            <p className="no-results">Aucun hebergement trouve.</p>
                        )}

                        <div className="hebergements-grid">
                            {hebergements.map((h) => (
                                <div key={h.id} className="hebergement-card">
                                    <div className="hebergement-type">{h.type}</div>
                                    <h3>{h.destination}</h3>
                                    {h.nbEtoiles && <div className="hebergement-etoiles">{h.nbEtoiles} etoiles</div>}
                                    <p className="hebergement-lieu">{h.ville || h.destination}, {h.pays}</p>
                                    <div className="hebergement-prix">{h.prixNuit} {h.devise} / nuit</div>
                                    {h.niveauConfort && <span className="hebergement-confort">{h.niveauConfort}</span>}
                                </div>
                            ))}
                        </div>
                    </div>
                </div>
            )}

        </div>
    );
}

function Question({ number, title, value, setValue, options }) {
    return (
        <div className="question-block">
            <h3><span className="q-num">{number}</span> {title}</h3>
            <div className="options-row">
                {options.map(opt => (
                    <label key={opt.value} className={"option-card " + (value === opt.value ? "selected" : "")}>
                        <input type="radio" value={opt.value} checked={value === opt.value}
                               onChange={(e) => setValue(e.target.value)} />
                        <span className="opt-label">{opt.label || opt.value}</span>
                    </label>
                ))}
            </div>
        </div>
    );
}

export default ProfilForm;
