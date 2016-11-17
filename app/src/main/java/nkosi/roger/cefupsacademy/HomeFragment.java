package nkosi.roger.cefupsacademy;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MyCefups> persons;
    private FragmentPagerAdapter adapter;
    private ViewPager pager;
    private FragmentManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        manager = getChildFragmentManager();
        pager = (ViewPager)view.findViewById(R.id.view_pager);

        adapter = new MyPagerAdapter(manager);

        final ArrayList<Fragment> list = new ArrayList<>();
        list.add(new RecentActivities());
        list.add(new Students());

        pager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        mRecyclerView = (RecyclerView)view.findViewById(R.id.my_cefups);
        mLinearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        initializeData();

        RVAdapter adapter = new RVAdapter(persons);
        mRecyclerView.setAdapter(adapter);


        return view;
    }


    public class MyCefups {
        private String name;
        private int photoId;

        public MyCefups(String name, int photoId) {
            this.name = name;
            this.photoId = photoId;
        }
    }

    // This method creates an ArrayList that has three Person objects
    // Checkout the project associated with this tutorial on Github if
    // you want to use the same images.
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new MyCefups("Assignments", R.drawable.ic_assignment_turned_in));
        persons.add(new MyCefups("Class Tests", R.drawable.ic_assignment));
        persons.add(new MyCefups("My Schedule", R.drawable.ic_date_range ));
        persons.add(new MyCefups("My Account", R.drawable.ic_account_box));
        persons.add(new MyCefups("Announcements", R.drawable.ic_announcement));
        persons.add(new MyCefups("Discussions", R.drawable.ic_record_voice_over));
        persons.add(new MyCefups("School Events", R.drawable.ic_event));
    }

    public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CefupsViewHolder>{

        List<MyCefups> list;

        public RVAdapter(List<MyCefups> list) {
            this.list = list;
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
        public CefupsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cefups_row, parent, false);
            return new CefupsViewHolder(v);
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
        public void onBindViewHolder(CefupsViewHolder holder, final int position) {
            holder.textView.setText(list.get(position).name);
            holder.imageView.setImageResource(list.get(position).photoId);
            holder.view.setSelected(persons.contains(position));
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (position){
                        case Constants.discussions:
                            startActivity(new Intent(getContext(), DiscusionsActivity.class));
                            break;

                        case Constants.schedule:
                            startActivity(new Intent(getContext(), MyScheduleActivity.class));
                            break;


                        default:
                            return;

                    }
                }
            });

        }

        /**
         * Called by RecyclerView when it starts observing this Adapter.
         * <p>
         * Keep in mind that same adapter may be observed by multiple RecyclerViews.
         *
         * @param recyclerView The RecyclerView instance which started observing this adapter.
         * @see #onDetachedFromRecyclerView(RecyclerView)
         */
        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        /**
         * Returns the total number of items in the data set held by the adapter.
         *
         * @return The total number of items in this adapter.
         */
        @Override
        public int getItemCount() {
            return list.size();
        }

        public class CefupsViewHolder extends RecyclerView.ViewHolder{

            TextView textView;
            ImageView imageView;
            View view;

            public CefupsViewHolder(View itemView) {
                super(itemView);
                view = itemView;
                textView = (TextView)view.findViewById(R.id.my_cefups_txt_view);
                imageView = (ImageView)view.findViewById(R.id.my_cefups_image);
            }
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return the Fragment associated with a specified position.
         *
         * @param position
         */
        @Override
        public Fragment getItem(int position) {
           return null;
        }

        /**
         * Return the number of views available.
         */
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        /**
         * This method may be called by the ViewPager to obtain a title string
         * to describe the specified page. This method may return null
         * indicating no title for this page. The default implementation returns
         * null.
         *
         * @param position The position of the title requested
         * @return A title for the requested page
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }
    }

}
