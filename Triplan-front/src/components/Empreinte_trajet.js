import { useState } from "react";
import "../styles/Empreinte_trajet.css";

const MODES = [
    { name: "Marche", img: "/assets/marche.png" },
    { name: "Vélo", img: "/assets/velo.png" },
    { name: "Métro", img: "/assets/metro.png" },
    { name: "Voiture essence", img: "../assets/voiture.png" },
    { name: "Voiture électrique", img: "/assets/voiture_elec.png" },
    { name: "Bus urbain", img: "/assets/bus.png" },
    { name: "Avion court courrier", img: "/assets/avion.png" },
];

export default function Empreinte_trajet() {
    const [distance, setDistance] = useState(10);
    const [selected, setSelected] = useState(null);
    const [infrastructure, setInfrastructure] = useState("autoroute");
    const [remplissage, setRemplissage] = useState("moyen");
    const [resultat, setResultat] = useState(null);
    const calculerEmpreinte = async () => {
        try {
            const payload = {
                transport: selected,
                distance: distance,
                infrastructure: infrastructure,
                remplissage: remplissage,
            };

            console.log("Données envoyées au backend :", payload);

            const response = await fetch("http://localhost:8080/empreinte/calcul", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(payload),
            });

            console.log("Status HTTP :", response.status);

            const data = await response.json();
            console.log("Résultat reçu :", data);

            setResultat(data);
        } catch (error) {
            console.error("Erreur fetch :", error);
            alert("Erreur de communication avec le serveur");
        }
    };


    return (
        <div className="co2-container">
            <div className="co2-card">
                <h2>Distance</h2>

                <div className="distance-input">
                    <input
                        type="number"
                        min="0"
                        value={distance}
                        onChange={(e) => setDistance(Number(e.target.value))}
                    />
                    <span>km</span>
                </div>

                <p className="subtitle">Choisissez votre moyen de transport</p>

                <div className="transport-grid">
                    {MODES.map((mode) => (
                        <button
                            key={mode.name}
                            className={`transport-btn ${
                                selected === mode.name ? "active" : ""
                            }`}
                            onClick={() => setSelected(mode.name)}
                        >
                            <img src={mode.img} alt={mode.name} />
                            <span>{mode.name}</span>
                        </button>
                    ))}
                </div>

                <p className="subtitle">Type d’infrastructure</p>

                <select
                    className="infrastructure-select"
                    value={infrastructure}
                    onChange={(e) => setInfrastructure(e.target.value)}
                >
                    <option value="autoroute">Autoroute</option>
                    <option value="urbain">Urbain</option>
                    <option value="site_propre">Site propre</option>
                    <option value="ligne_classique">Ligne classique</option>
                    <option value="ligne_saturee">Ligne saturée</option>
                    <option value="aerien">Aérien</option>
                    <option value="souterrain">Souterrain</option>
                </select>

                <p className="subtitle">Niveau de remplissage</p>

                <select
                    className="infrastructure-select"
                    value={remplissage}
                    onChange={(e) => setRemplissage(e.target.value)}
                >
                    <option value="faible">Faible</option>
                    <option value="moyen">Moyen</option>
                    <option value="plein">Plein</option>
                </select>

                <button
                    className="calcul-btn"
                    onClick={calculerEmpreinte}
                    disabled={!selected}
                >
                    Calculer l’empreinte
                </button>

                {resultat && (
                    <div className="resultat-box">
                        <h3>Résultat</h3>
                        <p>
                            <strong>Transport :</strong>{" "}
                            {resultat.transport.nom}
                        </p>
                        <p>
                            <strong>Distance :</strong> {resultat.distance} km
                        </p>
                        <p>
                            <strong>Empreinte carbone :</strong>{" "}
                            {resultat.empreinte.toFixed(2)} kg CO₂
                        </p>
                    </div>
                )}
            </div>
        </div>
    );
}
