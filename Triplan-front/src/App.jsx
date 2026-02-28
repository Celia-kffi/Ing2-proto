import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import OptimalItinerary from './components/OptimalItinerary';
import Recommendations from './components/Recommendation';
import Empreintetrajet from "./components/Empreintetrajet";
import Clients from "./components/Clients";


function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/itinerary" element={<OptimalItinerary />} />
                <Route path="/recommendations" element={<Recommendations />} />
                <Route path="/carbon" element={<Empreintetrajet />} />
                <Route path="/clients" element={<Clients />} />
            </Routes>
        </Router>
    );
}

export default App;
