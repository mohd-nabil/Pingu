package com.mjm.webservices.sample;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mjm.webservices.Methods;
import com.mjm.webservices.R;
import com.mjm.webservices.RequestCallback;
import com.mjm.webservices.Response;
import com.mjm.webservices.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class MainActivity extends AppCompatActivity{

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        Button button = new Button(this);
        button.setText("Call");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callService(v);
            }
        });
        linearLayout.addView(button);
        setContentView(linearLayout);

        mContext = this;

    }

    public void callService(View view) {

//        checkMobileConnection(URL);
//        checkWifiConnection(URL);
//        checkOtherConnection(URL);

//        URL = "http://192.168.15.223:3000/onlyUrl";
//        callOnlyURL(URL);

//        URL = "http://192.168.15.223:3000/singleKeyValue";
//        callSingleKeyValue(URL);

//        URL = "http://192.168.15.223:3000/multipleKeyValue";
//        callMultipleKeyValue(URL);

//        URL = "http://192.168.15.223:3000/withJsonBody";
//        callWithJsonBody(URL);

//        URL = "http://192.168.15.223:3000/withJsonBodyAndSingleKeyValue";
//        callWithJsonBodyAndSingleKeyValue(URL);

        String URL = "http://192.168.15.223:3000/withJsonBodyAndMultipleKeyValue";
//        callWithJsonBodyAndMultipleKeyValue(URL);

//        URL = "http://192.168.15.223:3000/withMultipart";
//        callWithMultipart(URL);


//        Service.url("").method(Methods.POST()).file(null);
//        Service.url("").method(Methods.POST()).file("", "", null);
//        Service.url("").method(Methods.POST()).file(new String[]{}, null, null);
//        Service.url("").method(Methods.POST()).file("", "", "", null, null);
//        Service.url("").method(Methods.POST()).file(new String[]{}, null, "", null, null);
//        Service.url("").method(Methods.POST()).file("", null, null);
    }

    private void callWithMultipart(String URL) {
        String file = Environment.getExternalStorageDirectory().getPath() + "/CMX";
        File logFile = new File(file, "/log/log.txt");
        File demoFile = new File(file, "/floor/temp/Demo2");
        String logPath = logFile.getPath();
        String demoPath = demoFile.getPath();

        Service.url(URL).method("POST").file(new String[]{"token", "id", "name"}, new String[]{"sdad4454", "01", "anisha"}, new String[]{"filePath", "filePath"}, new String[]{demoPath, logPath}).request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkOtherConnection(String URL) {
        Service.url(URL).method("POST").request().execute(mContext, ConnectivityManager.TYPE_BLUETOOTH, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkMobileConnection(String URL) {
        Service.url(URL).method("POST").request().execute(mContext, ConnectivityManager.TYPE_MOBILE, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkWifiConnection(String URL) {
        Service.url(URL).method("POST").request().execute(mContext, ConnectivityManager.TYPE_WIFI, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callOnlyURL(String URL) {
//        Service.url(URL).method("GET").request().execute(mContext, new RequestCallback() {
        Service.url(URL).method("POST").request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callSingleKeyValue(String URL) {
        Service.url(URL).method("GET").header("token", "12d2sd21").request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callMultipleKeyValue(String URL) {
        String[] paramKeys = {"token", "id"};
        String[] paramValues = {"454adasdsd", "1"};
        Service.url(URL).method("GET").headers(paramKeys, paramValues).request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callWithJsonBody(String URL) {
        Service.url(URL).method("POST").json("{\"name\":\"Nabil\"}").request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callWithJsonBodyAndSingleKeyValue(String URL) {
        Service.url(URL).method("PUT").header("token", "ds454s5d4s").json("{\"name\":\"Nabil\"}").request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                String header = response.getHeaders().get("anisha");
                Toast.makeText(mContext, header, Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callWithJsonBodyAndMultipleKeyValue(String URL) {
        String[] paramKeys = {"token", "id"};
        String[] paramValues = {"454adasdsd", "1"};
        Service.url(URL).method("DELETE").headers(paramKeys, paramValues).json("{\"name\":\"Nabil\"}").request().execute(mContext, new RequestCallback() {
            @Override
            public void onSuccess(Response response) {
                String header = response.getHeaders().get("anisha");
                Toast.makeText(mContext, header, Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(Response response) {
                Toast.makeText(mContext, response.getResponse(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
