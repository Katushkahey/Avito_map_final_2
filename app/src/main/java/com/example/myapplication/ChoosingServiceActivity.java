package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ChoosingServiceActivity extends AppCompatActivity {

    private Button ok;
    private Button cancel;
    private final JsonUtils jsonUtils = new JsonUtils();
    private List<String> services;
    private List<String> resultServices;
    private ServiceAdapter adapter;
    private List<Service> listOfServices;
    private List<Service> resultListOfService;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_service);
        final RecyclerView recyclerView = findViewById(R.id.activity_choosing_service__rr);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        resultListOfService = new ArrayList<>();
        resultServices = new ArrayList<>();
        services = jsonUtils.parsingToServices(getApplicationContext());
        jsonUtils.parsingToList(getApplicationContext());
        adapter = new ServiceAdapter(getListOfServices(services), new ServiceAdapter.Listener() {
            @Override
            public void onServiceClick(Service service) {
                if (!service.checked) {
                    service.setChecked(true);
                    resultServices.add(service.getName());
                    adapter.notifyDataSetChanged();
                } else {
                    service.setChecked(false);
                    resultServices.remove(service.getName());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        recyclerView.setAdapter(adapter);

        ok = findViewById(R.id.activity_choosing_service__ok);
        cancel = findViewById(R.id.activity_choosing_service__cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                for (int i = 0; i < resultServices.size(); i++) {
                    Service serviceNew = new Service(resultServices.get(i));
                    serviceNew.setLat(jsonUtils.getLatResult(resultServices.get(i)));
                    serviceNew.setLng(jsonUtils.getLngResult());
                    resultListOfService.add(serviceNew);
                }
                intent.putParcelableArrayListExtra("resultServices", (ArrayList<? extends Parcelable>) resultListOfService);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    public List<Service> getListOfServices(List<String> names) {
        listOfServices = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            listOfServices.add(new Service(names.get(i)));
        }
        return listOfServices;
    }

}