package org.example;

public class Pensionar extends Utilizator{
    public Pensionar(String nume) {
        super(nume);
    }

    @Override
    public String textCerere(TipCerere tipCerere) throws IllegalArgumentException{
        if(!verificareTipCerere(tipCerere))
            throw new IllegalArgumentException("Utilizatorul de tip pensionar nu poate inainta o cerere de tip " + tipCerere.getTipCerere());
        return "Subsemnatul " + this.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tipCerere.getTipCerere();
    }

    @Override
    public boolean verificareTipCerere(TipCerere tipCerere) {
        if(!(tipCerere == TipCerere.pensie || tipCerere == TipCerere.buletin || tipCerere == TipCerere.sofer))
            return false;
        return true;
    }

    @Override
    public String getTipUtilizator() {
        return "pensionar";
    }
}
