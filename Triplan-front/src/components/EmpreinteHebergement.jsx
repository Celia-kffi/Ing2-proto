import React, { useState } from "react";

function EmpreinteHebergement() {
    const [hebergementId, setHebergementId] = useState("");
    const [duree, setDuree] = useState("");
    const [saison, setSaison] = useState("HIVER");
    const [chauffage, setChauffage] = useState(false);
    const [resultat, setResultat] = useState(null);
    const [erreur, setErreur] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErreur(null);
        setResultat(null);
        setLoading(true);

        try {
            const response = await fetch(
                `http://localhost:8081/api/empreinte/calcul?hebergementId=${hebergementId}&duree=${duree}&saison=${saison}&chauffage=${chauffage}`,
                {
                    method: "POST",
                }
            );

            if (!response.ok) {
                throw new Error("Erreur serveur : " + response.status);
            }

            const data = await response.text();
            setResultat(data);
        } catch (error) {
            setErreur(error.message);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="container">
            <h2 className="title">Calcul Empreinte Hébergement</h2>

            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <input
                        type="number"
                        placeholder="ID Hébergement"
                        value={hebergementId}
                        onChange={(e) => setHebergementId(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <input
                        type="number"
                        placeholder="Durée (jours)"
                        value={duree}
                        onChange={(e) => setDuree(e.target.value)}
                        required
                    />
                </div>

                <div className="form-group">
                    <select
                        value={saison}
                        onChange={(e) => setSaison(e.target.value)}
                    >
                        <option value="HIVER">HIVER</option>
                        <option value="ETE">ETE</option>
                        <option value="AUTOMNE">AUTOMNE</option>
                        <option value="PRINTEMPS">PRINTEMPS</option>
                    </select>
                </div>

                <div className="form-group">
                    <label>
                        Chauffage activé :
                        <input
                            type="checkbox"
                            checked={chauffage}
                            onChange={(e) => setChauffage(e.target.checked)}
                            style={{ marginLeft: "10px" }}
                        />
                    </label>
                </div>

                <button type="submit" disabled={loading}>
                    {loading ? "Calcul..." : "Calculer"}
                </button>
            </form>

            {resultat && (
                <h3 className="result">
                    Empreinte : {parseFloat(resultat).toFixed(2)} kg CO₂
                </h3>
            )}

            {erreur && (
                <p className="error">
                    {erreur}
                </p>
            )}
        </div>
    );
}

export default EmpreinteHebergement;