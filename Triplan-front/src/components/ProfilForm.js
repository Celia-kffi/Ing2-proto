import { useState } from "react";

function ProfilForm({ profilId, onClose }) {

    const [scores, setScores] = useState({
        nature: 0,
        aventure: 0,
        culture: 0,
        detente: 0,
        luxe: 0
    });

    function addPoints(theme, points) {
        const newScores = { ...scores };
        newScores[theme] = newScores[theme] + points;
        setScores(newScores);
    }

    function handleSubmit(e) {
        e.preventDefault();

        let total = 0;
        for (let key in scores) {
            total += scores[key];
        }

        const percentages = {};

        for (let key in scores) {
            if (total !== 0) {
                percentages[key] = Math.round((scores[key] / total) * 100);
            } else {
                percentages[key] = 0;
            }
        }

        console.log("Profil choisi :", profilId);
        console.log("Scores :", scores);
        console.log("Pourcentages :", percentages);

        onClose();
    }

    return (
        <div style={{ padding: "30px" }}>

            <h2>Questionnaire Profil {profilId}</h2>

            <form onSubmit={handleSubmit}>

                <h4>1. Environnement préféré</h4>
                <button type="button" onClick={() => addPoints("nature", 2)}>Montagne</button>
                <button type="button" onClick={() => addPoints("detente", 2)}>Mer</button>
                <button type="button" onClick={() => addPoints("culture", 2)}>Ville</button>

                <h4>2. Activité favorite</h4>
                <button type="button" onClick={() => addPoints("aventure", 3)}>Sports</button>
                <button type="button" onClick={() => addPoints("culture", 3)}>Musées</button>
                <button type="button" onClick={() => addPoints("detente", 3)}>Spa</button>

                <h4>3. Budget</h4>
                <button type="button" onClick={() => addPoints("nature", 1)}>Petit budget</button>
                <button type="button" onClick={() => addPoints("luxe", 3)}>Gros budget</button>

                <br /><br />

                <button type="submit">
                    Valider
                </button>

                <button type="button" onClick={onClose} style={{ marginLeft: "10px" }}>
                    Retour
                </button>

            </form>
        </div>
    );
}

export default ProfilForm;