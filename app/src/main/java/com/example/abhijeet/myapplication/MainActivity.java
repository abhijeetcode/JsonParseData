package com.example.abhijeet.myapplication;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    TextView tv,tvname,tvprice;
    ImageView imgvw;
    DataAdapter dAdapter;
    RecyclerView recyclerView;
    private List<Data> Datalist ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Datalist = new ArrayList<>();
        tv = (TextView) findViewById(R.id.text);
        imgvw=(ImageView)findViewById(R.id.imageView);
        tvname = (TextView) findViewById(R.id.name);
        tvprice = (TextView) findViewById(R.id.price);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        dAdapter=new DataAdapter(Datalist);
        Ion.with(this)
                .load("http://deliverit.co.in/test.php?tablename=shakestable")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result)
                    {
                        try {
                            Dataload(result);
                            Log.v("ion cool", result);
                        }
                        catch (Exception t) {
                            //Log.v("error ion",t.toString());

                        }
                    }
                });
        Recycler();


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(), new GestureDetector.SimpleOnGestureListener() {

                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if(child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    displayDetails(position,Datalist.get(position).getItem(),Datalist.get(position).getImage(),Datalist.get(position).getprice());
                    //Toast.makeText(getApplicationContext(), Datalist.get(position).getItem(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }
            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });













        ConnectivityManager connec =(ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);




        if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED )
        {
            Toast.makeText(MainActivity.this, " Connected ", Toast.LENGTH_LONG).show();


        }
        else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            Toast.makeText(MainActivity.this, " Not Connected ", Toast.LENGTH_LONG).show();
            imgvw.setImageResource(R.drawable.nonetwork);
        }

        /*if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            Toast.makeText(MainActivity.this, " Not Connected ", Toast.LENGTH_LONG).show();
            imgvw.setImageResource(R.drawable.nonetwork);
        }
        else if (
                connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  )
        {
            Toast.makeText(MainActivity.this, " Not Connected ", Toast.LENGTH_LONG).show();
            // p_imageView.setImageResource(R.drawable.nonetwork);
        }*/






    }


    void Dataload(String result)
    {
        try
        {
            String pro_name,pro_image,pro_price;
            JSONArray jsonArray;
            JSONObject jsonObject1;
            JSONObject jsonObject = new JSONObject(result);
            Log.v("dataload","pikapika "+jsonObject);
            jsonArray = jsonObject.getJSONArray("products");
            for (int i = 0; i < jsonArray.length(); i++)
            {
                jsonObject1 = jsonArray.getJSONObject(i);
                pro_name = jsonObject1.getString("name");
                pro_image = jsonObject1.getString("image");
                pro_price = jsonObject1.getString("price");

                Datalist.add(new Data(pro_name, pro_image, pro_price));
            }
        }
        catch (Exception e)
        {
        Log.v("dataload",e.toString());
        }

         try
         {
              dAdapter.notifyDataSetChanged();
             Recycler();
         }
        catch(Exception e1)
        {
        }
    }
    public void displayDetails(int i,String item,String imageurl,String price)
    {
        Intent in=new Intent(MainActivity.this,Main2Activity.class);
        in.putExtra("name",item);
        in.putExtra("image",imageurl);
        in.putExtra("price",price);
        startActivity(in);
    }
    public void Recycler()

    {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dAdapter);

    }


}
