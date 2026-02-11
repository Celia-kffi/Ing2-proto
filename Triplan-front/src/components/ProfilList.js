import "../styles/ProfilList.css";

function ProfilList({ profils, onSelectProfil, selectedProfil }) {
    return (
        <div className="profil-list">
            {profils.map((profil) => (
                <button
                    key={profil.id}
                    className={
                        "profil-card" +
                        (selectedProfil === profil.id ? " selected" : "")
                    }
                    onClick={() => onSelectProfil(profil.id)}
                >
                    <h3>{profil.nom}</h3>

                    <h3>{profil.adresseMail}</h3>



                </button>
            ))}
        </div>
    );
}

export default ProfilList;
