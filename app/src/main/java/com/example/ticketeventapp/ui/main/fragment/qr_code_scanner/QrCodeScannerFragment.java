package com.example.ticketeventapp.ui.main.fragment.qr_code_scanner;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ticketeventapp.R;
import com.example.ticketeventapp.model.mng_tickets.Ticket;
import com.example.ticketeventapp.model.mng_tickets.TicketsManager;
import com.example.ticketeventapp.model.qr_code_scanner.QRCodeFoundListener;
import com.example.ticketeventapp.model.qr_code_scanner.QRCodeImageAnalyzer;
import com.example.ticketeventapp.model.utils.PermissionManager;
import com.example.ticketeventapp.viewmodel.mng_events.EventListViewModel;
import com.example.ticketeventapp.viewmodel.mng_tickets.InfoTicketViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class QrCodeScannerFragment extends Fragment {

    private Activity activity;
    private PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;

    private Button qrCodeFoundButton;
    private String qrCode;

    private PermissionManager permissionManager;
    private TicketsManager ticketsManager;
    private InfoTicketViewModel infoTicketViewModel;
    private EventListViewModel eventListViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.qr_code_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();
        infoTicketViewModel = new ViewModelProvider(getActivity()).get(InfoTicketViewModel.class);
        eventListViewModel = new ViewModelProvider(getActivity()).get(EventListViewModel.class);
        ticketsManager = new TicketsManager();
        permissionManager = new PermissionManager(activity,this);
        cameraProviderFuture = ProcessCameraProvider.getInstance(activity);
        previewView = activity.findViewById(R.id.activity_main_previewView);

        qrCodeFoundButton = activity.findViewById(R.id.activity_main_qrCodeFoundButton);
        qrCodeFoundButton.setVisibility(View.INVISIBLE);
        qrCodeFoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, qrCode, Toast.LENGTH_SHORT).show();
                //Log.i(MainActivity.class.getSimpleName(), "QR Code Found: " + qrCode);
            }
        });

        if(permissionManager.isPermissionCameraAllowed()){
            startCamera();
        } else {
            permissionManager.launchPermissionRequestCamera();
            getFragmentManager().popBackStack();
        }

        infoTicketViewModel.getTicketsLiveData().observe(getActivity(), new Observer<List<Ticket>>() {
            @Override
            public void onChanged(List<Ticket> tickets) {
                ticketsManager.setTicketList(tickets);
            }
        });

    }


    /*private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
            }
        }
    }*/

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindCameraPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(activity, "Error starting camera " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(activity));
    }


    private void bindCameraPreview(@NonNull ProcessCameraProvider cameraProvider) {
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);

        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        preview.setSurfaceProvider(previewView.createSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(activity), new QRCodeImageAnalyzer(new QRCodeFoundListener() {
            @Override
            public void onQRCodeFound(String _qrCode) {
                qrCode = _qrCode;
                Log.e("QrCode",_qrCode);
                //qrCodeFoundButton.setVisibility(View.VISIBLE);


                //Log.e("QrCode","Selected ticket event id: "+infoTicketViewModel.getSelectedTicket().getValue().getId_event());
                int res = ticketsManager.isValidTicket(_qrCode, eventListViewModel.getSelectedEventItem().getValue().getId());
                if(res == 1){
                    Log.e("QrCode","Ticket valido");
                } else if(res == 0){
                    Log.e("QrCode","Ticket non valido");
                } else if(res == 2){
                    Log.e("QrCode","Ticket già usatp");
                }
            }

            @Override
            public void qrCodeNotFound() {
                qrCodeFoundButton.setVisibility(View.INVISIBLE);
            }
        }));

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }
}
