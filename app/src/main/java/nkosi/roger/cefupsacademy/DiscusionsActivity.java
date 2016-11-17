package nkosi.roger.cefupsacademy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DiscusionsActivity extends AppCompatActivity implements APIController.HomeCallBackListener {

    private APIController controller;
    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private List<PostModel> list =  new ArrayList<>();
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discusions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(" ");

        recyclerView = (RecyclerView)findViewById(R.id.posts);

        controller = new APIController(this);
        controller.fetchPosts();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new PostsAdapter(list);
        recyclerView.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DiscusionsActivity.this);

            }
        });
    }

    public String getUserID(){
        SharedPreferences sharedPreferences = getSharedPreferences("wiggle_whiterivertechnical_user_id", Context.MODE_PRIVATE);
        String userID = sharedPreferences.getString("memID", "");
        return userID;
    }

    private void showDialog(Context context){
        // custom dialog
        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.make_post_layout);


        final AutoCompleteTextView subject = (AutoCompleteTextView)dialog.findViewById(R.id.edit_subject_post);
        final AutoCompleteTextView content = (AutoCompleteTextView)dialog.findViewById(R.id.edit_body_post);
        Button submit = (Button)dialog.findViewById(R.id.make_post);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject1 = subject.getText().toString();
                String content1 = content.getText().toString();
                new MakePost().execute(subject1, content1);
                Intent intent = new Intent(DiscusionsActivity.this, DiscusionsActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(PostModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<PostModel> modelList) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }

    private class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Holder>{
        public String TAG = DiscusionsActivity.class.getSimpleName();
        private List<PostModel> postModels;

        public PostsAdapter(List<PostModel> contactsModels) {
            this.postModels = contactsModels;
        }

        public void populate(PostModel model){
            postModels.add(model);
            notifyDataSetChanged();
        }

        /**
         * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
         * an item.
         * <p>
         * This new ViewHolder should be constructed with a new View that can represent the items
         * of the given type. You can either create a new View manually or inflate it from an XML
         * layout file.
         * <p>
         * The new ViewHolder will be used to display items of the adapter using
         * {@link #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
         * different items in the data set, it is a good idea to cache references to sub views of
         * the View to avoid unnecessary {@link View#findViewById(int)} calls.
         *
         * @param parent   The ViewGroup into which the new View will be added after it is bound to
         *                 an adapter position.
         * @param viewType The view type of the new View.
         * @return A new ViewHolder that holds a View of the given view type.
         * @see #getItemViewType(int)
         * @see #onBindViewHolder(ViewHolder, int)
         */
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,parent, false);
            return new Holder(row);
        }

        /**
         * Called by RecyclerView to display the data at the specified position. This method should
         * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
         * position.
         * <p>
         * Note that unlike {@link ListView}, RecyclerView will not call this method
         * again if the position of the item changes in the data set unless the item itself is
         * invalidated or the new position cannot be determined. For this reason, you should only
         * use the <code>position</code> parameter while acquiring the related data item inside
         * this method and should not keep a copy of it. If you need the position of an item later
         * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
         * have the updated adapter position.
         * <p>
         * Override {@link #onBindViewHolder(ViewHolder, int, List)} instead if Adapter can
         * handle efficient partial bind.
         *
         * @param holder   The ViewHolder which should be updated to represent the contents of the
         *                 item at the given position in the data set.
         * @param position The position of the item within the adapter's data set.
         */
        @Override
        public void onBindViewHolder(Holder holder, int position) {
            final PostModel model = this.postModels.get(position);
            holder.body.setText(model.body);
            holder.subject.setText(model.subject);
            holder.date.setText(model.datedAdded);

            holder.parentView.setSelected(list.contains(position));
            holder.parentView.setOnClickListener(new View.OnClickListener() {
                String id = model.id;
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DiscusionsActivity.this, SinglePost.class);
                    intent.putExtra("id", id);
                    startActivityForResult(intent,1);
                }
            });
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return postModels.size();
        }

        public class Holder extends RecyclerView.ViewHolder{
            public TextView subject, body, date;
            public View parentView;

            public Holder(View itemView) {
                super(itemView);
                this.parentView = itemView;
                subject = (TextView)itemView.findViewById(R.id.subject);
                body = (TextView)itemView.findViewById(R.id.body);
                date = (TextView)itemView.findViewById(R.id.date);


            }
        }
    }

    private class MakePost extends AsyncTask<String, String, JSONObject> {

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
            progressDialog = new ProgressDialog(DiscusionsActivity.this);
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
            if (progressDialog != null){
                progressDialog.dismiss();
            }

            try {
                if (jsonObject != null){
                    String message = jsonObject.getString("message");
                    Toast.makeText(DiscusionsActivity.this, message, Toast.LENGTH_SHORT).show();
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

            try{
                HashMap<String, String> map = new HashMap<>();
                map.put("method", "post");
                map.put("userID", getUserID());
                map.put("post", params[1]);
                map.put("heading", params[0]);

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
