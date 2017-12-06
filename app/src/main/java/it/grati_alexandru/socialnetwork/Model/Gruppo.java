package it.grati_alexandru.socialnetwork.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class Gruppo implements Serializable {
    private String nome;
    private List<Post> postList;

    public Gruppo(){
        this.nome = null;
        this.postList = new ArrayList<>();
    }

    public Gruppo(String nome, List<Post> postList) {
        this.nome = nome;
        this.postList = postList;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }
}
