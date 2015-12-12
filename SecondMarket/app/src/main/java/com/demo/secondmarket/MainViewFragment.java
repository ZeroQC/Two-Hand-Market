package com.demo.secondmarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import com.markmao.pulltorefresh.widget.XListView;

import adapter.ListTopicAdapter;
import sql.Service;

/**
 * Created by Administrator on 2015/11/16.
 */
public class MainViewFragment extends Fragment  {


    private Context context;
    private XListView listTopic;
    private ListTopicAdapter topicData;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        context = this.getActivity().getApplicationContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_mainview,container,false);
        FindView(retView);

        Service service=new Service(getActivity());
        topicData = new ListTopicAdapter(getActivity(),service.getData(0,10));
        listTopic.setAdapter(topicData);
        listTopic.setOnScrollListener(new XListView.OnXScrollListener() {
            @Override
            public void onXScrolling(View view) {
                Service service = new Service(getActivity());
                topicData = new ListTopicAdapter(getActivity(), service.getData(0, 10));
                listTopic.setAdapter(topicData);
                listTopic.stopRefresh();
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, parent.getItemIdAtPosition(position) + "", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent();
                intent.setClass(getActivity(),ActivityGoodsDetails.class);
                getActivity().startActivity(intent);
            }
        });

        return retView;
    }





    private void FindView(View view){

        listTopic = (XListView)view.findViewById(R.id.listView);
    }







}
