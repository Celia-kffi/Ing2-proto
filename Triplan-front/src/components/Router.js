import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Empreintetrajet from './Empreintetrajet';


export default function Router() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Empreintetrajet/>} />
            </Routes>
        </BrowserRouter>
    );
}