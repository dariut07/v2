package ro.ase.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Rapoarte extends AppCompatActivity {
    Spinner perioadeTimp;
    Switch swCheltuieli;
    Switch swVenituri;
    Spinner spnCateg;
    Button btnDocument;
    ListView listViewTranzactii;
    private String jsonUrlVenit = "https://www.jsonkeeper.com/b/O18U";
    private String jsonUrlCheltuiala = "https://www.jsonkeeper.com/b/YTO0";
    ArrayList<Tranzactie> tranzactiiList=new ArrayList<>();
    ArrayList<Cheltuiala> cheltuieliList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rapoarte);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spnCateg = findViewById(R.id.spnCateg);
        perioadeTimp = findViewById(R.id.spnPerioadaTimp);
        ArrayAdapter<CharSequence> arrayAdapterCateg = ArrayAdapter.createFromResource(this, R.array.categorii, android.R.layout.simple_spinner_dropdown_item);
        spnCateg.setAdapter(arrayAdapterCateg);

        String[] perioade = {"Azi", "O saptamana", "O luna"};
        ArrayAdapter<String> arrayAdapterPerioade = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, perioade);
        perioadeTimp.setAdapter(arrayAdapterPerioade);

        swCheltuieli = findViewById(R.id.switchCheltuieli);
        swVenituri = findViewById(R.id.switchVenituri);
        btnDocument = findViewById(R.id.btnObtineDocument);
        listViewTranzactii = findViewById(R.id.lv);
        Intent intent = getIntent();
        Tranzactie tranzactie = (Tranzactie) intent.getSerializableExtra("tranzactie");
        if (tranzactie != null) {
            if (tranzactie instanceof Cheltuiala) {
                cheltuieliList.add((Cheltuiala) tranzactie);
            }
            tranzactiiList.add(tranzactie);
        }

        TranzactieAdapter tranzactieAdapter = new TranzactieAdapter(getApplicationContext(), R.layout.view_tranzactii, tranzactiiList, getLayoutInflater());
        listViewTranzactii.setAdapter(tranzactieAdapter);

        incarcaVenituriDinRetea();
        incarcaCheltuieliDinRetea();

        btnDocument.setOnClickListener(view -> {
            CheltuialaAdapter cheltuieliAdapter = new CheltuialaAdapter(getApplicationContext(), R.layout.view_cheltuiala, cheltuieliList, getLayoutInflater());
            listViewTranzactii.setAdapter(cheltuieliAdapter);
        });
    }

    private void incarcaVenituriDinRetea() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(jsonUrlVenit);
                String json = manager.procesare();
                new Handler(getMainLooper()).post(() -> {
                    try {
                        getVenituri(json);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };
        thread.start();
    }

    private void incarcaCheltuieliDinRetea() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                HttpsManager manager = new HttpsManager(jsonUrlCheltuiala);
                String json = manager.procesare();
                new Handler(getMainLooper()).post(() -> {
                    try {
                        getCheltuieli(json);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };
        thread.start();
    }

    private void getVenituri(String json) throws JSONException {
        tranzactiiList.addAll(VenitParser.parseJson(json));
        TranzactieAdapter tranzactieAdapter = (TranzactieAdapter) listViewTranzactii.getAdapter();
        tranzactieAdapter.notifyDataSetChanged();
    }

    private void getCheltuieli(String json) throws JSONException {
        List<Cheltuiala> cheltuieli = CheltuialaParser.parseJson(json);
        cheltuieliList.addAll(cheltuieli);
        tranzactiiList.addAll(cheltuieli);
        TranzactieAdapter tranzactieAdapter = (TranzactieAdapter) listViewTranzactii.getAdapter();
        tranzactieAdapter.notifyDataSetChanged();
    }
}
