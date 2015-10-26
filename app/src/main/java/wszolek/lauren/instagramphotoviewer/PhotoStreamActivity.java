package wszolek.lauren.instagramphotoviewer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotoStreamActivity extends AppCompatActivity {

    public static final String CLIENT_ID = "05981384d8514fcb91f0481ac7a56fae";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter aPhotos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_stream);
        // send out api request to instagram to get the popular photos
        photos = new ArrayList<>();

        //create the adapter linking it to the source
        aPhotos = new InstagramPhotosAdapter(this, photos);
        // find the listview from the layout
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        // set the adapter binding it to the list view
        lvPhotos.setAdapter(aPhotos);
        // fetch the popular photos
        fetchPopularPhotos();
    }

    //trigger api request
    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id=" + CLIENT_ID;
        //create the network client
        AsyncHttpClient client = new AsyncHttpClient();
        // trigger get request
        client.get(url, new JsonHttpResponseHandler() {

            // onSuccess
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //iterate through each item and decode the item into a java object
                JSONArray photosJSON;
                try {
                    photosJSON = response.getJSONArray("data"); // array of posts
                    //iterate array of posts
                    for (int i = 0; i < photosJSON.length(); i++){
                        // get the JSON object at position i in the array
                        JSONObject photoJSON = photosJSON.getJSONObject(i);
                        // decode the attributes of the json into a data model
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.username = photoJSON.getJSONObject("user").getString("username");
                        photo.userProfilePictureUrl = photoJSON.getJSONObject("user").getString("profile_picture");
                        photo.caption = photoJSON.getJSONObject("caption").getString("text");
                        photo.createdAt = Long.parseLong(photoJSON.getJSONObject("caption").getString("created_time"));
                        photo.type = photoJSON.getString("type");
                        photo.imageUrl = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getString("url");
                        photo.imageHeight = photoJSON.getJSONObject("images").getJSONObject("standard_resolution").getInt("height");
                        photo.likesCount = photoJSON.getJSONObject("likes").getInt("count");
                        // add decoded object to the photos array
                        photos.add(photo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // success callback
                aPhotos.notifyDataSetChanged();
            }

             // onFailure

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo_stream, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
