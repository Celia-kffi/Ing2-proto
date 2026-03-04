import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import HomePage from "./components/HomePage";
import OptimalItinerary from "./components/OptimalItinerary";
import Recommendation from "./components/Recommendation";
import Empreintetrajet from "./components/Empreintetrajet";
import ProfilForm from "./components/ProfilForm";
import EmpreinteHebergement from "./components/EmpreinteHebergement";

function App() {
    return (
        <Router>
            <Routes>

                {/* Page d'accueil normale */}
                <Route path="/" element={<HomePage />} />

                {/* Page recommandations normale */}
                <Route path="/recommendations" element={<Recommendation />} />

                {/* Formulaire profil (nouvelle page) */}
                <Route path="/profil/:id/formulaire" element={<ProfilForm />} />

                {/* Autres pages */}
                <Route path="/itinerary" element={<OptimalItinerary />} />
                <Route path="/carbon" element={<Empreintetrajet />} />
                <Route path="/hebergement" element={<EmpreinteHebergement />} />

            </Routes>
        </Router>
    );
}

export default App;