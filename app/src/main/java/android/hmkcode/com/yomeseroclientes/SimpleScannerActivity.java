package android.hmkcode.com.yomeseroclientes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    String text;
    String [] res;
    Context context;
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
        context = this;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        text = rawResult.getText();
        res = text.split(" ");
        if(res.length==4){
            Intent intent= new Intent(this,DisplayMenuActivity.class);
            intent.putExtra("Resultado", res);
            startActivity(intent);
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(SimpleScannerActivity.this).create();
            alertDialog.setTitle("Código QR inválido");
            alertDialog.setMessage("El código QR escaneado es inválido, vuelva a escanear el codigo QR");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent intent = new Intent(context,MainActivity.class);
                            startActivity(intent);
                        }
                    });
            alertDialog.show();
        }
        Log.d("Result_text_gumon", rawResult.getText()); // Prints scan results
        Log.d("Format_gumon", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
    }
}

