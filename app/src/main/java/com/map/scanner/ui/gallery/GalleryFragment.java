package com.map.scanner.ui.gallery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.map.scanner.R;
import com.map.scanner.databinding.FragmentGalleryBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class GalleryFragment extends Fragment {

    private CodeScanner mCodeScanner;
    private TextView iid;
    RequestQueue requestQueue;
    String addOcup ="http://192.168.43.186:3000/api/occupations";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        TextView idd =  root.findViewById(R.id.textView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(activity, result.getText(), Toast.LENGTH_SHORT).show();
                         idd.setText(result.getText());
                        try {
                            envoiDonner(result.getText(),"61d48905c62d3453b049fdf0");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
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
                Toast.makeText(getActivity().getApplicationContext(), "Occupation succeed", Toast.LENGTH_SHORT).show();

                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        requestQueue.add(req);
    }
}