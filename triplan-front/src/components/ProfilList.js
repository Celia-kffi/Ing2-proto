function ProfilList({ profils, onSelectProfil }) {
    return (
        <div className="profil-buttons">
            {profils.map((profil) => (
                <button
                    key={profil.id}
                    className="profil-btn"
                    onClick={() => onSelectProfil(profil.id)}
                >
                    {profil.prenom
                        ? `${profil.prenom} ${profil.nom}`
                        : profil.nom}
                </button>
            ))}
        </div>
    );
}

export default ProfilList;
