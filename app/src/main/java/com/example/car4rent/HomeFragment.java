package com.example.car4rent;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.ImageView;
import android.widget.TextView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.car4rent.Apdapter.ImageAdapter;
import com.example.car4rent.Carousel.ImageModel;
import com.example.car4rent.Carousel.ImageViewActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private TextView textUserName;
    private FirebaseAuth mAuth;
    private Button btnXeTuLai;
    private Button btnXeCoTaiXe;
    private RecyclerView recyclerViewC;
    private CarRenderAdapter carRenderAdapter;
    private ArrayList<ProductCar> carList = new ArrayList<>();
    private FirebaseFirestore db;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        ArrayList<ImageModel> arrayList = new ArrayList<>();

        arrayList.add(new ImageModel("file:///android_asset/image/img_SG.jpg", "TP. HỒ CHÍ MINH"));
        arrayList.add(new ImageModel("file:///android_asset/image/img_HN.jpg", "HÀ NỘI"));
        arrayList.add(new ImageModel("file:///android_asset/image/img_DN.jpg", "ĐÀ NẴNG"));
        arrayList.add(new ImageModel("file:///android_asset/image/img_DL.jpg", "ĐÀ LẠT"));
        arrayList.add(new ImageModel("file:///android_asset/image/img_NT.jpg", "NHA TRANG"));
        arrayList.add(new ImageModel("file:///android_asset/image/img_VT.jpg", "VŨNG TÀU"));

        ImageAdapter adapter = new ImageAdapter(requireActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onClick(ImageView imageView, String imageUrl, String description) {
                Intent intent = new Intent(requireActivity(), ImageViewActivity.class);
                intent.putExtra("image", imageUrl);
                intent.putExtra("description", description);
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(requireActivity(), imageView, "image");
                startActivity(intent, options.toBundle());
            }
        });


        recyclerViewC = view.findViewById(R.id.recyclerC);
//        recyclerViewC.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerViewC.setHasFixedSize(true);
        recyclerViewC.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        db = FirebaseFirestore.getInstance();

        loadCarsFromFirebase();
        return view;
    }
    private void loadCarsFromFirebase() {
        db.collection("items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                carList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    ProductCar car = document.toObject(ProductCar.class);
                    carList.add(car);
                }
                // Initialize and set adapter for RecyclerView
                carRenderAdapter = new CarRenderAdapter(requireContext(), carList);
                recyclerViewC.setAdapter(carRenderAdapter);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnXeTuLai = view.findViewById(R.id.btnXeTuLai);
        btnXeCoTaiXe = view.findViewById(R.id.btnXeCoTaiXe);
        btnXeTuLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnXeTuLai.setSelected(true);
                btnXeCoTaiXe.setSelected(false);
            }
        });
        btnXeCoTaiXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnXeTuLai.setSelected(false);
                btnXeCoTaiXe.setSelected(true);
            }
        });
    }
    private String getUserDisplayName(FirebaseUser user) {
        return user.getDisplayName();
    }
}