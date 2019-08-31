package com.example.lpukipathshala.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.lpukipathshala.Category.Category_Main;
import com.example.lpukipathshala.MainActivity;
import com.example.lpukipathshala.Myaccount.AccountDetails;
import com.example.lpukipathshala.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class ProductGridFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }
        MaterialButton myaccount,category;
        FirebaseApp mAuth;
        @Override
        public View onCreateView(
                @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.product_grid_fragment, container, false);
            setUpToolbar(view);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                view.findViewById(R.id.product_grid).setBackground(getContext().getDrawable(R.drawable.shr_product_grid_background_shape));
            }
            RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            ArrayList<ProductEntry> arrayList = new ArrayList<>();
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
            arrayList.add(new ProductEntry("qwe","123"));
          GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL , false);
           ProductCardRecyclerViewAdapter adapter = new ProductCardRecyclerViewAdapter(arrayList);
           // StaggeredProductCardRecyclerViewAdapter adapter = new StaggeredProductCardRecyclerViewAdapter(arrayList);
            recyclerView.setAdapter(adapter);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return 1;
                }
            });
            recyclerView.setLayoutManager(gridLayoutManager);
//            int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_large);
//            int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_staggered_product_grid_spacing_small);
                int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
                int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);
            recyclerView.addItemDecoration(new ProductGridItemDecoration(largePadding, smallPadding));

            return view;
        }

        private void setUpToolbar(final View view) {
            Toolbar toolbar = view.findViewById(R.id.app_bar);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            if (activity != null) {
                activity.setSupportActionBar(toolbar);
            }

            //toolbar.setNavigationOnClickListener(new NavigationIconClickListener(getContext(), view.findViewById(R.id.product_grid)));
            toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                    getContext(),
                    view.findViewById(R.id.product_grid),
                    new AccelerateDecelerateInterpolator(),
            getContext().getResources().getDrawable(R.drawable.menu),
                    getContext().getResources().getDrawable(R.drawable.close)));


            myaccount = view.findViewById(R.id.myaccount);
            myaccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext(), AccountDetails.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            category = view.findViewById(R.id.categories);
            category.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), Category_Main.class);
                startActivity(intent);
                getActivity().finish();
            });
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.menu_option, menu);
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.filter :
                Log.i("Filter","-------------->");
                break;
            case R.id.search :
                Log.i("Search","-------------->");
                break;
        }
            return true;
    }



}
