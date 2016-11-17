package nkosi.roger.cefupsacademy;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyScheduleActivity extends AppCompatActivity implements APIController.TaskCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;

    private TaskAdapter adapter;
    private List<TaskModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        controller = new APIController(this);
        controller.fetchTasks();

        recyclerView = (RecyclerView)findViewById(R.id.task);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new TaskAdapter(modelList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(TaskModel model) {
        adapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<TaskModel> models) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public void onFetchFailed() {

    }

    public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.Holder>{

        private List<TaskModel> modelList;

        public TaskAdapter(List<TaskModel> modelList) {
            this.modelList = modelList;
        }

        public void populate(TaskModel model){
            modelList.add(model);
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
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
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
            final TaskModel model = this.modelList.get(position);
            holder.content.setText(model.content);
            holder.dateAdded.setText(model.datedAdded);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return modelList.size();
        }

        public class Holder extends RecyclerView.ViewHolder{

            public TextView content, dateAdded;
            public View parentView;
            public ImageView imageView;

            public Holder(View itemView) {
                super(itemView);
                this.parentView = itemView;
                content = (TextView)parentView.findViewById(R.id.task_content);
                imageView = (ImageView)parentView.findViewById(R.id.task_image);
                dateAdded = (TextView)parentView.findViewById(R.id.content_date);
            }
        }
    }
}
