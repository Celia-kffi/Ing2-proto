import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import OptimalItinerary from './components/OptimalItinerary';
import Recommendations from './components/Recommendation';
import Empreinte_trajet from "./components/Empreinte_trajet";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/itinerary" element={<OptimalItinerary />} />
                <Route path="/recommendations" element={<Recommendations />} />
                <Route path="/carbon" element={<Empreinte_trajet />} />
            </Routes>
        </Router>
    );
}

export default App;
