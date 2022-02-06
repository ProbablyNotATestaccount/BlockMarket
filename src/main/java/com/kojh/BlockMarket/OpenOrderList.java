package com.kojh.BlockMarket;

import android.content.Context;
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
 * {@link OpenOrderList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OpenOrderList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpenOrderList extends androidx.fragment.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "restArr";
    private static final String ARG_PARAM2 = "priceArr";
    private static final String ARG_PARAM3 = "uidArr";

    // TODO: Rename and change types of parameters
    private String[] restArr;
    private String[] priceArr;
    private String[] uidArr;

    private OnFragmentInteractionListener mListener;

    public OpenOrderList() {
        Log.d("fragment OO","created");
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OpenOrderList.
     */
    // TODO: Rename and change types and number of parameters
    public static OpenOrderList newInstance(String[] param1, String[] param2, String[] param3) {
        OpenOrderList fragment = new OpenOrderList();
        Bundle args = new Bundle();
        args.putStringArray(ARG_PARAM1, param1);
        args.putStringArray(ARG_PARAM2, param2);
        args.putStringArray(ARG_PARAM3,param3);
        fragment.setArguments(args);
        Log.d("fragment OO","created");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("fragment OO","created");
        if (getArguments() != null) {
            restArr = getArguments().getStringArray(ARG_PARAM1);
            priceArr = getArguments().getStringArray(ARG_PARAM2);
            uidArr = getArguments().getStringArray(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("fragment OO","created");
        return inflater.inflate(R.layout.fragment_open_order_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstance){

        //change priority of buttons
        View pBut = getActivity().findViewById(R.id.HomeButton);
        pBut.setBackground(getActivity().getDrawable(R.drawable.home_button_noprio));
        View npBut = getActivity().findViewById(R.id.OrderButton);
        npBut.setBackground(getActivity().getDrawable(R.drawable.order_button_prio));

        //build programmatical UI
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup wrapper = (ViewGroup)view.findViewById(R.id.OOrderListVG);

        int num_orders = restArr.length;


        for(int i = 0; i < num_orders; i++) {
            View v = inflater.inflate(R.layout.open_order_base_element,null);
            Button clicker = (Button)v.findViewById(R.id.button5);
            Log.d("ID_ASSIGNER",uidArr[i]);
            clicker.setTag(R.id.tag_id,uidArr[i]);
            String str = restArr[i] + " - $" + priceArr[i];
            clicker.setText(str);


//            int alpha_v = (255-Constants.MIN_ACCEPTABLE_ALPHA)*i/num_restaurants + Constants.MIN_ACCEPTABLE_ALPHA;
//            Log.d("BUTTON",Integer.toString(alpha_v));
//            v.setAlpha(alpha_v);
            Log.d("BUTTON_CLICK_OO","clicker added");

            wrapper.addView(v,0);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
