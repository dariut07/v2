package ro.ase.moneysaver;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    Button logIn;
    Button register;
    EditText email;
    EditText parola;
    List<ContUser> userList = new ArrayList<>();
    ListView lvUseri;
    private int pozitieUserEditatInLista;
    private ActivityResultLauncher<Intent> launcher;
    private String jsonUrl="https://www.jsonkeeper.com/b/K5SO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        AppDB dbInstance = AppDB.getInstance(getApplicationContext());
        lvUseri = findViewById(R.id.listViewUseri);
        userList = dbInstance.getUserDAO().getAll();
        UserAdapter userAdapter=new UserAdapter(getApplicationContext(),R.layout.view_useri,userList,getLayoutInflater());
        lvUseri.setAdapter(userAdapter);
        incarcareUseriDinRetea();
        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
           if(result.getData().hasExtra("userFromIntent")){
               Intent intent=result.getData();
               ContUser user= (ContUser) intent.getSerializableExtra("userFromIntent");
               if(user!=null){
                   userList.clear();
                   userList.addAll(dbInstance.getUserDAO().getAll());
                   UserAdapter adapter= (UserAdapter) lvUseri.getAdapter();
                   adapter.notifyDataSetChanged();
               }

           } else if (result.getData().hasExtra("edit")) {
               Intent intent = result.getData();
               ContUser user = (ContUser) intent.getSerializableExtra("edit");
               if (user != null) {
                  ContUser userEditat=userList.get(pozitieUserEditatInLista);
                  dbInstance.getUserDAO().updateUser(user.getNume(),user.getPrenume(),user.getEmail(),user.getParola(),userEditat.getId());
                  userList.clear();
                  userList.addAll(dbInstance.getUserDAO().getAll());
                   UserAdapter adapter= (UserAdapter) lvUseri.getAdapter();
                  adapter.notifyDataSetChanged();

               }
           } else if (result.getData().hasExtra("deleteUser")) {
               Toast.makeText(this, "Cont sters cu succes!", Toast.LENGTH_SHORT).show();
               userList.clear();
               userList.addAll(dbInstance.getUserDAO().getAll());
               UserAdapter adapter= (UserAdapter) lvUseri.getAdapter();
               adapter.notifyDataSetChanged();
           }

        });

        logIn = findViewById(R.id.btnLogIn);
        register = findViewById(R.id.btnRegister);
        email = findViewById(R.id.editTextEmail);
        parola = findViewById(R.id.editTextPassword);

        logIn.setOnClickListener(view -> {
            String emailText = email.getText().toString();
            String parolaText = parola.getText().toString();

            ContUser utilizator = dbInstance.getUserDAO().getUserByEmailAndPassword(emailText, parolaText);
            if (utilizator != null) {
                Toast.makeText(this, "Autentificare reușită!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("user", utilizator);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Credentiale invalide!", Toast.LENGTH_SHORT).show();
            }
        });

        register.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Inregistrare.class);
            launcher.launch(intent);
        });

        lvUseri.setOnItemClickListener((adapterView, view, position, id) -> {
            pozitieUserEditatInLista = position;
            Intent intent = new Intent(getApplicationContext(), Inregistrare.class);
            intent.putExtra("edit", userList.get(position));
            launcher.launch(intent);
        });
    }
    private void incarcareUseriDinRetea(){
        Thread thread=new Thread(){
            @Override
            public void run() {
                    HttpsManager manager=new HttpsManager(jsonUrl);
                    String json=manager.procesare();
                    new Handler(getMainLooper()).post(()->{
                        try {
                            getUseri(json);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
            }
        };

        thread.start();
    }
    private void getUseri(String json) throws JSONException {
        List<ContUser> parsedUsers = ParserUser.parseJson(json);

        AppDB dbInstance = AppDB.getInstance(getApplicationContext());
        for (ContUser user : parsedUsers) {
            if (dbInstance.getUserDAO().getUserByEmailAndPassword(user.getEmail(),user.getParola())==null) {
                dbInstance.getUserDAO().insertUser(user);
            }
        }
        // Reîncarcă lista locală din baza de date
        userList.clear();
        userList.addAll(dbInstance.getUserDAO().getAll());
        // Notifică adapterul pentru a actualiza lista afișată
        UserAdapter userAdapter = (UserAdapter) lvUseri.getAdapter();
        userAdapter.notifyDataSetChanged();
    }


}
