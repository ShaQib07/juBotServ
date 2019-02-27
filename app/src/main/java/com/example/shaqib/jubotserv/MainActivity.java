package com.example.shaqib.jubotserv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shaqib.jubotserv.Model.Request;
import com.example.shaqib.jubotserv.interfacee.ItemClickListener;
import com.example.shaqib.jubotserv.viewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference request;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, MenuViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        request = database.getReference("Request");

        recyclerView = findViewById(R.id.list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();


    }

    private void loadOrder() {
        FirebaseRecyclerOptions<Request> options = new FirebaseRecyclerOptions.Builder<Request>()
                .setQuery(request, Request.class).build();

        adapter = new FirebaseRecyclerAdapter<Request, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MenuViewHolder holder, final int position, @NonNull final Request model) {
                holder.mName.setText(model.getName());
                holder.mPhone.setText(model.getPhone());
                holder.mAddress.setText(model.getAddress());
                holder.mComment.setText(model.getComment());
                holder.mDandT.setText(model.getDateAndTime());
                holder.mTotal.setText(model.getTotal());

                if (model.getStatus().equals("Done")){
                    holder.btn.setBackgroundColor(getColor(R.color.light_green));
                    holder.btn.setText(model.getStatus());
                } else {
                    holder.btn.setBackgroundColor(getColor(R.color.signup));
                    holder.btn.setText(model.getStatus());
                }

                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (model.getStatus().equals("Pending")){
                            holder.btn.setBackgroundColor(getColor(R.color.light_green));
                            holder.btn.setText("Done");
                            Request currentRequest = new Request(model.getPhone(),model.getName(),model.getAddress(),model.getComment(),model.getTotal(),model.getDateAndTime(),"Done",model.getFoods());
                            request.child(adapter.getRef(position).getKey()).setValue(currentRequest);
                        } else {
                            holder.btn.setBackgroundColor(getColor(R.color.signup));
                            holder.btn.setText("Pending");
                            Request currentRequest = new Request(model.getPhone(),model.getName(),model.getAddress(),model.getComment(),model.getTotal(),model.getDateAndTime(),"Pending",model.getFoods());
                            request.child(adapter.getRef(position).getKey()).setValue(currentRequest);
                        }
                    }
                });

                final Request clickItem = model;

                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent foodList = new Intent(MainActivity.this,FoodListActivity.class);
                        foodList.putExtra("RequestID",adapter.getRef(position).getKey());
                        startActivity(foodList);}
                });
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.order_item, viewGroup, false);
                return new MenuViewHolder(itemView);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }
}
