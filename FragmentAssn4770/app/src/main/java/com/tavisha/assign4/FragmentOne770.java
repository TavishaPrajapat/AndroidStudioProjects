package com.tavisha.assign4;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class FragmentOne770 extends Fragment {

    private ArrayList<String> friendsList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Toast.makeText(context, "OnAttach() is Called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(), "OnCreate() is Called", Toast.LENGTH_SHORT).show();

        // Initialize friends list with names
        friendsList = new ArrayList<>();
        friendsList.add("Simer");
        friendsList.add("Tanmay");
        friendsList.add("Suhani");
        friendsList.add("Ajay");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one770, container, false);

        TextView text = view.findViewById(R.id.text_frag770);
        Button firstBtn = view.findViewById(R.id.btn_frag770);

        // Display the names in the TextView with more space between them
        StringBuilder friendsText = new StringBuilder("Friend's Names:\n\n");
        for (String friend : friendsList) {
            friendsText.append(friend).append("\n\n"); // Adding an extra line break for more space
        }
        text.setText(friendsText.toString());

        firstBtn.setOnClickListener(v -> Toast.makeText(getActivity(),
                "Welcome to the First Fragment",
                Toast.LENGTH_SHORT).show());

        return view;
    }
}
