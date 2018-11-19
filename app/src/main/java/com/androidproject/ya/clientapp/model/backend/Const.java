package com.androidproject.ya.clientapp.model.backend;

import android.content.ContentValues;

import com.androidproject.ya.clientapp.model.entities.Client;

public class Const
{


    public static class ClientConst {
        public static final String NAME = "name";
        public static final String ID ="id" ;
        public static final String PHONE = "phone";
        public static final String EMAIL ="eMail" ;
    }


    public static ContentValues CourseToContentValues(Client client) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(ClientConst.NAME, client.getName());
        contentValues.put(ClientConst.ID, client.getId());
        contentValues.put(ClientConst.PHONE, client.getPhone());
        contentValues.put(ClientConst.EMAIL, client.geteMail());





        return contentValues;
    }




    public static Client ContentValuesToCourse(ContentValues contentValues) {

        Client client = new Client();

        client.setName(contentValues.getAsString(ClientConst.NAME));
        client.setId(contentValues.getAsLong(ClientConst.ID));
        client.setPhone(contentValues.getAsString(ClientConst.PHONE));
        client.seteMail(contentValues.getAsString(ClientConst.EMAIL));

        return client;

    }

    }
