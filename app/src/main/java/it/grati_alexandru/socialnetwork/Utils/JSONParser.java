package it.grati_alexandru.socialnetwork.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.grati_alexandru.socialnetwork.Model.Gruppo;
import it.grati_alexandru.socialnetwork.Model.Post;

/**
 * Created by utente4.academy on 06/12/2017.
 */

public class JSONParser {
    private JSONParser(){}
    public static List<Gruppo> getListaGruppi(String json){
        List<Gruppo> listaGruppi = new ArrayList<>();
        try{
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()){
                Gruppo g = new Gruppo(iterator.next(), new ArrayList<Post>());
                listaGruppi.add(g);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return listaGruppi;
    }
}
