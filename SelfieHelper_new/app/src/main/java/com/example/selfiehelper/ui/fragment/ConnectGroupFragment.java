package com.example.selfiehelper.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import com.abemart.wroup.client.WroupClient;
import com.abemart.wroup.common.WiFiDirectBroadcastReceiver;
import com.abemart.wroup.common.WiFiP2PError;
import com.abemart.wroup.common.WiFiP2PInstance;
import com.abemart.wroup.common.WroupDevice;
import com.abemart.wroup.common.WroupServiceDevice;
import com.abemart.wroup.common.listeners.ServiceConnectedListener;
import com.abemart.wroup.common.listeners.ServiceDiscoveredListener;
import com.abemart.wroup.common.listeners.ServiceRegisteredListener;
import com.abemart.wroup.service.WroupService;

import net.grandcentrix.thirtyinch.TiFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.selfiehelper.R;

import java.util.ArrayList;
import java.util.List;

public class ConnectGroupFragment extends AppCompatActivity  {


    private static final String TAG = ConnectGroupFragment.class.getSimpleName();

    private WiFiDirectBroadcastReceiver wiFiDirectBroadcastReceiver;
    private WroupService wroupService;
    private WroupClient wroupClient;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_Connect_Group, container, false);
        ButterKnife.bind(this, v);

        wiFiDirectBroadcastReceiver = WiFiP2PInstance.getInstance(this).getBroadcastReceiver();


        Button btnJoinGroup = (Button) findViewById(R.id.btn_joinGroup);



        btnJoinGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAvailableGroups();
            }
        });
        return v;
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        registerReceiver(wiFiDirectBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wiFiDirectBroadcastReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (wroupService != null) {
            wroupService.disconnect();
        }

        if (wroupClient != null) {
            wroupClient.disconnect();
        }
    }



    private void searchAvailableGroups() {
        final ProgressDialog progressDialog = new ProgressDialog(ConnectGroupFragment.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.prgrss_connecting_to_group));
        progressDialog.show();

        wroupClient = WroupClient.getInstance(getApplicationContext());
        wroupClient.discoverServices(5000L, new ServiceDiscoveredListener() {

            @Override
            public void onNewServiceDeviceDiscovered(WroupServiceDevice serviceDevice) {
                Log.i(TAG, "New group found:");
                Log.i(TAG, "\tName: " + serviceDevice.getTxtRecordMap().get(WroupService.SERVICE_GROUP_NAME));
            }

            @Override
            public void onFinishServiceDeviceDiscovered(List<WroupServiceDevice> serviceDevices) {
                Log.i(TAG, "Found '" + serviceDevices.size() + "' groups");
                progressDialog.dismiss();

                if (serviceDevices.isEmpty()) {
                    Toast.makeText(getApplicationContext(), getString(R.string.toast_not_found_groups),Toast.LENGTH_LONG).show();
                } else {
                    showPickGroupDialog(serviceDevices);
                }
            }

            @Override
            public void onError(WiFiP2PError wiFiP2PError) {
                Toast.makeText(getApplicationContext(), "Error searching groups: " + wiFiP2PError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showPickGroupDialog(final List<WroupServiceDevice> devices) {
        List<String> deviceNames = new ArrayList<>();
        for (WroupServiceDevice device : devices) {
            deviceNames.add(device.getTxtRecordMap().get(WroupService.SERVICE_GROUP_NAME));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select a group");
        builder.setItems(deviceNames.toArray(new String[deviceNames.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final WroupServiceDevice serviceSelected = devices.get(which);
                final ProgressDialog progressDialog = new ProgressDialog(ConnectGroupFragment.this);
                progressDialog.setMessage(getString(R.string.prgrss_connecting_to_group));
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                wroupClient.connectToService(serviceSelected, new ServiceConnectedListener() {
                    @Override
                    public void onServiceConnected(WroupDevice serviceDevice) {
                        progressDialog.dismiss();
                        startGroupChatActivity(serviceSelected.getTxtRecordMap().get(WroupService.SERVICE_GROUP_NAME), false);
                    }
                });
            }
        });

        AlertDialog pickGroupDialog = builder.create();
        pickGroupDialog.show();
    }


    private void startGroupChatActivity(String groupName, boolean isGroupOwner) {
        Intent intent = new Intent(getApplicationContext(), GroupChatActivity.class);
        intent.putExtra(GroupChatActivity.EXTRA_GROUP_NAME, groupName);
        intent.putExtra(GroupChatActivity.EXTRA_IS_GROUP_OWNER, isGroupOwner);
        startActivity(intent);
    }


}

