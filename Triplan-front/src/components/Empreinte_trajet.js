import { useState } from "react";
import "../styles/Empreinte_trajet.css";

const MODES = [
    { name: "Marche", img: "../assets/marche.png" },
    { name: "Vélo mécanique", img: "../assets/velo.png" },
    { name: "Métro", img: "../assets/metro.png" },
    { name: "Vélo électrique", img: "../assets/velo_elec.png" },
    { name: "Trottinette électrique", img: "../assets/trottinette.png" },
    { name: "Voiture électrique", img: "../assets/voiture_elec.png" },
    { name: "Bus", img: "../assets/bus.png" },
    { name: "Voiture", img: "../assets/voiture.png" },
    { name: "Avion", img: "../assets/avion.png" },
];


    export default function Empreinte_trajet() {
        const [distance, setDistance] = useState(10);
        const [selected, setSelected] = useState(null);
        const [resultat, setResultat] = useState(null);

        const calculerEmpreinte = async () => {
            if (!selected) {
                alert("Sélectionne un transport");
                return;
            }

            const response = await fetch("http://localhost:8080/calcul", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    transport: selected,
                    distance: distance,
                }),
            });

            const data = await response.json();
            setResultat(data);
            alert(`Empreinte : ${data.empreinte} kgCO₂`);
        };

    return (
        <div className="co2-container">
            <div className="co2-card">
                <h2>Distance</h2>

                <div className="distance-input">
                    <input
                        type="number"
                        value={distance}
                        onChange={(e) => setDistance(Number(e.target.value))}
                    />
                    <span>km</span>
                </div>

                <p className="subtitle">
                    Choisissez votre moyen de transport
                </p>

                <div className="transport-grid">
                    {MODES.map((mode) => (
                        <button
                            key={mode.name}
                            className={`transport-btn ${selected === mode.name ? "active" : ""}`}
                            onClick={() => setSelected(mode.name)}
                        >
                            <img src={mode.img} alt={mode.name}/>
                            <span>{mode.name}</span>
                        </button>
                    ))}
                </div>
                <button onClick={calculerEmpreinte} disabled={!selected}>
                    Calculer l’empreinte
                </button>

                {selected && (
                    <p className="selected-info">
                        Transport sélectionné : <strong>{selected}</strong> – {distance} km
                    </p>
                )}
                {resultat && (
                    <div className="resultat-box">
                        <h3>Résultat</h3>
                        <p><strong>Transport :</strong> {resultat.transport}</p>
                        <p><strong>Distance :</strong> {resultat.distance} km</p>
                        <p><strong>Empreinte carbone :</strong> {resultat.empreinte} kg CO₂</p>
                    </div>
                )}
            </div>
        </div>
    );
    }


