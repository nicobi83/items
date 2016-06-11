package api.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Thomas on 06/05/2016.
 */
public class Macchine{

    public static class Macchina {

        String color;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        String targa;

        public String getTarga() {
            return targa;
        }

        public void setTarga(String targa) {
            this.targa = targa;
        }

        TipiMacchina tipoMacchina;

        public TipiMacchina getTipo() {
            return tipoMacchina;
        }

        public void setTipo(TipiMacchina tipoMacchina) {
            this.tipoMacchina = tipoMacchina;
        }

        String produttore;

        public String getProduttore() {
            return produttore;
        }

        public void setProduttore(String produttore) {
            this.produttore = produttore;
        }

        String modello;

        public String getModello() {
            return modello;
        }

        public void setModello(String modello) {
            this.modello = modello;
        }

    }

    public Set<Macchina> getMacchine() {
        return macchine;
    }

    public void setMacchine(Set<Macchina> macchine) {
        this.macchine = macchine;
    }

    Set<Macchina> macchine = new HashSet<>();











   /* Date creationDate;
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; } */

  /*  Date modifiedDate;
    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; } */





    /*public Macchina() {
        this.targa = UUID.randomUUID().toString();
        this.creationDate = DateTime.now().toDate();
        this.modifiedDate = this.creationDate;
    }*/

    @Override
    public boolean equals(Object o) {
        if (o != null) {
            return o.hashCode() == this.hashCode();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
