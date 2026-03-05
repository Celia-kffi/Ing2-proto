import { useState } from "react";

function Clients() {
    const [clients, setClients] = useState([]);
    const [nombre, setNombre] = useState(5);

    const fetchClients = () => {
        fetch(`http://localhost:8081/mock/clients/${nombre}`)
            .then(response => response.json())
            .then(data => setClients(data))
            .catch(error => console.error("Erreur :", error));
    };

    return (
        <div style={{ padding: "20px" }}>
            <h2>Génération de clients mock</h2>

            <input
                type="number"
                value={nombre}
                onChange={(e) => setNombre(Number(e.target.value))}
            />

            <button onClick={fetchClients}>
                Générer
            </button>

            <hr />

            {clients.map((client, index) => (
                <div key={index} style={{border: "1px solid gray", margin: "10px", padding: "10px"}}>
                    <p><strong>{client.prenom} {client.nom}</strong></p>
                    <p>Age : {client.age}</p>
                    <p>Nationalité : {client.nationalite}</p>
                    <p>Voyage : {client.voyage}</p>
                    <p>En famille : {client.enFamille ? "Oui" : "Non"}</p>
                </div>
            ))}
        </div>
    );
}

export default Clients;