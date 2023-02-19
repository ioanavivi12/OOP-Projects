package org.example;

public class Angajat extends Utilizator{
    private String companie;

    public Angajat(String nume, String companie) {
        super(nume);
        this.companie = companie;
    }

    @Override
    public String textCerere(TipCerere tipCerere) throws IllegalArgumentException{
        if(!verificareTipCerere(tipCerere))
            throw new IllegalArgumentException("Utilizatorul de tip angajat nu poate inainta o cerere de tip " + tipCerere.getTipCerere());

        return "Subsemnatul " + this.getNume() + ", angajat la compania " + this.companie + ", va rog sa-mi aprobati urmatoarea solicitare: " + tipCerere.getTipCerere();
    }
    @Override
    public boolean verificareTipCerere(TipCerere tipCerere) {
        if(!(tipCerere == TipCerere.venit || tipCerere == TipCerere.buletin || tipCerere == TipCerere.sofer))
            return false;
        return true;
    }

    @Override
    public String getTipUtilizator() {
        return "angajat";
    }
}
