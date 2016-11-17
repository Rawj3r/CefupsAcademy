package nkosi.roger.cefupsacademy;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private CircleImageView profilePic;
    private TextView profileName, profileBio, profileGrade, profileDream,
            profilePlace, profileDateOfBirth, profileEmail, profileContactNum;
    private Typeface typeface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/arial.ttf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profile, container, false);

        new GetProfile().execute();

        profilePic = (CircleImageView)view.findViewById(R.id.profile_image);
        profileName = (TextView)view.findViewById(R.id.profile_name);
        profileBio = (TextView)view.findViewById(R.id.profile_bio);
        profileGrade = (TextView)view.findViewById(R.id.profile_grade);
        profileDream = (TextView)view.findViewById(R.id.profile_dream);
        profilePlace = (TextView)view.findViewById(R.id.place);
        profileDateOfBirth = (TextView)view.findViewById(R.id.date_of_birth);
        profileEmail = (TextView)view.findViewById(R.id.profile_email);
        profileContactNum = (TextView)view.findViewById(R.id.profile_contact_no);



        return view;
    }

    class GetProfile extends AsyncTask<String, String, JSONObject>{

        ProgressDialog progressDialog;
        JSONParser jsonParser = new JSONParser();


        /**
         * Runs on the UI thread before {@link #doInBackground}.
         *
         * @see #onPostExecute
         * @see #doInBackground
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Authenticating...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
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

            if (progressDialog != null){
                progressDialog.dismiss();
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
            try{
                HashMap<String, String> map = new HashMap<>();
                map.put("method", "userProfile");
                map.put("userID", "1");

                JSONObject jsonObject = jsonParser.makeHttpRequest("http://devwiggle.co.za/cefups/", "POST", map);

                String name = jsonObject.getString("firstName") + " " + jsonObject.getString("secondName")
                        + " " + jsonObject.getString("lastName");
                String uri = jsonObject.getString("uri");
                String bio = jsonObject.getString("bio");
                String grade = jsonObject.getString("school_grade");
                String dreams = jsonObject.getString("dreams");
                String place = jsonObject.getString("place");
                String num = jsonObject.getString("contactNum");
                String email = jsonObject.getString("email");
                String dob = jsonObject.getString("dateOfBirth") ;

                getData(name, uri, bio, grade, dreams, place, dob, email, num);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }

        public void getData(String name, String photoUri, String bio, String grade, String dream, String place,
                            String dob, String email, String num){
            final String fname = name;
            final String fphotoUri = photoUri;
            final String fbio = bio;
            final String fgrade = grade;
            final String fdream = dream;
            final String fplace = place;
            final String fdob = dob;
            final String femail =  email;
            final String fnum = num;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    profileName.setText(fname);
                    profileContactNum.setText("Contact nos: " +fnum);
                    profileEmail.setText("Email: " + femail);
                    profileDateOfBirth.setText("Date of Birth: " + fdob);
                    profilePlace.setText("Lives in: " + fplace);
                    profileGrade.setText("Doing grade: " + fgrade);
                    profileDream.setText("Dreams of: " + fdream);
                    profileBio.setText(fbio);

                    Picasso.with(getContext()).load(Constants.BASE_URL + "/images/" + fphotoUri).into(profilePic);
                }
            });

        }

    }


}
