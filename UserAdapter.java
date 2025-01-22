package ro.ase.moneysaver;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<ContUser> {
private Context context;
private int layoutId;
private List<ContUser> useri;
    private LayoutInflater layoutInflater;

    public UserAdapter(@NonNull Context context, int layoutId, @NonNull  List<ContUser> useri, LayoutInflater inflater) {
        super(context, layoutId, useri);
        this.layoutInflater = inflater;
        this.context = context;
        this.layoutId = layoutId;
        this.useri = useri;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view =layoutInflater.inflate(layoutId,parent,false);
        ContUser user=useri.get(position);
        TextView tvNumePrenume = view.findViewById(R.id.tvNumePrenume);
        TextView tvEmail = view.findViewById(R.id.tvEmail);

        tvNumePrenume.setText(user.getNume() + " " + user.getPrenume());
 tvEmail.setText(user.getEmail());
        return view;
    }
}

