package com.androidproject.ya.clientapp.model.datasource;

import android.content.ContentValues;
import android.location.Location;
import android.support.annotation.NonNull;

import com.androidproject.ya.clientapp.model.backend.Backend;
import com.androidproject.ya.clientapp.model.entities.Client;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.androidproject.ya.clientapp.model.backend.Const.ContentValuesToCourse;

public class Firebase_DBManager implements Backend {

    static FirebaseDatabase database;
    static DatabaseReference clientsRef;
    static List<Client> clientList;
    static boolean flag;

    static {
        database = FirebaseDatabase.getInstance();
        clientsRef = database.getReference("clients");
        clientList = new ArrayList<>();
        flag = false;
    }


    public interface Action<T> {
        void onSuccess(T obj);

        void onFailure(Exception exception);

        void onProgress(String status, double percent);
    }

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);
    }


    @Override
    public Long addClient(ContentValues values, Location a, Location b) {

        Client client = ContentValuesToCourse(values);
        client.setStartPoint(a);
        client.setDestinationPoint(b);
        String key = client.getId().toString();
        Task<Void> upload = clientsRef.child(key).setValue(client);
        //clientsRef.child(key).setValue(locationA);

        upload.addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> upload) {
                if (upload.isSuccessful()) {
                    flagTrue();
                }
            }

        });
        if (flag == false)
            return client.getId();
        else
            return Long.valueOf(0);
    }

    private void flagTrue() {
        flag = true;
    }


    @Override
    public boolean removeClient(Long id) {
        String key = id.toString();
        clientsRef.child(key).removeValue();


        return true;
    }

    @Override
    public boolean updateClient(Long id, ContentValues values) {

        Client client = ContentValuesToCourse(values);
        String key = id.toString();
        //client.setName(name);  // to check
        clientsRef.child(key).setValue(client);

        return true;
    }

    @Override
    public List<Client> getClients() {


        return clientList;
    }
}
