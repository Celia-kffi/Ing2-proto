package triplan.back.entities;

public enum TypeChauffage {

    ELECTRIQUE(0.5, 0.05),
    GAZ(0.6, 0.2),
    FIOUL(0.7, 0.3),
    POMPE_CHALEUR(0.3, 0.05);

    private final double coefKwh;
    private final double facteurCO2;

    TypeChauffage(double coefKwh, double facteurCO2) {
        this.coefKwh = coefKwh;
        this.facteurCO2 = facteurCO2;
    }

    public double getCoefKwh() {
        return coefKwh;
    }

    public double getFacteurCO2() {
        return facteurCO2;
    }
}