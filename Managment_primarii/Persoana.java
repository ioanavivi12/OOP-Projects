package org.example;

public class Persoana extends Utilizator{
    public Persoana(String nume) {
        super(nume);
    }

    @Override
    public String textCerere(TipCerere tipCerere) throws IllegalArgumentException{
        if(!verificareTipCerere(tipCerere))
            throw new IllegalArgumentException("Utilizatorul de tip persoana nu poate inainta o cerere de tip " + tipCerere.getTipCerere());
        return "Subsemnatul " + this.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tipCerere.getTipCerere();
    }

    @Override
    public boolean verificareTipCerere(TipCerere tipCerere) {
        if(!(tipCerere == Utilizator.TipCerere.sofer || tipCerere == Utilizator.TipCerere.buletin))
            return false;
        return true;
    }

    @Override
    public String getTipUtilizator() {
        return "persoana";
    }

}
