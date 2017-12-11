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
    private String contenuto;

    public Post(){
        this.titolo = null;
        this.autore = null;
        this.dataCreazeione = new Date();
        this.contenuto = null;
    }

    public Post(String titolo, String autore, Date dataCreazeione, String contenuto) {
        this.titolo = titolo;
        this.autore = autore;
        this.dataCreazeione = dataCreazeione;
        this.contenuto = contenuto;
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

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
}
