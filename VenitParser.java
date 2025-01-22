package ro.ase.moneysaver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VenitParser {
    public static List<Venit> parseJson(String json) throws JSONException {
        try{JSONArray jsonArray=new JSONArray(json);
        return parserVenituri(jsonArray);}
        catch (JSONException e){
            throw new RuntimeException();
        }
    }
    private static List<Venit> parserVenituri(JSONArray jsonArray) throws JSONException {
        List<Venit> venituri=new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++) {
        venituri.add(parserVenit(jsonArray.getJSONObject(i)));
        }
            return venituri;
    }
    private static Venit parserVenit(JSONObject jsonObject) throws JSONException {
        double suma= jsonObject.getDouble("suma");
        String dataString = jsonObject.getString("data");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date data = null;
        try {
            data = dateFormat.parse(dataString);
        } catch (ParseException e) {
            throw new JSONException("Data este invalidă");
        }
        String descriere=jsonObject.getString("descriere");
        String val=jsonObject.getString("valuta");
        Valuta valuta=Valuta.valueOf(val);
        String cat=jsonObject.getString("categorie");
        Categorie categorie=Categorie.valueOf(cat);
        String metoda=jsonObject.getString("metodaPlata");
        MetodaPlata metodaPlata=MetodaPlata.valueOf(metoda);
        String rec=jsonObject.getString("recurenta");
        Recurenta recurenta=Recurenta.valueOf(rec);
        return new Venit(suma,data,descriere,valuta,categorie,metodaPlata,recurenta);
    }
}
