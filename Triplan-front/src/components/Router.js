import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Empreinte_trajet from './Empreinte_trajet';


export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Empreinte_trajet/>} />
            </Routes>
        </BrowserRouter>
    );
}