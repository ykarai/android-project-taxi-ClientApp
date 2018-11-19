package com.androidproject.ya.clientapp.model.backend;

import com.androidproject.ya.clientapp.model.datasource.Firebase_DBManager;
import com.androidproject.ya.clientapp.model.datasource.List_DBManager;


public class BackendFactory {

    static Backend DB = null;


    public static Backend getDB() {

//        return new List_DBManager();
        if (DB == null)
            //DB = new List_DBManager();
            DB= new Firebase_DBManager();


        return DB;
    }

    public static int t() {
        return 1;
    }
}

