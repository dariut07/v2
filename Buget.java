package ro.ase.moneysaver;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "bugete",foreignKeys = @ForeignKey(entity = ContUser.class,parentColumns = "id",childColumns = "idUser",onDelete = ForeignKey.CASCADE))
public class Buget implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private long idBuget;
    @ColumnInfo(name = "suma")
    private float suma;
    @ColumnInfo(name = "idUser")
    private long idUser;



    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }
public long getIdBuget() {
        return idBuget;

}
public void setIdBuget(long idBuget) {
        this.idBuget = idBuget;
}

    public Buget(float suma, long idUser) {
        this.suma = suma;
        this.idUser = idUser;
    }
    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return "Buget{" +
                "idBuget=" + idBuget +
                ", suma=" + suma +
                ", idUser=" + idUser +
                '}';
    }
}
