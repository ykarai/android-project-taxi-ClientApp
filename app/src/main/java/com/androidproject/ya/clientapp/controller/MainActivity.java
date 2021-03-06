package com.androidproject.ya.clientapp.controller;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.ya.clientapp.R;
import com.androidproject.ya.clientapp.model.backend.BackendFactory;
import com.androidproject.ya.clientapp.model.backend.Const;
import com.androidproject.ya.clientapp.model.datasource.Utils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
//import com.google.android.gms.common.api.Status;
//import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import com.google.android.gms.location.places.Place;
//import com.google.android.gms.location.places.ui.PlacePicker;

public class MainActivity extends Activity implements View.OnClickListener {

    //try78
    // private Edittext nameTextView;
    private EditText idEditText;
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText eMailEditText;


    private Button findLocationButton;
//    private Button findLocationButton2;
    private Button sendClientButton;
private ImageButton findLocationButton2;

    // private TextView locationTextView;
    private TextView distanceTextView;


    Location locationA = new Location("A");//= new Location(from);
    Location locationB = new Location("B");//= new Location(to);
    public String address;

    //   final int PLACE_PICKER_REQUEST = 1;


    private PlaceAutocompleteFragment placeAutocompleteFragment1;
    private PlaceAutocompleteFragment placeAutocompleteFragment2;

    // Acquire a reference to the system Location Manager
    LocationManager locationManager;


    // Define a listener that responds to location updates
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews(getBaseContext());


//        task.addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful())
//                    Toast.makeText(MainActivity.this,
//                            "Successful", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(MainActivity.this,
//                            "Error", Toast.LENGTH_LONG).show();
//            }
//        });


        //// code that can helps bhmshech
//        task.addOnFailureListener(new OnFailureListener(){
//            @Override
//            public void onFailure(@NonNull Exception e) {
//               String s= e.getMessage().toString();
//                Toast.makeText(MainActivity.this,
//                        s, Toast.LENGTH_LONG).show();
//            }
//        });


    }


    private void findViews(final Context context) {




        placeAutocompleteFragment1 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment1);
        placeAutocompleteFragment2 = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment2);
        placeAutocompleteFragment1.setHint("כתובת  מוצא");
        placeAutocompleteFragment2.setHint("כתובת היעד");

        idEditText = (EditText) findViewById(R.id.idEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        eMailEditText = (EditText) findViewById(R.id.eMailEditText);

//        findLocationButton = (Button) findViewById(R.id.findLocationButton);
//        findLocationButton.setOnClickListener(this);

        findLocationButton2 = (ImageButton) findViewById(R.id.findLocationButton2);
        findLocationButton2.setOnClickListener(this);




        sendClientButton = (Button) findViewById(R.id.sendClientButton);
      //  sendClientButton.setEnabled(false);
//        sendClientButton.setOnClickListener(this);
        Button sendClientButton = findViewById(R.id.sendClientButton);
        sendClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isEmpty(idEditText)) {
                    Toast.makeText(context, " you most inter id", Toast.LENGTH_SHORT).show();

                } else {
                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.dialog_add_client);
                    dialog.show();

                    Button dialog_cancel = dialog.findViewById(R.id.dialog_cancel); //כפתור שנמצא בתוך הדיאלוג
                    dialog_cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    Button dialog_ok = dialog.findViewById(R.id.dialog_ok);  //כפתור שנמצא בתוך הדיאלוג
                    dialog_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            addClient();
                            dialog.dismiss();


                        }
                    });
                }

            }
        });

        //locationTextView = (TextView) findViewById(R.id.locationTextView);
        distanceTextView = (TextView) findViewById(R.id.distanceTextView);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {

                //showSunTimes(location.getLatitude(), location.getLongitude()); /// ...

                // Called when a new location is found by the network location provider.
                //    Toast.makeText(getBaseContext(), location.toString(), Toast.LENGTH_LONG).show();
                //locationTextView.setText(getPlace(location));////location.toString());


                address=getPlace(location);
                placeAutocompleteFragment1.setText(address);
                locationA=location;

                // Remove the listener you previously added
                //  locationManager.removeUpdates(locationListener);
            }


            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        placeAutocompleteFragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                locationA.setLatitude(place.getLatLng().latitude);
                locationA.setLongitude(place.getLatLng().longitude);
                address=getPlace(locationA);
                // showDistance();
                // .getAddress().toString();//get place details here
            }

            @Override
            public void onError(Status status) {

            }
        });

        placeAutocompleteFragment2.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                //  to = place.getAddress().toString();//get place details here
                locationB.setLatitude(place.getLatLng().latitude);
                locationB.setLongitude(place.getLatLng().longitude);
                showDistance();
            }

            @Override
            public void onError(Status status) {

            }
        });


    }
  //  check if id editText empty
    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    @SuppressLint("StaticFieldLeak")
    private void getLocation() {

        //     Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);

        } else {

            // getting GPS status
            Boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            Boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            // getting network status
            Boolean isPassivenabled = locationManager
                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);



            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 8000, 0, locationListener);

                if (locationManager != null) {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location != null) {

                        //latitude = location.getLatitude();
                        //longitude = location.getLongitude();
                    }
                }

            }
            else if ( isNetworkEnabled) {

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

                if (locationManager != null) {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {

                        //latitude = location.getLatitude();
                        //longitude = location.getLongitude();
                    }
                }
                return;
            }
            else if (isPassivenabled) {

                locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, locationListener);

                if (locationManager != null) {
                    Location location = locationManager
                            .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    if (location != null) {

                        //latitude = location.getLatitude();
                        //longitude = location.getLongitude();
                    }
                }
                return;
            }
            else
            {
                Toast.makeText(this, "cant access location PROVIDER", Toast.LENGTH_SHORT).show();
            }




            // Android version is lesser than 6.0 or the permission is already granted.
            //stopUpdateButton.setEnabled(true);
            //getLocationButton.setEnabled(false);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

    }


    public String getPlace(Location location) {


//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
//        String cityName = addresses.get(0).getAddressLine(0);


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);


            if (addresses.size() > 0) {
                String cityName = addresses.get(0).getAddressLine(0);
//              String stateName = addresses.get(0).getAddressLine(1);
                // String countryName = addresses.get(0).getAddressLine(2);
                return cityName; //+ "\n" + stateName + "\n" + countryName;
            }

            return "no place: \n (" + location.getLongitude() + " , " + location.getLatitude() + ")";
        } catch (
                IOException e)

        {
            e.printStackTrace();
        }
        return "IOException ...";
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 5) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //stopUpdateButton.setEnabled(true);
                //getLocationButton.setEnabled(false);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the location", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onClick(View v) {
//        if (v == sendClientButton)
//            addClient();
        if (v == findLocationButton2)
            getLocation();

//            if (v == searchButton) {
//
//                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
//
//                try {
//                    startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
//                } catch (GooglePlayServicesRepairableException e) {
//                    e.printStackTrace();
//                } catch (GooglePlayServicesNotAvailableException e) {
//                    e.printStackTrace();
//                }
//            }

    }

    //    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == PLACE_PICKER_REQUEST) {
//            if (resultCode == RESULT_OK) {
//                Place place = PlacePicker.getPlace(data, this);
//                String toastMsg = String.format("Place: %s", place.getName());
//
//                Date date = new Date();// date of calculation
//
//                SunTimes times = SunTimes.compute()
//                        .on(date)       // set a date
//                        .at(place.getLatLng().latitude,place.getLatLng().longitude)   // set a location
//                        .execute();     // get the results
//                toastMsg+= "\nSunrise: " + times.getRise();
//                toastMsg+= "\nSunset: " + times.getSet();
//
//
//                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
//
//
//            }
//        }
//    }
    @SuppressLint("StaticFieldLeak")
    private void addClient() {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Const.ClientConst.ID, this.idEditText.getText().toString());
        contentValues.put(Const.ClientConst.NAME, this.nameEditText.getText().toString());
        contentValues.put(Const.ClientConst.PHONE, this.phoneEditText.getText().toString());
        contentValues.put(Const.ClientConst.EMAIL, this.eMailEditText.getText().toString());
        //Long x = BackendFactory.getDB().addClient(contentValues);


        new AsyncTask<Void, Void, Long>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //openDialog();
                Toast.makeText(getBaseContext(),
                        "send request.... ", Toast.LENGTH_SHORT).show();

            }
            @Override
            protected Long doInBackground(Void... params) {
                try {
                    return BackendFactory.getDB().addClient(contentValues,address, locationA, locationB, new Utils.Action<Long>() {
                        @Override
                        public void onSuccess(Long obj) {
                            Toast.makeText(getBaseContext(), "Upload successful id: " + obj, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Exception exception) {
                            Toast.makeText(getBaseContext(), "Error \n" + exception.getMessage(), Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onProgress(String status, double percent) {

                        }
                    });
                }
                catch (Exception e){
                    Toast.makeText(getBaseContext(), "Error ", Toast.LENGTH_LONG).show();
                    return  Long.valueOf(0);
                }
            }

            @Override
            protected void onPostExecute(Long aLong) {
//
//                if (aLong == Long.valueOf(0))
//                    Toast.makeText(getBaseContext(), "problem with uploud", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(getBaseContext(), "client request added ", Toast.LENGTH_LONG).show();
            }

        }.execute();








//        new AsyncTask<Void, Void, Long>() {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                //openDialog();
//                Toast.makeText(getBaseContext(),
//                        "send request ", Toast.LENGTH_LONG).show();
//
//            }
//
//            @Override
//            protected Long doInBackground(Void... params) {
//
//                return Long.valueOf(BackendFactory.getDB().addClient(contentValues, locationA, locationB));
//            }
//
//            @Override
//            protected void onPostExecute(Long aLong) {
//                super.onPostExecute(aLong);
//                try
//                {
//                    Thread.sleep(1000);
//                }
//                catch(InterruptedException ex)
//                {
//                    Thread.currentThread().interrupt();
//                }
//                if (aLong == Long.valueOf(0))
//                    Toast.makeText(getBaseContext(), "problem with uploud", Toast.LENGTH_LONG).show();
//                else
//                    Toast.makeText(getBaseContext(), "client request added ", Toast.LENGTH_LONG).show();
//            }
//
//
//        }.execute();


    }

    private void showDistance() {

        float[] results = new float[1];
        Location.distanceBetween(locationA.getLatitude(), locationA.getLongitude(),
                locationB.getLatitude(), locationB.getLongitude(), results);

        float distance = locationA.distanceTo(locationB);


        if (distance > 1000) {
            String d = String.format("%.1f", distance / 1000);
            distanceTextView.setText("Distance :   " +d+ "  km");
        }
        else {

            String d = String.format("%.0f", distance );
            distanceTextView.setText("Distance :  " + distance + "  meter");
        }

//        if (distance > 1000)
//            distanceTextView.setText("Distance : " + distance / 1000 + " km");
//        else {
//            distanceTextView.setText("Distance :" + distance + " meter");
//        }


    }

    public void openDialog() {
        final Dialog dialog = new Dialog(this); // Context, this, etc.
        dialog.setContentView(R.layout.dialog_add_client);
        dialog.setTitle("1111111");
        dialog.show();
    }








}





