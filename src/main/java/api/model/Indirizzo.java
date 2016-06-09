package api.model;

/**
 * Created by Luca on 06/05/2016.
 */
public class Indirizzo {
    String id;
    String via;
    String cap;
    String citta;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Indirizzo indirizzo = (Indirizzo) o;

        return id.equals(indirizzo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Indirizzo{" +
          "cap='" + cap + '\'' +
          ", via='" + via + '\'' +
          '}';
    }
}
