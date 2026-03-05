import { useLocation } from "react-router-dom";
import EmpreinteHebergement from "../components/EmpreinteHebergement";

function CalculHebergement() {

    const location = useLocation();
    const hebergement = location.state?.hebergement;

    return (
        <div>
            <EmpreinteHebergement hebergement={hebergement} />
        </div>
    );
}

export default CalculHebergement;