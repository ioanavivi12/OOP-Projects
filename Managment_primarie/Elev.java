package org.example;

public class Elev extends Utilizator{
    private String scoala;

    public Elev(String nume, String scoala) {
        super(nume);
        this.scoala = scoala;
    }

    @Override
    public String textCerere(TipCerere tipCerere) throws IllegalArgumentException {
        if(!verificareTipCerere(tipCerere))
            throw new IllegalArgumentException("Utilizatorul de tip elev nu poate inainta o cerere de tip " + tipCerere.getTipCerere());
        return "Subsemnatul " + this.getNume() + ", elev la scoala " + this.scoala + ", va rog sa-mi aprobati urmatoarea solicitare: " + tipCerere.getTipCerere();
    }

    public boolean verificareTipCerere(TipCerere tipCerere){
        if(!(tipCerere == Utilizator.TipCerere.elev || tipCerere == Utilizator.TipCerere.buletin))
            return false;
        return true;
    }

    @Override
    public String getTipUtilizator() {
        return "elev";
    }
}
