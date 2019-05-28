package com.example.selfiehelper.presenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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


public class GroupCreationDialog extends DialogFragment {


    public interface GroupCreationAcceptButtonListener {
        void onAcceptButtonListener(String groupName);
    }

    private GroupCreationAcceptButtonListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View dialogContent = layoutInflater.inflate(R.layout.creation_group_dialog, null);

        final EditText tfGroupName = (EditText) dialogContent.findViewById(R.id.editTextGroupName);

        builder.setView(dialogContent);
        builder.setPositiveButton(getResources().getString(R.string.btn_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = tfGroupName.getText().toString();
                if (listener != null) {
                    listener.onAcceptButtonListener(groupName);
                }
            }
        });

        builder.setNegativeButton(getResources().getString(R.string.btn_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GroupCreationDialog.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    public void addGroupCreationAcceptListener(GroupCreationAcceptButtonListener listener) {
        this.listener = listener;
    }

}

