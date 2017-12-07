package it.grati_alexandru.socialnetwork.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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

    public static List<Post> getAllPosts(String jsonString){
        List<Post> postList = new ArrayList<>();
        try{
            JSONObject jsonObjectPost = new JSONObject(jsonString);
            Iterator iterator = jsonObjectPost.keys();
            while (iterator.hasNext()){
                String postKey = (String)iterator.next();
                JSONObject jsonObjectPostData = new JSONObject(postKey);
                Iterator iteratorPostData = jsonObjectPostData.keys();
                Post tempPost = new Post();
                while (iteratorPostData.hasNext()){
                    String dataKey = (String) iteratorPostData.next();
                    String postVal = jsonObjectPostData.getString(dataKey);
                    switch (dataKey){
                        case "Titolo":
                            tempPost.setTitolo(postVal);
                            break;
                        case "Autore":
                            tempPost.setAutore(postVal);
                            break;
                        case "Data_Creazione":
                            tempPost.setDataCreazeione(JSONParser.formatStringToDate(postVal));
                            break;
                    }
                }
                postList.add(tempPost);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return postList;
    }

    public static Date formatStringToDate(String dateString){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
        return  format.parse(dateString, new ParsePosition(0));
    }
}
