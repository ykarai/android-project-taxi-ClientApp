package com.androidproject.ya.clientapp.model.backend;

import android.content.ContentValues;
import android.location.Location;

import com.androidproject.ya.clientapp.model.entities.Client;

import java.util.List;

public interface Backend {

    Long addClient(ContentValues client, Location a, Location b);
    boolean removeClient(Long id);
    boolean updateClient(Long id, ContentValues values);
    List<Client> getClients();





}

