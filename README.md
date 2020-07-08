# Pingu

Simple, Easy Restful-API service calling.

To get a Git project into your build:

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

Step 2. Add the dependency

	dependencies {
        implementation 'com.github.mohd-nabil:Pingu:2.1.1'
	}

--Sample to call multiple different types of service--

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
