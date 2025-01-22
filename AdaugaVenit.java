package ro.ase.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdaugaVenit extends AppCompatActivity {
    EditText suma;
    EditText data;
    EditText descriere;
    Spinner categorie;
    Spinner valuta;
    Button salveazaVenit;
    RadioGroup metodaPlata;
    Spinner recurenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_venit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        suma=findViewById(R.id.editTextSumaPrimita);
        data=findViewById(R.id.editTextDataPrimire);
        descriere=findViewById(R.id.editTextDescrierePrimire);
        categorie=findViewById(R.id.spnCategorieVenit);
        valuta=findViewById(R.id.spnValutaVenit);
        metodaPlata=findViewById(R.id.radioGroupMetodaPrimire);
        String[] categorieArray={"Salariu","Cadou","Alte"};
        ArrayAdapter<String> arrayAdapterCategorie=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categorieArray);
        categorie.setAdapter(arrayAdapterCategorie);
        String[] valutaArray={"EUR","RON","DOL","CHF"};
        ArrayAdapter<String> arrayAdapterValuta=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,valutaArray);
        valuta.setAdapter(arrayAdapterValuta);
        recurenta=findViewById(R.id.spnRecurenta);
ArrayAdapter<CharSequence> arrayAdapterRecurenta=ArrayAdapter.createFromResource(this,R.array.Recurenta,android.R.layout.simple_spinner_dropdown_item);
        recurenta.setAdapter(arrayAdapterRecurenta);
        salveazaVenit=findViewById(R.id.btnSalvareVenit);
        salveazaVenit.setOnClickListener(view -> {
MetodaPlata metodaPlata1=metodaPlata.getCheckedRadioButtonId()==R.id.radioButtonCASH?MetodaPlata.CASH:MetodaPlata.CARD;
            double sumaVenit = Double.parseDouble(suma.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date dataVenit = null;
            try {
                dataVenit = sdf.parse(data.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            String descriereVenit = descriere.getText().toString();

Recurenta recurentaVenit = Recurenta.valueOf(recurenta.getSelectedItem().toString());
            Categorie categorieVenit = Categorie.valueOf(categorie.getSelectedItem().toString());
            Valuta valutaVenit = Valuta.valueOf(valuta.getSelectedItem().toString());
            Venit venit = new Venit(sumaVenit,dataVenit,descriereVenit,valutaVenit,categorieVenit,metodaPlata1,recurentaVenit);
            Intent intent = new Intent(this, Rapoarte.class);
            intent.putExtra("tranzactie", venit);
            startActivity(intent);
            finish();
        });
    }
}