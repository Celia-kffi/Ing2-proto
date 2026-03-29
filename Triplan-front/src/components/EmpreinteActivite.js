
import { useState } from "react";

function EmpreinteActiviteDetail() {
    const [id, setId] = useState("");
    const [nbPersonnes, setNbPersonnes] = useState(1);
    const [resultat, setResultat] = useState(null);
    const [loading, setLoading] = useState(false);
    const [details, setDetails] = useState(null);

    const calculerEmpreinte = async () => {
        if (!id) return;

        setLoading(true);
        setDetails(null);
        setResultat(null);

        try {
            const response = await fetch(
                `http://localhost:8081/activites/${id}/empreinte?nbPersonnes=${nbPersonnes}`
    );

if (!response.ok) throw new Error("Erreur API");

const data = await response.json();
setDetails(data);
setResultat(data.empreinte);

} catch (error) {
    setResultat("Erreur lors du calcul");
}

setLoading(false);
};

return (
    <div style={{
        padding: "30px",
        maxWidth: "500px",
        background: "#f9f9f9",
        borderRadius: "10px",
        margin: "50px auto",
        boxShadow: "0 4px 10px rgba(0,0,0,0.1)"
    }}>
        <h2>Empreinte Carbone - Activité</h2>

        <div style={{ marginBottom: "15px" }}>
            <input
                type="number"
                placeholder="ID activité"
                value={id}
                onChange={(e) => setId(e.target.value)}
                style={{ width: "120px", marginRight: "10px" }}
            />

            <input
                type="number"
                min="1"
                value={nbPersonnes}
                onChange={(e) => setNbPersonnes(e.target.value)}
                style={{ width: "80px", marginRight: "10px" }}
            />

            <button onClick={calculerEmpreinte}>
                Calculer
            </button>
        </div>

        {loading && <p>Calcul en cours...</p>}

        {details && (
            <div style={{
                marginTop: "20px",
                background: "#fff",
                padding: "15px",
                borderRadius: "8px",
                border: "1px solid #ccc"
            }}>
                <h3>Détails du calcul</h3>

                <p><strong>Nom :</strong> {details.nom}</p>
                <p><strong>Type :</strong> {details.type}</p>
                <p><strong>Ville :</strong> {details.ville}</p>

                {details.dureeHeures && (
                    <p><strong>Durée :</strong> {details.dureeHeures} heures</p>
                )}

                <p>
                    <strong>Facteur d’émission :</strong> {details.facteur} kgCO2 / {details.unite}
                </p>

                <hr />

                <p style={{ fontWeight: "bold", fontSize: "18px" }}>
                    Empreinte finale : {details.empreinte} kgCO2
                </p>
            </div>
        )}

        {resultat && !details && (
            <p>
                Empreinte carbone : <strong>{resultat} kgCO2</strong>
            </p>
        )}
    </div>
);
}

export default EmpreinteActiviteDetail;
