package it.grati_alexandru.socialnetwork.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class Comunity implements Serializable {
    List<Gruppo> listaGruppi;

    public Comunity(){
        listaGruppi = new ArrayList<>();
    }

    public Comunity(List<Gruppo> listaGruppi){
        this.listaGruppi = listaGruppi;
    }

    public List<Gruppo> getListaGruppi(){
        return listaGruppi;
    }

    public void setListaGruppi(List<Gruppo> listaGruppi) {
        this.listaGruppi = listaGruppi;
    }

    public void updateGroupeLastModDate(Gruppo gruppo, Date date){
        for(Gruppo g: getListaGruppi()){
            if(g.getNome().equals(gruppo.getNome())){
                g.setLastModDate(date);
                break;
            }
        }
    }

    public void updateGroupe(Gruppo gruppo){
        Gruppo g = getGroupeByName(gruppo.getNome());
        g = gruppo;
    }

    public Gruppo getGroupeByName(String groupeName){
        for(Gruppo g: getListaGruppi()){
            if(g.getNome().equals(groupeName))
                return g;
        }
        return null;
    }

    public void updateGroupePostList(Gruppo gruppo, List<Post> postList){
        for(Gruppo g : getListaGruppi()){
            if(g.getNome().equals(gruppo.getNome())){
                g.setPostList(postList);
                break;
            }
        }
    }

}
