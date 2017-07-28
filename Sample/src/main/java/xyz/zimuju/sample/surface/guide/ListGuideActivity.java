package xyz.zimuju.sample.surface.guide;

import android.app.Activity;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.guide.GuideAdapter;

public class ListGuideActivity extends Activity {
    List<String> arrayList = new ArrayList<>();
    ListView listView;
    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);
        for (int i = 1; i < 200; i++) {
            arrayList.add("第" + i + "行");
        }
        listView = (ListView) findViewById(R.id.guide_list_lv);
        adapter = new GuideAdapter(arrayList);
        listView.setAdapter(adapter);
    }
}
