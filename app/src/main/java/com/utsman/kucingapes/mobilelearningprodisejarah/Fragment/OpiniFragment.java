package com.utsman.kucingapes.mobilelearningprodisejarah.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.utsman.kucingapes.mobilelearningprodisejarah.AdapterContentList;
import com.utsman.kucingapes.mobilelearningprodisejarah.Content.ModelContentList;
import com.utsman.kucingapes.mobilelearningprodisejarah.R;
import com.utsman.kucingapes.mobilelearningprodisejarah.RcConfig.MarginDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpiniFragment extends Fragment {
    private List<ModelContentList> lists = new ArrayList<>();
    private AdapterContentList adapterContentList;

    public OpiniFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_opini, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.opini_list);
        adapterContentList = new AdapterContentList(lists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MarginDecoration(15, MarginDecoration.VERTICAL));
        recyclerView.setAdapter(adapterContentList);
        RecyclerView.ViewHolder p = recyclerView.findViewHolderForAdapterPosition(getId());
        int e = p.getAdapterPosition();
        //Context v = recyclerView.findViewHolderForAdapterPosition(e).itemView.getContext();
        TextView textView = ((TextView) recyclerView.findViewHolderForAdapterPosition(e).itemView.findViewById(R.id.body_sub));
        textView.setVisibility(View.GONE);
        preData();

        return view;
    }

    private void preData() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("opini");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String title = ds.child("title").getValue(String.class);
                    String imgUrl = ds.child("img").getValue(String.class);
                    String cat = ds.child("author").getValue(String.class);
                    String body = ds.child("body").getValue(String.class);
                    addData(title, imgUrl, cat, body);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addData(String title, String imgUrl, String cat, String body) {
        ModelContentList contentList = new ModelContentList(title, imgUrl, cat, body);
        lists.add(contentList);
        adapterContentList.notifyDataSetChanged();
    }

}
