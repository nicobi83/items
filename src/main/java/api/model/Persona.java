package api.model;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Thomas on 06/05/2016.
 */
public class Persona {

    Integer id;
    String nome;
    String cognome;
    Date dataNascita;
    Indirizzo indirizzoResidenza;
    Indirizzo indirizzoDomicilio;
    Character sesso;
    String email;
    String numTel;
    String numCel;

    Set<Account> account;
    List<String> cittadinanza;


    public Persona() {
        this.nome = "";
        this.cognome = "";
        this.dataNascita = new Date();
        this.sesso = 'm';
        this.email = "";
        this.numTel = "";
        this.numCel = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public Indirizzo getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    public void setIndirizzoResidenza(Indirizzo indirizzoResidenza) {
        this.indirizzoResidenza = indirizzoResidenza;
    }

    public Indirizzo getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    public void setIndirizzoDomicilio(Indirizzo indirizzoDomicilio) {
        this.indirizzoDomicilio = indirizzoDomicilio;
    }

    public Character getSesso() {
        return sesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getNumCel() {
        return numCel;
    }

    public void setNumCel(String numCel) {
        this.numCel = numCel;
    }

    public void setSesso(Character sesso) {
        this.sesso = sesso;
    }

    public Set<Account> getAccount() {
        return account;
    }

    public void setAccount(Set<Account> account) {
        this.account = account;
    }

    public List<String> getCittadinanza() {
        if(this.cittadinanza == null){
            this.cittadinanza = new ArrayList<>();
            this.cittadinanza.add("ITA");
        }
        return cittadinanza;
    }

    public void setCittadinanza(List<String> cittadinanza) {
        this.cittadinanza = cittadinanza;
    }

    public void addCittadinanza(String cittadinanza){
        if(!StringUtils.isBlank(cittadinanza)) {
            if (!this.getCittadinanza().contains(cittadinanza.toUpperCase())) {
                this.getCittadinanza().add(cittadinanza.toUpperCase());
            }
        }
    }

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


    @Override
    public String toString() {
        return "Persona{" +
          "email='" + email + '\'' +
          '}';
    }

}

