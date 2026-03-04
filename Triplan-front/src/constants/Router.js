import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Empreintetrajet from '../components/Empreintetrajet';
import EmpreinteHebergement from '../components/EmpreinteHebergement';

export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Empreintetrajet/>} />
            </Routes>
            <Routes>
                <Route path="/" element={<EmpreinteHebergement/>} />
            </Routes>
        </BrowserRouter>
    );
}