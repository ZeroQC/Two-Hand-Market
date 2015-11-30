package com.demo.secondmarket;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import adapter.ListTopicAdapter;
import sql.Service;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MainViewFragment extends Fragment {

    private ListView listTopic;
    private ListTopicAdapter topicData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.mainview,container,false);
        FindView(retView);

        Service service=new Service(getActivity());
        topicData = new ListTopicAdapter(getActivity(),service.getData(0,10));
        listTopic.setAdapter(topicData);

        return retView;
    }

    private void FindView(View view){
        listTopic = (ListView)view.findViewById(R.id.listView);
    }

}
