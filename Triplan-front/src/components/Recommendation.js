import { useEffect, useState } from "react";
import { getProfils } from "../api/api";
import ProfilList from "../components/ProfilList";
import ProfilForm from "../components/ProfilForm";
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
        <div style={{ padding: "20px" }}>

            <h1>TRIPLAN</h1>
            <p>Assistant de voyage</p>

            <hr />

            <h2>Menu</h2>

            <div>
                <p><b>Recommandations</b></p>

                <Link to="/itinerary">
                    <button>Planification</button>
                </Link>

                <Link to="/carbon">
                    <button style={{ marginLeft: "10px" }}>
                        Empreinte carbone
                    </button>
                </Link>
            </div>

            <hr />

            {profilChoisi === null ? (
                <div>
                    <h2>Choisissez un profil</h2>

                    <ProfilList
                        profils={profils}
                        onSelectProfil={choisirProfil}
                        selectedProfil={profilChoisi}
                    />
                </div>
            ) : (
                <ProfilForm
                    profilId={profilChoisi}
                    onClose={retour}
                />
            )}

        </div>
    );
}

export default Recommendation;