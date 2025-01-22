package ro.ase.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

public class AdaugaCheltuiala extends AppCompatActivity {
EditText suma;
EditText data;
EditText descriere;
Spinner categorie;
Spinner valuta;
Button salveazaCheltuiala;
RadioGroup metodaPlata;
CheckBox esteUrgenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_cheltuiala);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        suma=findViewById(R.id.editTextSuma);
        data=findViewById(R.id.editTextData);
        descriere=findViewById(R.id.editTextDescriere);
        categorie=findViewById(R.id.spnCategorie);
        valuta=findViewById(R.id.spnValuta);
        metodaPlata=findViewById(R.id.radioGroupMetodaPlata);
        String[] categorieArray={"Sanatate","Casa","Cadouri","Educatie","Alimente"};

        ArrayAdapter<String> arrayAdapterCategorie=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,categorieArray);
        categorie.setAdapter(arrayAdapterCategorie);
        String[] valutaArray={"EUR","RON","DOL","CHF"};
        ArrayAdapter<String> arrayAdapterValuta=new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,valutaArray);
valuta.setAdapter(arrayAdapterValuta);
esteUrgenta=findViewById(R.id.checkBox);
salveazaCheltuiala=findViewById(R.id.btnSalvare);
        salveazaCheltuiala.setOnClickListener(view -> {
            double sumaChelt = Double.parseDouble(suma.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date dataChelt = null;
            try {
                dataChelt = sdf.parse(data.getText().toString());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            String descriereChelt = descriere.getText().toString();
            Categorie categorieChelt = Categorie.valueOf(categorie.getSelectedItem().toString());
            Valuta valutaChelt=Valuta.valueOf(valuta.getSelectedItem().toString());
      MetodaPlata metodaPlata1=metodaPlata.getCheckedRadioButtonId()==R.id.radioButtonCASH?MetodaPlata.CASH:MetodaPlata.CARD;
      boolean esteUrgenta1=esteUrgenta.isChecked();
            Cheltuiala cheltuiala = new Cheltuiala(sumaChelt, dataChelt,descriereChelt,valutaChelt,categorieChelt,metodaPlata1,esteUrgenta1);
            Intent intent = new Intent(this, Rapoarte.class);
            intent.putExtra("tranzactie", cheltuiala);
            startActivity(intent);
            finish();
        });

    }
}