package com.example.ticketeventapp.model.qr_code_scanner;

import static android.graphics.ImageFormat.YUV_420_888;
import static android.graphics.ImageFormat.YUV_422_888;
import static android.graphics.ImageFormat.YUV_444_888;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import java.nio.ByteBuffer;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class QRCodeImageAnalyzer implements ImageAnalysis.Analyzer {
    private QRCodeFoundListener listener;
    private LocalTime lastScannedTime;

    public QRCodeImageAnalyzer(QRCodeFoundListener listener) {
        this.listener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.lastScannedTime = LocalTime.now();
        } else {
            //TODO
        }
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        if (image.getFormat() == YUV_420_888 || image.getFormat() == YUV_422_888 || image.getFormat() == YUV_444_888) {
            ByteBuffer byteBuffer = image.getPlanes()[0].getBuffer();
            byte[] imageData = new byte[byteBuffer.capacity()];
            byteBuffer.get(imageData);

            PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(
              imageData,
              image.getWidth(), image.getHeight(),
              0, 0,
              image.getWidth(), image.getHeight(),
              false
            );

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                if(lastScannedTime.until(LocalTime.now(), ChronoUnit.SECONDS) > 2){ //difference from two scan
                    try {
                        Result result = new QRCodeMultiReader().decode(binaryBitmap);
                        listener.onQRCodeFound(result.getText());
                        lastScannedTime = LocalTime.now();
                    } catch (FormatException | ChecksumException | NotFoundException e) {
                        listener.qrCodeNotFound();
                    }
                }
            }
        }

        image.close();
    }
}
