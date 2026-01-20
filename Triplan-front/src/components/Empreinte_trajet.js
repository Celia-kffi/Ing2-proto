import { useState } from "react";
import "../styles/Empreinte_trajet.css";
import { MODES } from "../constants/transports";
import { INFRASTRUCTURES } from "../constants/infrastructures";
import { REMPLISSAGES } from "../constants/remplissages";



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
                    {INFRASTRUCTURES.map((item) => (
                        <option key={item.value} value={item.value}>
                            {item.label}
                        </option>
                    ))}
                </select>

                <p className="subtitle">Niveau de remplissage</p>

                <select
                    className="infrastructure-select"
                    value={remplissage}
                    onChange={(e) => setRemplissage(e.target.value)}
                >
                    {REMPLISSAGES.map((item) => (
                        <option key={item.value} value={item.value}>
                            {item.label}
                        </option>
                    ))}
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
