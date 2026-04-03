import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../styles/HomePage.css';
import logo from '../assets/Triplan.png';

const HomePage = () => {
    const navigate = useNavigate();

    const features = [
        {
            id: 'recommendations',
            title: 'Recommandations de voyages',
            route: '/login'
        },
        {
            id: 'itinerary',
            title: 'Planification d\'activités',
            route: '/itinerary'
        },
        {
            id: 'carbon',
            title: 'Empreinte carbone',
            route: '/carbon'
        },
        {
            id: 'client',
            title: 'Client mock en masse',
            route: '/clients'
        },
        {
            id: 'hebergement',
            title: 'Carbone hébergement',
            route: '/hebergement'
        },
        {
            id: 'activite',
            title: 'Empreinte Activité',
            available: true,
            route: '/activite-carbone'
        }
    ];

    const handleNavigation = (route) => {
        navigate(route);
    };

    return (
        <div className="home-page">
            <header className="home-header">
                <img src={logo} alt="Triplan Logo" className="logo"/>
                <h1 className="title">TRIPLAN</h1>
            </header>

            <main className="home-main">
                <div className="welcome-section">
                    <h2>Bienvenue sur Triplan</h2>
                    <p>Planifiez votre voyage de manière optimale et écologique</p>
                </div>

                <div className="features-grid">
                    {features.map((feature) => (
                        <div
                            key={feature.id}
                            className="feature-card"
                            onClick={() => handleNavigation(feature.route)}
                        >
                            <h3>{feature.title}</h3>
                            <button className="feature-button">Commencer</button>
                        </div>
                    ))}
                </div>

                <div className="info-section">
                    <div className="info-card">
                        <h4>Notre mission</h4>
                        <p>Rendre vos voyages plus efficaces et respectueux de l'environnement</p>
                    </div>
                    <div className="info-card">
                        <h4>Comment ça marche ?</h4>
                        <p>
                            1. Recevez des recommandations<br/>
                            2. Planifiez votre itinéraire<br/>
                            3. Consultez votre impact carbone
                        </p>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default HomePage;