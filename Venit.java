package ro.ase.moneysaver;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
 enum Recurenta {
    Lunar, Saptamanal, Anual, Unic
}

@Entity(tableName = "venituri")
public class Venit extends Tranzactie {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "recurenta")
    private Recurenta recurenta;
    public Recurenta getRecurenta() {
        return recurenta;
    }
    public void setRecurenta(Recurenta recurenta) {
        this.recurenta = recurenta;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public Venit(double suma, Date data, String descriere, Valuta valuta, Categorie categorie, MetodaPlata metodaPlata, Recurenta recurenta) {
        super(suma, data, descriere, valuta, categorie, metodaPlata);
        this.recurenta = recurenta;
    }

    @Override
    public String toString() {
        return "Venit: " + super.toString();
    }
}
