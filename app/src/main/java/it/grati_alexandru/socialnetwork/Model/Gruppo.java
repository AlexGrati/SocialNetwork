package it.grati_alexandru.socialnetwork.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.grati_alexandru.socialnetwork.Utils.DateConversion;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class Gruppo implements Serializable {
    private String nome;
    private List<Post> postList;
    private Date lastModDate;

    public Gruppo(){
        this.nome = null;
        this.postList = new ArrayList<>();
        this.lastModDate = new Date();
    }

    public Gruppo(String nome, List<Post> postList, Date lastModDate) {
        this.nome = nome;
        this.postList = postList;
        this.lastModDate = lastModDate;
    }

    public Date getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Date lastModDate) {
        this.lastModDate = lastModDate;
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

    public Post getPostByTitle(String postTitle){
        for(Post p : getPostList()){
            if(p.getTitolo().equals(postTitle))
                return p;
        }
        return null;
    }

    public void addPost(Post p){
        getPostList().add(p);
    }
}
