package ro.ase.moneysaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class TranzactieAdapter extends ArrayAdapter<Tranzactie> {
    private Context context;
    private int layoutId;
    private List<Tranzactie> tranzactii;
    private LayoutInflater layoutInflater;

    public TranzactieAdapter(@NonNull Context context, int layoutId, @NonNull List<Tranzactie> tranzactii , LayoutInflater layoutInflater) {
        super(context, layoutId, tranzactii);
        this.context = context;
        this.layoutId = layoutId;
        this.tranzactii = tranzactii;
        this.layoutInflater = layoutInflater;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=layoutInflater.inflate(layoutId,parent,false);
        Tranzactie tranzactie=tranzactii.get(position);
        TextView tvSuma=view.findViewById(R.id.textVSuma);
        TextView tvData=view.findViewById(R.id.textVData);
        TextView tvDescriere=view.findViewById(R.id.textVDescriere);
        TextView tvCategorie=view.findViewById(R.id.textVCategorie);
        TextView tvValuta=view.findViewById(R.id.textVValuta);
        tvSuma.setText(String.valueOf(tranzactie.getSuma()));
        SimpleDateFormat sdf=new SimpleDateFormat("dd.MM.yyyy");
        String dataFormatata=sdf.format(tranzactie.getData());
        tvData.setText(dataFormatata);
        tvDescriere.setText(tranzactie.getDescriere());
        tvCategorie.setText(tranzactie.getCategorie().toString());
        tvValuta.setText(tranzactie.getValuta().toString());
        return view;
    }
}
