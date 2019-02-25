package com.example.realtimelocation;

import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class MapTraking extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String email;
    DatabaseReference location;
    double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_traking);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        location= FirebaseDatabase.getInstance().getReference("location");
        if (getIntent() != null){
            email=getIntent().getStringExtra("email");
            lat=getIntent().getDoubleExtra("lat",0);
            lng=getIntent().getDoubleExtra("lng",0);
        }
        if (!TextUtils.isEmpty(email)){
            locationForThisUser(email);
        }

    }

    private void locationForThisUser(String email) {
        Query user_location=location.orderByChild("email").equalTo(email);
        user_location.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot:dataSnapshot.getChildren()){
                    Traking traking=postSnapshot.getValue(Traking.class);

                    LatLng friendLocation=new LatLng(Double.parseDouble(traking.getLat()),
                            Double.parseDouble(traking.getLang()));
                    Location currentUser=new Location("");
                    currentUser.setLatitude(lat);
                    currentUser.setLongitude(lng);

                    Location friend=new Location("");
                    friend.setLatitude(Double.parseDouble(traking.getLat()));
                    friend.setLongitude(Double.parseDouble(traking.getLang()));



                    mMap.addMarker(new MarkerOptions()
                                   .position(friendLocation)
                                    .title(traking.getEmail())
                                     .snippet("Distance"+new DecimalFormat("#.#").format(distance(currentUser,friend)))
                                     .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),12.0f));


                }
                LatLng current=new LatLng(lat,lng);
                mMap.addMarker(new MarkerOptions()
                               .position(current)
                                .title(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double distance(Location currentUser, Location friend) {
        Double theta=currentUser.getLongitude()-friend.getLongitude();
        Double dis=(Math.sin(deg2rad(currentUser.getLatitude())
                    *Math.sin(deg2rad(friend.getLatitude())) 
                    *Math.cos(deg2rad(currentUser.getLatitude()))
                    *Math.cos(deg2rad(friend.getLatitude()))
                    *Math.cos(deg2rad(theta))));
        dis=Math.acos(dis);
        dis=rad2deg(dis);
        dis=dis*60*1.1515;
        return (dis);

    }

    private Double rad2deg(Double rad) {
        return (rad*180/Math.PI);
    }

    private double deg2rad(double deg) {
        return (deg*Math.PI/180.0);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


    }
}
