package com.demo.secondmarket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MainViewFragment extends Fragment {

    private ListView listGood;

    private SimpleAdapter simpleadapter;
    private List<Map<Object,String>> datalsit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.mainview,container,false);
        FindView(retView);

        datalsit = new ArrayList<>();

        simpleadapter = new SimpleAdapter(getActivity(),getData(),R.layout.listview_item,
                new String[]{"img","title","content","price"},
                new int[]{R.id.goodpic,R.id.goodtitle,R.id.goodcontent,R.id.goodprice});

        listGood.setAdapter(simpleadapter);

        return retView;
    }

    private void FindView(View view){
        listGood = (ListView)view.findViewById(R.id.listView);
    }

    public List getData() {
        for(int i=1;i<=15;i++){
            Map map=new HashMap();
            map.put("img",R.mipmap.ic_launcher);
            map.put("title","Title"+i);
            map.put("content","Content"+i);
            map.put("price","$"+(i+100));
            datalsit.add(map);
        }
        return datalsit;
    }
}
