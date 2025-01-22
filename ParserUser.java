package ro.ase.moneysaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParserUser {
    public static List<ContUser> parseJson(String json) throws JSONException {
        try{
        JSONArray jsonArray=new JSONArray(json);
        return parserUseri(jsonArray);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    private static List<ContUser> parserUseri(JSONArray jsonArray) throws JSONException {
        List<ContUser> useri=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++) {
            useri.add(parseContUser(jsonArray.optJSONObject(i)));
        }
        return useri;
    }
    private static ContUser parseContUser(JSONObject jsonObject) throws JSONException {
        String nume=jsonObject.getString("nume");
        String prenume=jsonObject.getString("prenume");
        String email=jsonObject.getString("email");
        String parola=jsonObject.getString("parola");
        return new ContUser(nume,prenume,email,parola);
    }
}
