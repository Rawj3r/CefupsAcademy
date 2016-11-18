package nkosi.roger.cefupsacademy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;

public class AssignmentsActivity extends AppCompatActivity {

    private TextView tsubject, taverage, tcass;

    private Runner runner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        tsubject = (TextView)findViewById(R.id.app_bar_subject);
        taverage = (TextView)findViewById(R.id.app_bar_assignments_average);
        tcass = (TextView)findViewById(R.id.app_bar_assignments_cass);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        runner = new Runner();
//        runner.start();


        new GetTask().execute();

    }

    class Runner extends Thread{
        /**
         * If this thread was constructed using a separate
         * <code>Runnable</code> run object, then that
         * <code>Runnable</code> object's <code>run</code> method is called;
         * otherwise, this method does nothing and returns.
         * <p>
         * Subclasses of <code>Thread</code> should override this method.
         *
         * @see #start()
         * @see #stop()
         * @see #Thread(ThreadGroup, Runnable, String)
         */
        @Override
        public void run() {
            super.run();
            while (true){
                new GetTask().execute();
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * Returns a psuedo-random number between min and max, inclusive.
     * The difference between min and max can be at most
     * <code>Integer.MAX_VALUE - 1</code>.
     *
     * @param min Minimim value
     * @param max Maximim value.  Must be greater than min.
     * @return Integer between min and max, inclusive.
     * @see java.util.Random#nextInt(int)
     */
    public static Integer randInt(int min, int max) {

        // Usually this can be a field rather than a method variable
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        Integer randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    class GetTask extends AsyncTask<String, String, JSONObject> {


        JSONParser jsonParser = new JSONParser();
        ProgressDialog progressDialog;

        String subjectID = randInt(1,7).toString();

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog= new ProgressDialog(AssignmentsActivity.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Please wait...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            else
                return;
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

                Log.e("subjectID", subjectID);

                HashMap<String, String> map = new HashMap<>();
                map.put("method", "getAVg");
                map.put("userID", "2");
                map.put("subjectID", subjectID);

                JSONObject jsonObject = jsonParser.makeHttpRequest(Constants.URL, "POST", map);
                String c, a, s;
                a = jsonObject.getString("average");
                s = jsonObject.getString("subjectName");
                c = "Cass contribution: 0%";
                getData(s,a,c);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        public void getData(String subject, String average, String cass){
            final String s = subject;
            final String a = average;
            final String c = cass;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    taverage.setText(a);
                    tcass.setText(c);
                    tsubject.setText(s);
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
}
