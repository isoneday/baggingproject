package com.teamproject.plastikproject.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.teamproject.plastikproject.R;
import com.teamproject.plastikproject.activity.ShoppingLocationActivity;
import com.teamproject.plastikproject.imageslider.application.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.myinnos.imagesliderwithswipeslibrary.Animations.DescriptionAnimation;
import in.myinnos.imagesliderwithswipeslibrary.SliderLayout;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.BaseSliderView;
import in.myinnos.imagesliderwithswipeslibrary.SliderTypes.TextSliderView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment  implements BaseSliderView.OnSliderClickListener  {
    Unbinder unbinder;
    private SliderLayout mDemoSlider;
    LinearLayout linelocation;
    // Billionaires json url
    private static final String getURL = "http://35.231.18.55:3000/api/smart_advertisings";
    HashMap<String, String> url_maps;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mDemoSlider = (SliderLayout)view.findViewById(R.id.slider);
        linelocation = (LinearLayout) view.findViewById(R.id.linelocation);
        linelocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), ShoppingLocationActivity.class);
                startActivity(intent1);
                getActivity().finish();
            }
        });
        unbinder = ButterKnife.bind(this, view);
        // Creating volley request obj
        JsonArrayRequest billionaireReq = new JsonArrayRequest(getURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        url_maps = new HashMap<String, String>();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                url_maps.put(obj.getString("company") + " - " + obj.getString("publish_time"), obj.getString("image_name"));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (String name : url_maps.keySet()) {
                            TextSliderView textSliderView = new TextSliderView(getActivity());
                            // initialize a SliderLayout
                            textSliderView
                                    .description(name)
                                    .descriptionSize(20)
                                    .image("http://35.231.18.55:3000/api/attachments/advertising/download/"+url_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                                   ;

                            //add your extra information
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle().putString("extra", name);

                            mDemoSlider.addSlider(textSliderView);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "network issue: please enable wifi/mobile data"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(billionaireReq);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Stack);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Top);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);

        mDemoSlider.setPresetTransformer("Stack");

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    @Override
    public void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(getContext(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
