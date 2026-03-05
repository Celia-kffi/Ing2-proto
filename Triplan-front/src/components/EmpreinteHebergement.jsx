import React, { useState } from "react";
import { Stage, Layer, Rect, Circle, Text } from "react-konva";
import { SAISONS, TYPES_HEBERGEMENT, API_URL } from "../constants/hebergements";
import "../styles/EmpreinteHebergement.css";

function EmpreinteHebergement({ hebergement }) {

    const [formData, setFormData] = useState({
        hebergementId: hebergement?.id || "",
        duree: "",
        saison: "HIVER",
        chauffage: false,
        superficie: 50,
        type: hebergement?.type === "Appartement" ? "APPART" : "HOTEL"
    });

    const [resultat, setResultat] = useState(null);
    const [erreur, setErreur] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;

        setFormData({
            ...formData,
            [name]: type === "checkbox" ? checked : value
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        setErreur(null);
        setResultat(null);
        setLoading(true);

        try {

            const { hebergementId, duree, saison, chauffage, superficie } = formData;

            const response = await fetch(
                `${API_URL}?hebergementId=${hebergementId}&duree=${duree}&saison=${saison}&chauffage=${chauffage}&superficie=${superficie}`,
                { method: "POST" }
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

    const chauffageLightColor = formData.chauffage ? "red" : "grey";

    return (
        <div className="hebergement-container">

            <h2>Calcul Empreinte Hébergement</h2>

            <form onSubmit={handleSubmit} className="hebergement-form">

                <input
                    type="number"
                    name="hebergementId"
                    placeholder="ID Hébergement"
                    value={formData.hebergementId}
                    onChange={handleChange}
                    required
                />

                <input
                    type="number"
                    name="duree"
                    placeholder="Durée (jours)"
                    value={formData.duree}
                    onChange={handleChange}
                    required
                />

                <input
                    type="number"
                    name="superficie"
                    placeholder="Superficie (m²)"
                    value={formData.superficie}
                    onChange={handleChange}
                    required
                />

                <select
                    name="saison"
                    value={formData.saison}
                    onChange={handleChange}
                >
                    {SAISONS.map((s) => (
                        <option key={s.value} value={s.value}>
                            {s.label}
                        </option>
                    ))}
                </select>

                <select
                    name="type"
                    value={formData.type}
                    onChange={handleChange}
                >
                    {TYPES_HEBERGEMENT.map((t) => (
                        <option key={t.value} value={t.value}>
                            {t.label}
                        </option>
                    ))}
                </select>

                <label className="checkbox-label">
                    Chauffage
                    <input
                        type="checkbox"
                        name="chauffage"
                        checked={formData.chauffage}
                        onChange={handleChange}
                    />
                </label>

                <button type="submit" disabled={loading}>
                    {loading ? "Calcul..." : "Calculer"}
                </button>

            </form>

            {resultat && (
                <h3>
                    Empreinte : {parseFloat(resultat).toFixed(2)} kg CO₂
                </h3>
            )}

            {erreur && <p className="error">{erreur}</p>}

            <Stage width={600} height={400}>
                <Layer>

                    <Rect
                        x={50}
                        y={50}
                        width={formData.type === "HOTEL" ? 400 : 300}
                        height={formData.superficie * 4}
                        fill="#e0e0e0"
                        stroke="black"
                        strokeWidth={2}
                        cornerRadius={10}
                    />

                    <Text
                        text={`${formData.type} - ${formData.superficie} m²`}
                        x={60}
                        y={20}
                        fontSize={18}
                        fill="#333"
                    />

                    <Rect
                        x={100}
                        y={100}
                        width={80}
                        height={30}
                        fill="#555"
                        stroke="black"
                        cornerRadius={5}
                    />

                    <Circle
                        x={135}
                        y={115}
                        radius={6}
                        fill={chauffageLightColor}
                        stroke="black"
                    />

                    <Text
                        text="Chauffage"
                        x={100}
                        y={135}
                        fontSize={12}
                    />

                </Layer>
            </Stage>

        </div>
    );
}

export default EmpreinteHebergement;