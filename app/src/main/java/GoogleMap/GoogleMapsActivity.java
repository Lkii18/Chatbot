package GoogleMap;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.example.vmac.WatBot.BuildConfig;
import com.example.vmac.WatBot.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

// From Google
import android.content.pm.PackageManager;

import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

//From Geeks for Geeks
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private double latitude =22.302711,  longitude=114.177216;
    private int ProximityRadius = 10000;
    public String url;

    private static final String TAG = GoogleMapsActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition cameraPosition;


    //The entry point to the Places API.
    private PlacesClient placesClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(22.302711, 114.177216);
    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    // Used for selecting the current place.(outdated, should deleted later)
    private static final int M_MAX_ENTRIES = 5;
    private String[] likelyPlaceNames;
    private String[] likelyPlaceAddresses;
    private List[] likelyPlaceAttributions;
    private LatLng[] likelyPlaceLatLngs;
    public String LatLngOfHKRegions[][]= new String[18][2];

    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    public String[] dialogCombination=new String[2]; // possibilities of every choices of the users.
    String attraction = "attraction", restaurant = "restaurant";
    String SentMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        //Get the message sent by user
        Intent intent = getIntent();
        if (intent != null) {
            dialogCombination = intent.getStringArrayExtra("dialogCombination");

        }

        //Find current Location
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();




        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }


        // Construct a PlacesClient
        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
        placesClient = Places.createClient(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Check Corresponding Actions after 5 secs(need to be 5 sec to get location)
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                ActionFliter();
            }
        }, 5000);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

    }

    public void ActionFliter() {

        switch (dialogCombination[0]) {
            case "FindingRestaurants":
                if(dialogCombination[1].equals("n/a")) {
                    System.out.println("Test 1 is:"+dialogCombination[1]);
                    zoomToCurrentLocation("restaurant");
                    break;
                }
                else {
                    System.out.println("Test 2 is:"+dialogCombination[1]);
                    zoomToSpecificLocation("restaurant");}
                break;
            case "FindingAttractions":
                if(dialogCombination[1].equals("n/a"))
                    zoomToCurrentLocation("attraction");
                else
                    zoomToSpecificLocation("attraction");
                break;

        }
    }

    //IF user want to know the restaurant or attractions nearby:
    public void zoomToCurrentLocation(String type){

        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        mMap.clear();
        if(type == "restaurant")
            url = getUrl(latitude, longitude, "restaurant");
        else{
            url = getUrl(latitude, longitude, "tourist_attraction");
        }
        transferData[0] = mMap;
        transferData[1] = url;

        getNearbyPlaces.execute(transferData);

        Toast.makeText(this, "Searching for Nearby Restaurants...", Toast.LENGTH_SHORT).show();
    }

    //IF user want to know the restaurant or attractions in a specific region:
    public void zoomToSpecificLocation(String type){

        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
        mMap.clear();

        try{
            JSONObject jsonObject = new JSONObject(readCoordinates());
            JSONArray jsonArray = jsonObject.getJSONArray("regions");
            for(int i=0; i<jsonArray.length();i++) {
                if (jsonArray.getJSONObject(i).getString("name").equals(dialogCombination[1]))
                {
                    System.out.println("JsonReader is running!");
                    latitude =Double.parseDouble(jsonArray.getJSONObject(i).getString("latitude"));
                    longitude=Double.parseDouble(jsonArray.getJSONObject(i).getString("longitude"));
                    break;
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        if(type == "restaurant")
            url = getUrl(latitude, longitude, "restaurant");
        else{
            url = getUrl(latitude, longitude, "tourist_attraction");
        }
        transferData[0] = mMap;
        transferData[1] = url;

        getNearbyPlaces.execute(transferData);

        Toast.makeText(this, "Searching for the Restaurants in specific location", Toast.LENGTH_SHORT).show();
    }

    private String readCoordinates(){
        String jsonString=null;
        try {
            InputStream is = getAssets().open("Coordinates.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return jsonString;

    }

    private String getUrl(double latitide, double longitude, String nearbyPlace) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=" + ProximityRadius);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyAUJFFocZMuMZvjS5-tXBDDhKXATeJAXA0");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }



    //Current Location
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude =location.getLatitude();
                            longitude=location.getLongitude();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude =mLastLocation.getLatitude();
            longitude=mLastLocation.getLongitude();
        }
    };
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }


}