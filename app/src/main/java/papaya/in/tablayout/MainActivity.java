package papaya.in.tablayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import papaya.in.tablayout.databinding.ActivityMainBinding;
import papaya.in.tablayout.fragment.OneFragment;
import papaya.in.tablayout.fragment.TwoFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivity activity;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        activity = this;
        
        initView();
    }

    private void initView() {
        setupViewPager(binding.viewPager);
//        binding.tabLayout.setupWithViewPager(binding.viewPager);


        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position)-> {tab.setText(adapter.mFragmentTitleList.get(position));
//                tab.setCustomView(R.layout.custom_tab);
        }).attach();

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++){

            TextView tv = (TextView) LayoutInflater.from(activity)
                    .inflate(R.layout.custom_tab, null);

            binding.tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapter =new ViewPagerAdapter(activity.getSupportFragmentManager(),
          activity.getLifecycle()     );
        adapter.addFragment(new OneFragment(), "Papaya");
        adapter.addFragment(new TwoFragment(), "Coders");


        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);

    }



    class ViewPagerAdapter extends FragmentStateAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String>mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }
    }
}