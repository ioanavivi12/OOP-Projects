package org.example;

public class EntitateJuridica extends Utilizator{
    private String reprezentat;

    public EntitateJuridica(String nume, String reprezentat) {
        super(nume);
        this.reprezentat = reprezentat;
    }


    @Override
    public String textCerere(TipCerere tipCerere) throws IllegalArgumentException{
        if(!verificareTipCerere(tipCerere))
            throw new IllegalArgumentException("Utilizatorul de tip entitate juridica nu poate inainta o cerere de tip " + tipCerere.getTipCerere());
        return "Subsemnatul " + this.reprezentat + ", reprezentant legal al companiei " + this.getNume() + ", va rog sa-mi aprobati urmatoarea solicitare: " + tipCerere.getTipCerere();

    }

    @Override
    public boolean verificareTipCerere(TipCerere tipCerere) {
        if(!(tipCerere == TipCerere.constituiv || tipCerere == TipCerere.autorizatie))
            return false;
        return true;
    }

    @Override
    public String getTipUtilizator() {
        return "entitate juridica";
    }
}
