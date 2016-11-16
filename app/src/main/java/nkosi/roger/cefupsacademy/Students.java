package nkosi.roger.cefupsacademy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Students extends Fragment implements APIController.StudentsCallBackListener{

    private APIController controller;
    private RecyclerView recyclerView;
    private ListAdapter listAdapter;
    private List<StudentModel> models = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controller =  new APIController(this);
        controller.fetchUsers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_students, container, false);

        recyclerView = (RecyclerView)view.findViewById(R.id.students);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        listAdapter = new ListAdapter(models);
        recyclerView.setAdapter(listAdapter);
        return view;
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(StudentModel model) {
        listAdapter.populate(model);
    }

    @Override
    public void onFetchProgress(List<StudentModel> models) {

    }

    @Override
    public void onFetchComplete() {

    }

    @Override
    public void onFetchFailed() {

    }


    class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder>{

        private List<StudentModel> studentModels;

        public ListAdapter(List<StudentModel> studentModels) {
            this.studentModels = studentModels;
        }

        public void populate(StudentModel model){
            studentModels.add(model);
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
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_row,parent, false);
            return new ListHolder(row);
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
        public void onBindViewHolder(ListHolder holder, int position) {
            final StudentModel model = this.studentModels.get(position);
            holder.textView.setText(model.pname);
            holder.textView1.setText(model.pprice);

            Picasso.with(holder.itemView.getContext()).load(Constants.BASE_URL + "/images/" + model.pimg).into(holder.imageView);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return studentModels.size();
        }

        class ListHolder extends RecyclerView.ViewHolder{

            public TextView textView, textView1;
            public CircleImageView imageView;
            public View view;

            public ListHolder(View itemView) {
                super(itemView);
                view = itemView;
                imageView = (CircleImageView)view.findViewById(R.id.profile_image);
                textView = (TextView)view.findViewById(R.id.c_name);
                textView1 = (TextView)view.findViewById(R.id.c_intro);
            }
        }
    }
}
