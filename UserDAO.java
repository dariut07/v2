package ro.ase.moneysaver;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUser(ContUser user);
    @Query("SELECT * FROM useri")
    List<ContUser> getAll();
    @Query("SELECT * FROM useri WHERE id = :idCautat")
    ContUser getById(long idCautat);
    @Query("DELETE FROM useri")
    void deleteAll();
    @Query("DELETE FROM useri WHERE id = :id")
    void deleteUserById(long id);
    @Query("SELECT * FROM useri WHERE email = :email AND parola = :parola LIMIT 1")
    ContUser getUserByEmailAndPassword(String email, String parola);
    @Query("update useri set nume=:nume,prenume=:prenume,email=:email,parola=:parola where id=:id")
    void updateUser(String nume,String prenume,String email,String parola,long id);



}
