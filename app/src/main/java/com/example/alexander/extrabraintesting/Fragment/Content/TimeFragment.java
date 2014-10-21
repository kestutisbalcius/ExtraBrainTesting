package com.example.alexander.extrabraintesting.Fragment.Content;

        import android.app.ListFragment;
        import android.os.Bundle;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import com.example.alexander.extrabraintesting.R;

/**
 * Created by Daniel on 17/10/2014.
 */
public class TimeFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_time, container, false);
    }

}

