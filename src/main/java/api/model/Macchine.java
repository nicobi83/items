package api.model;

import java.util.*;

/**
 * Created by Thomas on 06/05/2016.
 */
public class Macchine{





    public static class Macchina {


        int id;
        public int getId() { return id; }
        public void setId(int id) { this.id = id; }

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

        String categoria;
        public String getCategoria() { return categoria; }
        public void setCategoria(String categoria) { this.categoria = categoria; }

        String cilindrata;
        public String getCilindrata() { return cilindrata; }
        public void setCilindrata(String cilindrata) { this.cilindrata = cilindrata; }

        String potenza;
        public String getPotenza() { return potenza; }
        public void setPotenza(String potenza) { this.potenza = potenza; }

        String potenza_fiscale;
        public String getPotenza_fiscale() { return potenza_fiscale; }
        public void setPotenza_fiscale(String potenza_fiscale) { this.potenza_fiscale = potenza_fiscale; }

        String sValues;
        public String getsValues() { return sValues; }
        public void setsValues(String sValues) { this.sValues = sValues;  }

        Date creationDate;
        public Date getCreationDate() { return creationDate; }
        public void setCreationDate(Date creationDate) { this.creationDate = creationDate; }

        Date modifiedDate;
        public Date getModifiedDate() { return modifiedDate; }
        public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; }

        Map<String, Object> values = new HashMap<>();
        public void setValues(Map<String, Object> values) {
            this.values = values;





        }

    }








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
