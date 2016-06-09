package api.model;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Thomas on 06/05/2016.
 */
public class Macchina extends Items.Item {


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

    String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

   /* Date creationDate;
    public Date getCreationDate() { return creationDate; }
    public void setCreationDate(Date creationDate) { this.creationDate = creationDate; } */

  /*  Date modifiedDate;
    public Date getModifiedDate() { return modifiedDate; }
    public void setModifiedDate(Date modifiedDate) { this.modifiedDate = modifiedDate; } */


    TipiMacchina tipoMacchina;
    public TipiMacchina getTipo() {
        return tipoMacchina;
    }
    public void setTipo(TipiMacchina tipoMacchina) {
        this.tipoMacchina = tipoMacchina;
    }


    /*public Macchina() {
        this.targa = UUID.randomUUID().toString();
        this.creationDate = DateTime.now().toDate();
        this.modifiedDate = this.creationDate;
    }*/

    @Override
    public boolean equals(Object o) {
        if(o != null) {
            return o.hashCode() == this.hashCode();
        }
        return false;
    }


    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
