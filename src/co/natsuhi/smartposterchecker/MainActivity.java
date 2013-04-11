package co.natsuhi.smartposterchecker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.util.Log;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";
    private String mUri;

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {

            String data = intent.getDataString();
            if (data == null) {
                finish();
            }
            Log.d(TAG, data);
            mUri = data;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getTitle());
            builder.setMessage(getString(R.string.check_uri_dialog_message,
                    data));
            builder.setNegativeButton(android.R.string.cancel,
                    new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
            builder.setPositiveButton(android.R.string.ok,
                    new OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Uri uri = Uri.parse(mUri);
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                            finish();
                        }
                    });
            builder.setOnCancelListener(new OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    finish();
                }
            });
            builder.show();
        } else {
            finish();
        }
    }
}
