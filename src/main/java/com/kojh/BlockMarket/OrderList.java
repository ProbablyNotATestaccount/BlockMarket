package com.kojh.BlockMarket;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OrderList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OrderList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderList extends androidx.fragment.app.Fragment  {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String[] mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OrderList() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderList.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderList newInstance(String[] param1, String param2) {
        OrderList fragment = new OrderList();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getStringArray(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.d("menu fragment","Fragment Made");




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view = inflater.inflate(R.layout.fragment_order_list, container, false);


         return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){

        //change priority of buttons
        View pBut = getActivity().findViewById(R.id.HomeButton);
        pBut.setBackground(getActivity().getDrawable(R.drawable.home_button_prio));
        View npBut = getActivity().findViewById(R.id.OrderButton);
        npBut.setBackground(getActivity().getDrawable(R.drawable.order_button_noprio));

        //build programmatical UI
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup wrapper = (ViewGroup)view.findViewById(R.id.MenuListVG);

        int num_restaurants = mParam1.length;


        for(int i = 0; i < num_restaurants; i++) {
            View v = inflater.inflate(R.layout.restaurant_base_element,null);
            Button nameBtn = v.findViewById(R.id.button3);
            nameBtn.setText(mParam1[i]);

//            int alpha_v = (255-Constants.MIN_ACCEPTABLE_ALPHA)*i/num_restaurants + Constants.MIN_ACCEPTABLE_ALPHA;
//            Log.d("BUTTON",Integer.toString(alpha_v));
//            v.setAlpha(alpha_v);
            Log.d("BUTTON_CLICK","clicker added");

            wrapper.addView(v,0);
        }
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
