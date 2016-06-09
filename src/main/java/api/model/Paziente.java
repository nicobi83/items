package api.model;

/**
 * Created by Thomas on 06/05/2016.
 */

public class Paziente extends Items.Item {
    String codiceFiscale;

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

   /* public Indirizzo getIndirizzoResidenza() {
        return indirizzoResidenza;
    }*/

    /*public void setIndirizzoResidenza(Indirizzo indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }*/
}

