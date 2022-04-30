package GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



public class DataParser
{
    public String SearchingName="";
    public List<HashMap<String, String>> NearbyPlacesList = new ArrayList<>();

    public String getSearchingName() {
        return SearchingName;
    }

    public void setSearchingName(String searchingName) {
        SearchingName = searchingName;
    }


    private HashMap<String, String> getSingleNearbyPlace(JSONObject googlePlaceJSON)
    {
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try
        {
            if (!googlePlaceJSON.isNull("name"))
            {
                NameOfPlace = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity"))
            {
                vicinity = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", NameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return googlePlaceMap;
    }



    private List<HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter = jsonArray.length();

        List<HashMap<String, String>> NearbyPlacesList = new ArrayList<>();

        HashMap<String, String> NearbyPlaceMap = null;

        for (int i=0; i<counter; i++)
        {
            try
            {
                NearbyPlaceMap = getSingleNearbyPlace( (JSONObject) jsonArray.get(i) );
                NearbyPlacesList.add(NearbyPlaceMap);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

        return NearbyPlacesList;
    }
    private List<HashMap<String, String>> compareAllNearbyPlaces(JSONArray jsonArray)
    {
        int counter = jsonArray.length();



        HashMap<String, String> NearbyPlaceMap = null;
        JSONObject temp;
        for (int i=0; i<counter; i++)
        {
            try
            {
                temp=(JSONObject) jsonArray.get(i);
                System.out.println("Searching:"+this.SearchingName+"  Comparing: "+temp.getString("name"));
                if(compareKeyword(temp.getString("name")))
                {
                    NearbyPlaceMap = getSingleNearbyPlace(temp);
                    System.out.println("temp: "+ temp);
                    NearbyPlacesList.add(NearbyPlaceMap);
                    System.out.println("Size1: "+NearbyPlacesList.size());
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        System.out.println("Size2: "+NearbyPlacesList.size());
        return NearbyPlacesList;
    }


    public List<HashMap<String, String>> parse(String jSONdata)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try
        {
            jsonObject = new JSONObject(jSONdata);
            jsonArray = jsonObject.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        if(this.SearchingName!="")
            return compareAllNearbyPlaces(jsonArray);

        return getAllNearbyPlaces(jsonArray);
    }
    public boolean compareKeyword(String keyword)
    {
        if(keyword.indexOf(SearchingName)!=-1)
            return true;
        else
            return false;
    }
}
