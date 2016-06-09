package api.model;

import java.util.Date;
import java.util.List;

/**
 * Created by nicob on 06/05/2016.
 */
public abstract class Person extends Items.Item {

    public Indirizzo getResidenza() {
        return residenza;
    }

    public void setResidenza(Indirizzo residenza) {
        this.residenza = residenza;
    }

    Indirizzo residenza;

    public Indirizzo getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(Indirizzo domicilio) {
        this.domicilio = domicilio;
    }

    Indirizzo domicilio;

    public char getSesso() {
        return sesso;
    }

    public void setSesso(char sesso) {
        this.sesso = sesso;
    }

    char sesso;

    public Date getDatanascita() {
        return datanascita;
    }

    public void setDatanascita(Date datanascita) {
        this.datanascita = datanascita;
    }

    Date datanascita;

    public List<String> getCittadinanza() {
        return cittadinanza;
    }

    public void setCittadinanza(List<String> cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    List<String> cittadinanza;

}
