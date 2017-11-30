package androidchatapp.com.oeewear2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StationSelectionActivity extends Activity {
    private static ArrayList<Integer> mIcons;
    private TextView mHeader;
    private TextView mTextView;
    private String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_selection);

        getAllStationsData();
        // Sample icons for the list
        mIcons = new ArrayList<Integer>();
        mIcons.add(R.mipmap.ic_launcher);
        mIcons.add(R.mipmap.ic_launcher);
        mIcons.add(R.mipmap.ic_launcher);
        mIcons.add(R.mipmap.ic_launcher);
        mIcons.add(R.mipmap.ic_launcher);
        mIcons.add(R.mipmap.ic_launcher);

        // This is our list header
        mHeader = (TextView) findViewById(R.id.header);

        WearableListView wearableListView =
                (WearableListView) findViewById(R.id.wearable_List);
        wearableListView.setAdapter(new WearableAdapter(this, mIcons));
        wearableListView.setClickListener(mClickListener);
        wearableListView.addOnScrollListener(mOnScrollListener);
    }

    // Handle our Wearable List's click events
    private WearableListView.ClickListener mClickListener =
            new WearableListView.ClickListener() {
                @Override
                public void onClick(WearableListView.ViewHolder viewHolder) {
                    Toast.makeText(StationSelectionActivity.this,
                            String.format("You selected item #%s",
                                    viewHolder.getLayoutPosition()+1),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTopEmptyRegionClick() {
                    Toast.makeText(StationSelectionActivity.this,
                            "Top empty area tapped", Toast.LENGTH_SHORT).show();
                }
            };

    // The following code ensures that the title scrolls as the user scrolls up
    // or down the list
    private WearableListView.OnScrollListener mOnScrollListener =
            new WearableListView.OnScrollListener() {
                @Override
                public void onAbsoluteScrollChange(int i) {
                    // Only scroll the title up from its original base position
                    // and not down.
                    if (i > 0) {
                        mHeader.setY(-i);
                    }
                }

                @Override
                public void onScroll(int i) {
                    // Placeholder
                }

                @Override
                public void onScrollStateChanged(int i) {
                    // Placeholder
                }

                @Override
                public void onCentralPositionChanged(int i) {
                    // Placeholder
                }
            };

    public void getAllStationsData(){
        final String URL_GET_ALL_STATIONS="http://localhost:3000/getStations";
        class GetStationsJSON extends AsyncTask<Void,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(URL_GET_ALL_STATIONS);

                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                getAllStations();

            }
        }
        GetStationsJSON gj = new GetStationsJSON();
        gj.execute();
    }

    private void getAllStations(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {

            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("data");

            Toast.makeText(StationSelectionActivity.this,
                    String.format("station",
                            result),
                    Toast.LENGTH_SHORT).show();

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                try {
                    String stationName=jo.getString("name");
                }
                catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
