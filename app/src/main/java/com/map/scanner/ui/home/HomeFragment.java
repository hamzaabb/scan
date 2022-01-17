package com.map.scanner.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.map.scanner.Capture;
import com.map.scanner.MainActivity;
import com.map.scanner.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HomeFragment extends Fragment {


    Button btScan;
    RequestQueue requestQueue;
    String addOcup ="http://192.168.0.219:3000/occupation/";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());




        return view;



    }
    private void envoiDonner(String idSalle,String idCreneau) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("salle", idSalle);
        json.put("creneau", idCreneau);
        Log.d("rrrr", "envoiDonner: "+json);
        JsonObjectRequest req = new JsonObjectRequest(addOcup, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Occupation est bien Ajouter", Toast.LENGTH_SHORT).show();

                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        requestQueue.add(req);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("gggg", "onActivityResult: ");
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}