import { useState } from "react"
import { useLocation} from "react-router-dom";
function EmpreinteActiviteDetail() {
    const location = useLocation();
    const activites = location.state?.activites || [];
    const selectedIds = activites.map(a => a.id);
    const [nbPersonnes, setNbPersonnes] = useState(1);
    const [resultat, setResultat] = useState(null);
    const [loading, setLoading] = useState(false);
    const [details, setDetails] = useState([]);

    const calculerEmpreinte = async () => {
        if (selectedIds.length === 0) return;

        setLoading(true);
        setDetails([]);
        setResultat(null);

        try {
            const response = await fetch(
                `http://172.31.252.54:8081/activites/empreinte?ids=${selectedIds.join(",")}&nbPersonnes=${nbPersonnes}`
    );

if (!response.ok) throw new Error("Erreur API" );

const data = await response.json();
console.log("Réponse API :", data);
const liste = data.activites;
setDetails(liste);
const total = liste.reduce((sum, act) => sum + (act.empreinte || 0), 0);
setResultat(total.toFixed(2));

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

        <h3>Activités sélectionnées :</h3>
        {activites.length === 0 ? (
            <p>Aucune activité reçue</p>
        ) : (
            <ul>
                {activites.map((act) => (
                    <li key={act.id}>
                        {act.nom} (ID: {act.id})
                    </li>
                ))}
            </ul>
        )}
        <div style={{ marginBottom: "15px" }}>


            <input
                type="number"
                min="1"
                value={nbPersonnes}
                onChange={(e) => setNbPersonnes(Number(e.target.value))}
                style={{ width: "80px", marginRight: "10px" }}
            />

            <button onClick={calculerEmpreinte}>
                Calculer
            </button>
        </div>

        {loading && <p>Calcul en cours...</p>}

        {details.length > 0 && (
            <div style={{ marginTop: "20px" }}>
                {details.map((act, index) => (
                    <div key={index} style={{
                        background: "#fff",
                        padding: "15px",
                        borderRadius: "8px",
                        border: "1px solid #ccc",
                        marginBottom: "15px"
                    }}>
                        <h3>Détails de {act.nom}</h3>

                        <p><strong>Type :</strong> {act.type}</p>
                        <p><strong>Ville :</strong> {act.ville}</p>

                        {act.dureeHeures && (
                            <p><strong>Durée :</strong> {act.dureeHeures} heures</p>
                        )}

                        <p>
                            <strong>Facteur :</strong> {act.facteur} kgCO2 / {act.unite}
                        </p>

                        <hr />

                        <p style={{ fontWeight: "bold" }}>
                            Empreinte : {act.empreinte} kgCO2
                        </p>
                    </div>
                ))}

                <div style={{
                    background: "#e8f5e9",
                    padding: "15px",
                    borderRadius: "8px",
                    border: "1px solid #a5d6a7",
                    textAlign: "center"
                }}>
                    <p style={{ fontSize: "18px", fontWeight: "bold" }}>
                        Total : {resultat} kgCO2
                    </p>
                </div>
            </div>
            )}
            {resultat &&  details.length === 0 && (
                <p>
                    Empreinte carbone : <strong>{resultat} kgCO2</strong>
                </p>
            )}
        </div>
);
}

export default EmpreinteActiviteDetail;
