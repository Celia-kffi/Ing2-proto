import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/Login.css";

function Login() {

    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");

    const navigate = useNavigate();

    function handleLogin(e) {
        e.preventDefault();

        fetch("http://localhost:8081/api/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                adresseMail: email,
                motDePasse: password
            })
        })
            .then(res => {
                if (!res.ok) {
                    return res.text().then(msg => { throw new Error(msg); });
                }
                return res.json();
            })
            .then(data => {
                localStorage.setItem("user", JSON.stringify(data));
                navigate(`/profil/${data.id}/formulaire`);
            })
            .catch(err => {
                setError(err.message);
            });
    }

    return (
        <div className="login-container">
            <div className="login-box">
                <h2>Connexion</h2>

                <form onSubmit={handleLogin}>

                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />

                    <input
                        type="password"
                        placeholder="Mot de passe"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />

                    <button type="submit">Se connecter</button>

                </form>

                {error && <p className="error-message">{error}</p>}
            </div>
        </div>
    );
}

export default Login;