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


    private int goodIconId[]={
            R.drawable.p1,R.drawable.p2,R.drawable.p3,
            R.drawable.p4,R.drawable.p5,R.drawable.p6,R.drawable.p7,
            R.drawable.p8,R.drawable.p9,R.drawable.p10,R.drawable.p11,
            R.drawable.p12,R.drawable.p13,R.drawable.p14,R.drawable.p15
    };
    private String goodTitle[]={"小黄人","小黄人+1","吉格斯","马尔扎哈","韦鲁斯","熊",
            "熊+1","猫","狮子","小明","地球"};
    private String goodContent[]={"......","......","......","......",
            "......","......","......","......","......","......"};
    private String goodPrice[]={};


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
        for(int i=0;i<=9;i++){
            Map map=new HashMap();
            map.put("img",goodIconId[i]);
            map.put("title",goodTitle[i]);
            map.put("content",goodContent[i]);
            map.put("price","$"+(i+100));
            datalsit.add(map);
        }
        return datalsit;
    }
}
