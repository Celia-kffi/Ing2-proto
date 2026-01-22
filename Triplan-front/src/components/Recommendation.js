import { useEffect, useState } from "react";
import { getProfils, getRecommendations } from "../api/api";
import logo from "../assets/Triplan.png";
import "../styles/Recommendation.css";
import ProfilList from "../components/ProfilList";


function Recommendation() {
    const [profils, setProfils] = useState([]);
    const [voyages, setVoyages] = useState([]);
    const [profilChoisi, setProfilChoisi] = useState(null);

    useEffect(() => {what
        getProfils().then(setProfils);
    }, []);

    const handleProfilClick = (profilId) => {
        setProfilChoisi(profilId);
        getRecommendations(profilId).then(setVoyages);
    };

    return (
        <div>
            {/* HEADER */}
            <header className="header fade-in">
                <div className="header-content">
                    <img src={logo} alt="Triplan" />
                    <div>
                        <h1>TRIPLAN</h1>
                        <p>Votre assistant de voyage intelligent et responsable</p>
                    </div>
                </div>
            </header>

            {/* HERO */}
            <section className="hero fade-in">
                <h2>Bienvenue sur Triplan</h2>
                <p>Planifiez votre voyage de manière optimale et écologique</p>
            </section>

            {/* CARDS */}
            <section className="cards fade-in">
                <div className="card active">
                    <h3>Recommandations de Voyages</h3>
                    <p>Basées sur votre profil</p>
                </div>
                <div className="card disabled">
                    <h3>Planification d’Activités</h3>
                    <p>Bientôt disponible</p>
                </div>
                <div className="card disabled">
                    <h3>Empreinte Carbone</h3>
                    <p>Bientôt disponible</p>
                </div>
            </section>

            {/* PROFILS */}
            <section className="section fade-in">
                <h2>Choisissez un profil</h2>
                <ProfilList
                    profils={profils}
                    onSelectProfil={handleProfilClick}
                />
            </section>

            {/* RECOMMANDATIONS */}
            {profilChoisi && (
                <section className="section fade-in">
                    <h2>Recommandations de voyages</h2>

                    <div className="voyages">
                        {voyages.map((v) => (
                            <div key={v.id} className="voyage-card">
                                <div>
                                    <h3>{v.nom}</h3>
                                    <span className="theme">{v.theme}</span>
                                </div>
                                <div className="prix">{v.prix} €</div>
                            </div>
                        ))}
                    </div>
                </section>
            )}
        </div>
    );
}

export default Recommendation;
