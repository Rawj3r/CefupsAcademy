package nkosi.roger.cefupsacademy;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RecentActivities extends Fragment implements APIController.HomeCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;
    private PostsAdapter adapter;
    private List<PostModel> list =  new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new APIController(this);
        controller.fetchPosts();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recent_activities, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.posts);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        adapter = new PostsAdapter(list);
        recyclerView.setAdapter(adapter);

        return view;
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
        public String TAG = RecentActivities.class.getSimpleName();
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
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_single_post,parent, false);
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), RecentActivities.class);
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


}
