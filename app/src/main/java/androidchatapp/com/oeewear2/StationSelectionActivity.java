package androidchatapp.com.oeewear2;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.support.wearable.view.WearableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StationSelectionActivity extends Activity {
    private static ArrayList<Integer> mIcons;
    private TextView mHeader;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_station_selection);

        // Sample icons for the list
        mIcons = new ArrayList<Integer>();
        mIcons.add(R.drawable.ic_action_home);
        mIcons.add(R.drawable.ic_action_home);
        mIcons.add(R.drawable.ic_action_home);
        mIcons.add(R.drawable.ic_action_home);
        mIcons.add(R.drawable.ic_action_home);
        mIcons.add(R.drawable.ic_action_home);

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
}
