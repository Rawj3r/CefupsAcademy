package nkosi.roger.cefupsacademy;


import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Home extends AppCompatActivity {

    private AHBottomNavigationItem home, profile, settings;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigation = (AHBottomNavigation)findViewById(R.id.bottom_navigation);

        FragmentManager manager1 =  getSupportFragmentManager();
        FragmentTransaction transaction1 = manager1.beginTransaction();
        transaction1.replace(R.id.frame, new HomeFragment());
        transaction1.commit();
        setTabs();
    }



    private void setTabs() {
        home = new AHBottomNavigationItem("Home", R.drawable.ic_action_home);
        profile = new AHBottomNavigationItem("Profile", R.drawable.ic_action_profile);
        settings = new AHBottomNavigationItem("settings", R.drawable.ic_action_settings);

        bottomNavigation.addItem(home);
        bottomNavigation.addItem(profile);
        bottomNavigation.addItem(settings);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#A20016"));
        bottomNavigation.setAccentColor(Color.parseColor("#B2D7D2"));
        bottomNavigation.setInactiveColor(Color.parseColor("#747474"));

        // Force to tint the drawable (useful for font with icon for example)
        bottomNavigation.setForceTint(true);

        // Force the titles to be displayed (against Material Design guidelines!)
        bottomNavigation.setForceTitlesDisplay(true);

        bottomNavigation.setCurrentItem(0);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {

                switch (position) {
                    case 0:
                        FragmentManager manager1 = getSupportFragmentManager();
                        FragmentTransaction transaction1 = manager1.beginTransaction();
                        transaction1.replace(R.id.frame, new HomeFragment());
                        transaction1.commit();
                        break;
                    case 1:
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.frame, new ProfileFragment());
                        transaction.commit();
                        break;
                }

                return false;
            }
        });

//        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(int position, boolean wasSelected) {
//                switch (position){
//                    case 0:
//                        FragmentManager manager1 =  getSupportFragmentManager();
//                        FragmentTransaction transaction1 = manager1.beginTransaction();
//                        transaction1.replace(R.id.frame, new HomeFragment());
//                        transaction1.commit();
//                        break;
//                    case 1:
//                        FragmentManager manager =  getSupportFragmentManager();
//                        FragmentTransaction transaction = manager.beginTransaction();
//                        transaction.replace(R.id.frame, new ProfileFragment());
//                        transaction.commit();
//                        break;
//
//                }
//            }
//        });
    }


}
