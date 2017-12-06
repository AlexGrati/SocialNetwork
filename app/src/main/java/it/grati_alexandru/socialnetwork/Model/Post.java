package it.grati_alexandru.socialnetwork.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class Post implements Serializable{
    private String titolo;
    private String autore;
    private Date dataCreazeione;

    public Post(){
        this.titolo = null;
        this.autore = null;
        this.dataCreazeione = new Date();
    }

    public Post(String titolo, String autore, Date dataCreazeione) {
        this.titolo = titolo;
        this.autore = autore;
        this.dataCreazeione = dataCreazeione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public Date getDataCreazeione() {
        return dataCreazeione;
    }

    public void setDataCreazeione(Date dataCreazeione) {
        this.dataCreazeione = dataCreazeione;
    }
}
