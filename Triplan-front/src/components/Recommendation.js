import { useEffect, useState } from "react";
import { getProfils } from "../api/api";
import ProfilList from "../components/ProfilList";
import ProfilForm from "../components/ProfilForm";
import "../styles/Recommendation.css";
import { Link } from "react-router-dom";

function Recommendation() {

    const [profils, setProfils] = useState([]);
    const [profilChoisi, setProfilChoisi] = useState(null);

    useEffect(() => {
        getProfils().then((data) => {
            setProfils(data);
        });
    }, []);

    function choisirProfil(id) {
        setProfilChoisi(id);
    }

    function retour() {
        setProfilChoisi(null);
    }

    return (
        <div>

            <header className="header fade-in">
                <div className="header-content">
                    <h1>TRIPLAN</h1>
                    <p>Votre assistant de voyage intelligent et responsable</p>
                </div>
            </header>

            <section className="hero fade-in">
                <h2>Bienvenue sur Triplan</h2>
                <p>Planifiez votre voyage de manière optimale</p>
            </section>

            <section className="cards fade-in">
                <div className="card">
                    <h3>Recommandations</h3>
                    <p>Basées sur votre profil</p>
                </div>

                <Link to="/itinerary" className="card-link">
                    <div className="card">
                        <h3>Planification</h3>
                        <p>Accéder aux activités</p>
                    </div>
                </Link>

                <Link to="/carbon" className="card-link">
                    <div className="card">
                        <h3>Empreinte carbone</h3>
                        <p>Voir mon impact</p>
                    </div>
                </Link>
            </section>

            <section className="section fade-in">

                {profilChoisi === null ? (
                    <>
                        <h2>Choisissez un profil</h2>

                        <ProfilList
                            profils={profils}
                            onSelectProfil={choisirProfil}
                            selectedProfil={profilChoisi}
                        />
                    </>
                ) : (
                    <ProfilForm
                        profilId={profilChoisi}
                        onClose={retour}
                    />
                )}

            </section>

        </div>
    );
}

export default Recommendation;