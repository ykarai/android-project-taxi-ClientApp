package com.androidproject.ya.clientapp.model.backend;

import android.content.ContentValues;
import android.location.Location;

import com.androidproject.ya.clientapp.model.datasource.Utils;
import com.androidproject.ya.clientapp.model.entities.Client;

import java.util.List;

public interface Backend {

    Long addClient(ContentValues client, String city, Location a, Location b, Utils.Action<Long> action);
    boolean removeClient(Long id);
    boolean updateClient(Long id, ContentValues values);
    List<Client> getClients();





}

