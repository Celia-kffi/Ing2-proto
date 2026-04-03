import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ProfilCard.css";

function ProfilCard() {
    const navigate = useNavigate();
    const [profil, setProfil] = useState(null);

    useEffect(() => {
        const user = localStorage.getItem("user");

        if (!user) {
            navigate("/login");
        } else {
            setProfil(JSON.parse(user));
        }
    }, []);

    if (!profil) return null;

    return (
        <div className="pc-page">

            <div className="pc-hero">
                <div className="pc-hero-top">
                    <div className="pc-avatar">
                        {profil.prenom[0]}{profil.nom[0]}
                    </div>

                    <div>
                        <div className="pc-name">
                            {profil.prenom} {profil.nom}
                        </div>
                        <div className="pc-email">
                            {profil.adresseMail}
                        </div>
                    </div>

                    <div className="pc-badge">
                        Voyageur {profil.typeExperience}
                    </div>
                </div>

                <div className="pc-stats">
                    <div>
                        Budget : {profil.budget} €
                    </div>
                    <div>
                        Durée : {profil.dureeMoyenne} jours
                    </div>
                    <div>
                        Expérience : {profil.typeExperience}
                    </div>
                    <div>
                        Membre depuis : {profil.dateCreation}
                    </div>
                </div>
            </div>

            <div className="pc-action-strip">

                <button onClick={() => {
                    localStorage.removeItem("user");
                    navigate("/login");
                }}>
                    Déconnexion
                </button>

                <button onClick={() => {
                    navigate(`/profil/${profil.id}/formulaire`);
                }}>
                    Continuer
                </button>

            </div>

        </div>
    );
}

export default ProfilCard;