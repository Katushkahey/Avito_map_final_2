package com.example.myapplication.utils;

import android.content.Context;

import com.example.myapplication.Pins;
import com.example.myapplication.R;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private List<JSONObject> totalList = new ArrayList<>();
    private List<JSONObject> resultList;
    private List<String> services = new ArrayList<>();
    private JSONArray jsonArray;

    public List<String> parsingToServices(Context context) {
        try {
            String jsonText = readText(context, R.raw.pins);

//            Moshi moshi = new Moshi.Builder().build();
//            JsonAdapter<Pins> jsonAdapter = moshi.adapter(Pins.class);
//            Pins pins = jsonAdapter.fromJson(jsonText);
//            System.out.println(pins);

            JSONObject jsonRoot = new JSONObject(jsonText);
            JSONArray jsonArr = jsonRoot.getJSONArray("services");
            for (int i = 0; i < jsonArr.length(); i++) {
                services.add(jsonArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    public void parsingToList(Context context) {
        try {
            String jsonText = readText(context, R.raw.pins);
            JSONObject jsonRoot = new JSONObject(jsonText);
            jsonArray = jsonRoot.getJSONArray("pins");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectInfo = jsonArray.getJSONObject(i);
                JSONObject coordinates = objectInfo.getJSONObject("coordinates");
                for (int j = 0; j < services.size(); j++) {
                    totalList.add(coordinates);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String readText(Context context, int resId) throws IOException {
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while ((s = br.readLine()) != null) {
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }

    public double[] getLatTotal() {
        double[] latTotal = new double[totalList.size()];
        for (int i = 0; i < totalList.size(); i++) {
            latTotal[i] = getLat(totalList.get(i));
        }
        return latTotal;
    }

    public double[] getLngTotal() {
        double[] lngTotal = new double[totalList.size()];
        for (int i = 0; i < totalList.size(); i++) {
            lngTotal[i] = getLng(totalList.get(i));
        }
        return lngTotal;
    }

    private List<JSONObject> getResultList(String name) {
        resultList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject objectInfo = jsonArray.getJSONObject(i);
                JSONObject coordinates = objectInfo.getJSONObject("coordinates");
                if (objectInfo.getString("service").equals(name)) {
                    resultList.add(coordinates);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public double[] getLatResult(String name) {
        resultList = getResultList(name);
        double[] latResult = new double[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            latResult[i] = getLat(resultList.get(i));
        }
        return latResult;
    }

    public double[] getLngResult() {
        double[] lngResult = new double[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            lngResult[i] = getLng(resultList.get(i));
        }
        return lngResult;
    }

    private double getLat(JSONObject obj) {
        double lat = 0;
        try {
            lat = obj.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lat;
    }

    private double getLng(JSONObject obj) {
        double lng = 0;
        try {
            lng = obj.getDouble("lng");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lng;
    }
}
