package ro.ase.moneysaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.List;

public class VenitAdapter extends ArrayAdapter<Venit> {
        private Context context;
    private int layoutId;
    private List<Venit> venituri;
    private LayoutInflater layoutInflater;
        public VenitAdapter(@NonNull Context context, int layoutId, @NonNull List<Venit> venituri, LayoutInflater layoutInflater) {
        super(context, layoutId, venituri);
        this.context = context;
        this.layoutId = layoutId;
        this.venituri = venituri;
        this.layoutInflater = layoutInflater;
    }

     @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = layoutInflater.inflate(layoutId, parent, false);
        Venit venit = venituri.get(position);


        TextView tvSuma = view.findViewById(R.id.textVSuma);
        TextView tvData = view.findViewById(R.id.textVData);
        TextView tvDescriere = view.findViewById(R.id.textVDescriere);
        TextView tvCategorie = view.findViewById(R.id.textVCategorie);
        TextView tvValuta = view.findViewById(R.id.textVValuta);
        TextView tvRecurenta = view.findViewById(R.id.textVRecurenta);

        tvSuma.setText(String.valueOf(venit.getSuma()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String dataFormatata = sdf.format(venit.getData());
        tvData.setText(dataFormatata);
        tvDescriere.setText(venit.getDescriere());
        tvCategorie.setText(venit.getCategorie().toString());
        tvValuta.setText(venit.getValuta().toString());
        tvRecurenta.setText(venit.getRecurenta().toString());

        return view;
    }

}






//}
