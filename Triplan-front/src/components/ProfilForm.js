import { useState, useEffect } from "react";
import "../styles/ProfilForm.css";
import { useNavigate, useSearchParams } from "react-router-dom";

const API_BASE_URL = "http://172.31.253.128:8081";

export default function ProfilForm({ onRetour }) {
    const navigate = useNavigate();
    const [searchParams, setSearchParams] = useSearchParams();
    const [profil, setProfil] = useState(null);

    // Formulaire — initialise depuis l'URL si deja remplie
    const [dateDebut,     setDateDebut]     = useState(searchParams.get("dateDebut")     || "");
    const [dateFin,       setDateFin]       = useState(searchParams.get("dateFin")       || "");
    const [environnement, setEnvironnement] = useState(searchParams.get("environnement") || "");
    const [activite,      setActivite]      = useState(searchParams.get("activite")      || "");
    const [budget,        setBudget]        = useState(searchParams.get("budget")        || "");
    const [experience,    setExperience]    = useState(searchParams.get("experience")    || "");
    const [compagnie,     setCompagnie]     = useState(searchParams.get("compagnie")     || "");

    // Synchronise l'URL a chaque changement de champ
    useEffect(() => {
        const params = {};

        if (dateDebut)     params.dateDebut     = dateDebut;
        if (dateFin)       params.dateFin       = dateFin;
        if (environnement) params.environnement = environnement;
        if (activite)      params.activite      = activite;
        if (budget)        params.budget        = budget;
        if (experience)    params.experience    = experience;
        if (compagnie)     params.compagnie     = compagnie;

        setSearchParams(params, { replace: true });

    }, [
        dateDebut,
        dateFin,
        environnement,
        activite,
        budget,
        experience,
        compagnie,
        setSearchParams
    ]);
    // Resultats
    const [voyages,           setVoyages]           = useState([]);
    const [voyageSelectionne, setVoyageSelectionne] = useState(null);
    const [loading,           setLoading]           = useState(false);
    const [erreur,            setErreur]            = useState(null);

    // Hebergements
    const [hebergements,       setHebergements]       = useState([]);
    const [hebergementsVoyage, setHebergementsVoyage] = useState(null);
    const [loadingHebergements,setLoadingHebergements]= useState(false);
    const [hebergementChoisi,  setHebergementChoisi]  = useState(null);

    // Charger profil depuis localStorage
    useEffect(() => {
        const stored = localStorage.getItem("user");

        if (!stored) {
            navigate("/login");
            return;
        }

        setProfil(JSON.parse(stored));

    }, [navigate]);

    // Progress bar
    const fields = [dateDebut, dateFin, environnement, activite, budget, experience, compagnie];
    const filled  = fields.filter(f => f !== "").length;
    const progress = Math.round((filled / fields.length) * 100);

    async function handleSubmit(e) {
        e.preventDefault();
        setLoading(true);
        setErreur(null);

        const data = { environnement, activite, budget, experience, compagnie, dateDebut, dateFin };

        try {
            const res = await fetch(API_BASE_URL + "/api/recommandations", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(data)
            });
            if (!res.ok) throw new Error("Erreur " + res.status);
            const result = await res.json();
            setVoyages(result);
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
        setHebergementChoisi(null);

        try {
            const ville = voyage.destination || voyage.pays;
            const res = await fetch(API_BASE_URL + "/api/hebergements?ville=" + encodeURIComponent(ville));
            if (res.ok) {
                const data = await res.json();
                setHebergements(data);
            }
        } catch (err) {
            console.error("Erreur hebergements", err);
        } finally {
            setLoadingHebergements(false);
        }
    }

    function allerItineraire(voyage) {
        const ville = voyage.destination || voyage.pays;

        const nbJours = (dateDebut && dateFin)
            ? Math.max(1, Math.round((new Date(dateFin) - new Date(dateDebut)) / 86400000))
            : 1;

        let url = `/itinerary?ville=${encodeURIComponent(ville)}&nbJours=${nbJours}`;

        if (hebergementChoisi) {
            url += `&hebergementId=${hebergementChoisi.id}`
                + `&hebergementNom=${encodeURIComponent(hebergementChoisi.nom)}`
                + `&hebergementPrix=${hebergementChoisi.prixNuit}`;
        }

        navigate(url);
    }

    return (
        <div className="form-wrapper">

            {/* Carte profil */}
            {profil && (
                <div className="profil-card">
                    <div className="profil-header">
                        <h3>{profil.prenom} {profil.nom}</h3>
                        <p>{profil.adresseMail}</p>
                    </div>
                    <div className="profil-infos">
                        <div><span>Budget</span><p>{profil.budget ?? "—"} €</p></div>
                        <div><span>Duree</span><p>{profil.dureeMoyenne ?? "—"} jours</p></div>
                        <div><span>Experience</span><p>{profil.typeExperience ?? "—"}</p></div>
                        <div><span>Role</span><p>{profil.role ?? "USER"}</p></div>
                    </div>
                </div>
            )}

            <div className="form-header">
                <h2>Profil du voyageur</h2>
                <p>Repondez aux questions pour obtenir une recommandation personnalisee</p>

                <div className="progress-bar-wrap">
                    <div className="progress-bar-fill" style={{ width: progress + "%" }} />
                </div>
                <span className="progress-label">{filled} / {fields.length} reponses</span>
            </div>

            <form onSubmit={handleSubmit} className="profil-form">

                {/* Dates */}
                <div className="question-block">
                    <h3><span className="q-num">1</span> Dates du voyage</h3>
                    <div className="options-row dates-row">
                        <label className="date-label">
                            Depart
                            <input type="date" value={dateDebut}
                                   onChange={e => setDateDebut(e.target.value)} />
                        </label>
                        <label className="date-label">
                            Retour
                            <input type="date" value={dateFin}
                                   onChange={e => setDateFin(e.target.value)} />
                        </label>
                    </div>
                </div>

                <Question number="2" title="Environnement prefere"
                          value={environnement} setValue={setEnvironnement}
                          options={[
                              { value: "Montagne" },
                              { value: "Mer" },
                              { value: "Ville" },
                              { value: "Nature" }
                          ]} />

                <Question number="3" title="Activite favorite"
                          value={activite} setValue={setActivite}
                          options={[
                              { value: "Sport" },
                              { value: "Culture" },
                              { value: "BienEtre", label: "Bien-etre" },
                              { value: "Gastronomie" }
                          ]} />

                <Question number="4" title="Budget"
                          value={budget} setValue={setBudget}
                          options={[
                              { value: "Petit", label: "Petit budget" },
                              { value: "Moyen", label: "Budget moyen" },
                              { value: "Confortable" },
                              { value: "Luxe" }
                          ]} />

                <Question number="5" title="Type d'experience"
                          value={experience} setValue={setExperience}
                          options={[
                              { value: "Frisson" },
                              { value: "Serenite" },
                              { value: "Depaysement" },
                              { value: "Confort" }
                          ]} />

                <Question number="6" title="Vous voyagez"
                          value={compagnie} setValue={setCompagnie}
                          options={[
                              { value: "Seul",   label: "Seul(e)" },
                              { value: "Couple", label: "En couple" },
                              { value: "Famille",label: "En famille" },
                              { value: "Amis",   label: "Entre amis" }
                          ]} />

                <div className="form-actions">
                    {onRetour && (
                        <button type="button" className="btn-retour" onClick={onRetour}>
                            Retour
                        </button>
                    )}
                    <button type="submit" className="btn-valider"
                            disabled={filled < fields.length || loading}>
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

                                {voyage.prix && (
                                    <div className="reco-prix">{voyage.prix} euros / pers.</div>
                                )}

                                <div className="reco-actions">
                                    <button type="button" className="btn-hebergements"
                                            onClick={(e) => { e.stopPropagation(); voirHebergements(voyage); }}>
                                        Hebergements
                                    </button>
                                    <button type="button" className="btn-activites"
                                            onClick={(e) => { e.stopPropagation(); allerItineraire(voyage); }}>
                                        Planifier les activites
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            {/* Modale hebergements */}
            {hebergementsVoyage && (
                <div className="modal-overlay" onClick={() => setHebergementsVoyage(null)}>
                    <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                        <button className="modal-close" onClick={() => setHebergementsVoyage(null)}>X</button>

                        <h2>Hebergements a {hebergementsVoyage.destination || hebergementsVoyage.ville}</h2>

                        {hebergementChoisi && (
                            <p className="hebergement-selectionne-info">
                                Selectionne : <strong>{hebergementChoisi.nom}</strong> — {hebergementChoisi.prixNuit} € / nuit
                            </p>
                        )}

                        {loadingHebergements && <p className="loading-text">Chargement...</p>}

                        {!loadingHebergements && hebergements.length === 0 && (
                            <p className="no-results">Aucun hebergement trouve.</p>
                        )}

                        <div className="hebergements-grid">
                            {hebergements.map((h) => (
                                <div key={h.id}
                                     className={"hebergement-card " + (hebergementChoisi?.id === h.id ? "selected" : "")}
                                     onClick={() => setHebergementChoisi(
                                         hebergementChoisi?.id === h.id ? null : h
                                     )}>
                                    <div className="hebergement-type">{h.type}</div>
                                    <h3>{h.nom || h.destination}</h3>
                                    {h.nbEtoiles && (
                                        <div className="hebergement-etoiles">{h.nbEtoiles} etoiles</div>
                                    )}
                                    <p className="hebergement-lieu">{h.ville || h.destination}, {h.pays}</p>
                                    <div className="hebergement-prix">{h.prixNuit} {h.devise} / nuit</div>
                                    {h.niveauConfort && (
                                        <span className="hebergement-confort">{h.niveauConfort}</span>
                                    )}
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
                    <label key={opt.value}
                           className={"option-card " + (value === opt.value ? "selected" : "")}>
                        <input type="radio" value={opt.value}
                               checked={value === opt.value}
                               onChange={(e) => setValue(e.target.value)} />
                        <span className="opt-label">{opt.label || opt.value}</span>
                    </label>
                ))}
            </div>
        </div>
    );
}
