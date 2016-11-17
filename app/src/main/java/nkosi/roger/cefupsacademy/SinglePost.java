package nkosi.roger.cefupsacademy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class SinglePost extends AppCompatActivity {

    private static TextView name, body, time;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single_post);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (TextView)findViewById(R.id.txtname);
        body = (TextView)findViewById(R.id.txtBody);
        time = (TextView)findViewById(R.id.txttime);

        // get the intent that we have passed from ActivityOne
        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        new LoadPost().execute(id);

    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(this, Home.class));

        super.onBackPressed();
    }

    private class LoadPost extends AsyncTask<String, String, JSONObject> {

        private JSONParser jsonParser = new JSONParser();
        private ProgressDialog progressDialog;

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(SinglePost.this);
            progressDialog.setMessage("Requesting...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param jsonObject The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            if (progressDialog.isShowing()){
                progressDialog.dismiss();
            }

            try {
                if (jsonObject != null){
                    String body = jsonObject.getString("post");
                    String fname = jsonObject.getString("firstName");
                    String lname = jsonObject.getString("lastName");
                    String dateAdded = jsonObject.getString("dateAdded");
                    SinglePost.body.setText(body);
                    SinglePost.name.setText(fname + " " + lname);
                    SinglePost.time.setText(dateAdded);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }

        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected JSONObject doInBackground(String... params) {

            try {
                HashMap<String, String> map = new HashMap<>();
                map.put("method", "getPost");
                map.put("postID", params[0]);

                JSONObject jsonObject = jsonParser.makeHttpRequest(Constants.URL, "POST", map);

                if (jsonObject != null){
                    Log.e("JSON result", jsonObject.toString());
                    return jsonObject;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

}
