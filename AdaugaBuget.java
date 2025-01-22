package ro.ase.moneysaver;

import static java.lang.Double.parseDouble;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaBuget extends AppCompatActivity {
EditText sumaBuget;
Button salveazaBuget;
Boolean isEditing=false;
Button stergeBuget;
long id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_buget);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    sumaBuget=findViewById(R.id.editTextValoareBuget);
    salveazaBuget=findViewById(R.id.btnSalveazaBuget);
    stergeBuget=findViewById(R.id.btnSterge);
        Intent editIntent = getIntent();
        if (editIntent.hasExtra("edit")) {
            isEditing = true;
            Buget editBuget = (Buget) editIntent.getSerializableExtra("edit");
            sumaBuget.setText(String.valueOf(editBuget.getSuma()));
             id=editBuget.getIdUser();
        }
        AppDB dbInstance = AppDB.getInstance(getApplicationContext());
        salveazaBuget.setOnClickListener(view -> {
            if (sumaBuget.getText().toString().isEmpty()) {
                sumaBuget.setError("Introduceți o sumă validă!");
                return;
            }
            float sumaBugetText = Float.parseFloat(sumaBuget.getText().toString());
            ContUser user = (ContUser) getIntent().getSerializableExtra("user");


            Buget buget;
if(isEditing){
  buget=new Buget(sumaBugetText,id);
}else {
    buget = new Buget(sumaBugetText, user.getId());
}
            Intent resultIntent = new Intent();
            if (isEditing) {
                resultIntent.putExtra("edit", buget);
            } else {
                dbInstance.getBugetDAO().insertBuget(buget);
                resultIntent.putExtra("bugetFromIntent", buget);
            }
            setResult(RESULT_OK, resultIntent);
            finish();
        });

        stergeBuget.setOnClickListener(view->{
        if(isEditing){
            Buget buget=(Buget)editIntent.getSerializableExtra("edit");
            dbInstance.getBugetDAO().deleteBugetById(buget.getIdBuget());
            Intent intent=new Intent();
            intent.putExtra("deleteBuget",true);
            setResult(RESULT_OK,intent);
            finish();
        }
    });
    }
}