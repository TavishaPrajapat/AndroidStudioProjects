package com.tavisha.assign4;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FragmentTwo770 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two770, container, false);

        ImageView image1 = view.findViewById(R.id.image_friend1);
        ImageView image2 = view.findViewById(R.id.image_friend2);
        ImageView image3 = view.findViewById(R.id.image_friend3);
        ImageView image4 = view.findViewById(R.id.image_friend4);
        Button btn = view.findViewById(R.id.second_btn770);

        // Set sample images (use drawable resources)
        image1.setImageResource(R.drawable.simer); // Replace with actual drawable name
        image2.setImageResource(R.drawable.tanmay);
        image3.setImageResource(R.drawable.suhani);
        image4.setImageResource(R.drawable.ajay);// Replace with actual drawable name

        btn.setOnClickListener(v -> Toast.makeText(getActivity(),
                "Good Bye From Second Fragment",
                Toast.LENGTH_SHORT).show());

        return view;
    }
}