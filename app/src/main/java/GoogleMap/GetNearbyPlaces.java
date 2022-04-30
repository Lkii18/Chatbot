package GoogleMap;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;


public class GetNearbyPlaces extends AsyncTask<Object, String, String>
{
    private String googleplaceData, url;
    private GoogleMap mMap;
    private String searchByName="";
    public Context context;
    private double latitude=0,longitude=0;
    private List<HashMap<String, String>> nearByPlacesList = null;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getSearchByName() {
        return searchByName;
    }

    public void setSearchByName(String searchByName) {
        this.searchByName = searchByName;
    }

    @Override
    protected String doInBackground(Object... objects)
    {
        DataParser dataParser = new DataParser();
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];
        DownloadUrl downloadUrl = new DownloadUrl();
        if(searchByName!=""){
            dataParser.setSearchingName(this.searchByName);
            JSONObject jsonObject = null;
            JSONArray jsonArray = null;
            try {
                jsonObject = new JSONObject(readCoordinates());
                jsonArray = jsonObject.getJSONArray("regions");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for(int i=1; i<jsonArray.length();i++){

                try {
                    googleplaceData = downloadUrl.ReadTheURL(url);
                    nearByPlacesList = dataParser.parse(googleplaceData);
                }
                catch (IOException e) {
                e.printStackTrace();
                }try {

                    latitude =Double.parseDouble(jsonArray.getJSONObject(i).getString("latitude"));
                    longitude=Double.parseDouble(jsonArray.getJSONObject(i).getString("longitude"));
                    System.out.println("Region"+i);
                    url = getUrl(latitude, longitude);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            try
            {
                googleplaceData = downloadUrl.ReadTheURL(url);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return googleplaceData;
    }

    @Override
    protected void onPostExecute(String s)
    {
        DataParser dataParser = new DataParser();

        if(searchByName!=""){

            if(nearByPlacesList.size()<1)
                Toast.makeText(context,"NOT FOUND",Toast.LENGTH_SHORT).show();
            else
                DisplayNearbyPlaces(nearByPlacesList);
        }
        else if (searchByName.equals("")){
            nearByPlacesList = dataParser.parse(s);
            DisplayNearbyPlaces(nearByPlacesList);
        }
    }

    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearByPlacesList)
    {
        for (int i=0; i<nearByPlacesList.size(); i++)
        {
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
            String nameOfPlace = googleNearbyPlace.get("place_name");
            String vicinity = googleNearbyPlace.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlace.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlace + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
    private String readCoordinates(){
        String jsonString=null;
        try {
            AssetManager asset= context.getAssets();
            InputStream is = asset.open("Coordinates.json");
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
    private String getUrl(double latitide, double longitude) {
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitide + "," + longitude);
        googleURL.append("&radius=10000");
        googleURL.append("&type=restaurants,tourist_attraction");
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyAUJFFocZMuMZvjS5-tXBDDhKXATeJAXA0");

        Log.d("GoogleMapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }
}