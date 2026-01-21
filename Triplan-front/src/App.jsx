import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import OptimalItinerary from './components/OptimalItinerary';


const Recommendations = () => <div></div>;
const Carbon = () => <div></div>;

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/" element={<HomePage />} />
                <Route path="/itinerary" element={<OptimalItinerary />} />
                <Route path="/recommendations" element={<Recommendations />} />
                <Route path="/carbon" element={<Carbon />} />
            </Routes>
        </Router>
    );
}

export default App;
