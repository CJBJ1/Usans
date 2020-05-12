package com.example.usans.SceneFragment;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.usans.Data.FacilityList;
import com.example.usans.Data.User;
import com.example.usans.LoginActivity;
import com.example.usans.R;
import com.example.usans.RequestHttpURLConnection;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;


public class MypageFragment extends Fragment implements View.OnClickListener {
    private View view;
    public static final String TAG = "ServerAuthCodeActivity";
    private static final int RC_GET_AUTH_CODE = 9003;
    private FacilityList facilityList;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView userName;
    private TextView userName2;
    private ImageView userImage;
    private TextView mAuthCodeTextView;
    private JSONObject jsonObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mypage, container, false);


        view.findViewById(R.id.sign_in_button).setOnClickListener( this);
        view.findViewById(R.id.sign_out_button).setOnClickListener(this);
        userName = (TextView)view.findViewById(R.id.user_name);
        userName2 = (TextView)view.findViewById(R.id.user_name2);
        userName.setText("이름");
        userName2.setText("닉네임");
        userImage = (ImageView)view.findViewById(R.id.user_image);
        facilityList = (FacilityList) getActivity().getApplication();

        validateServerClientID();
        //OAuth serverClientId
        updateUI();

        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER))
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
        return view;
    }

    private void getAuthCode() {
        //인증 코드가 온다.
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GET_AUTH_CODE);
    }

    private void signOut() {
        mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                updateUI();
            }
        });
    }

    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess().addOnCompleteListener(getActivity(),
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_GET_AUTH_CODE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                String authCode = account.getServerAuthCode();

                //String url = "http://3.34.18.171.nip.io:8000/complete/google-oauth2/?auth_code=" + authCode;
                String url = "http://3.34.18.171.nip.io:8000/get-code/?state=state_parameter_passthrough_value&code="+authCode+"&scope=email+profile+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.metadata.readonly+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.profile+openid&authuser=0&prompt=none#";
                NetworkTaskAuth networkTaskAuth = new NetworkTaskAuth(url, null);
                networkTaskAuth.execute();
                Log.d("된다",authCode);

            } catch (ApiException e) {
                Log.w(TAG, "안된다 안돼", e);
                updateUI();
            }
        }
    }

    private void updateUI() {
        view.findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        view.findViewById(R.id.sign_out_button).setVisibility(View.GONE);
    }

    private void validateServerClientID() {
        String serverClientId = getString(R.string.server_client_id);
        String suffix = ".apps.googleusercontent.com";
        if (!serverClientId.trim().endsWith(suffix)) {
            String message = "Invalid server client ID in strings.xml, must end with " + suffix;

            Log.w(TAG, message);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                getAuthCode();
                break;
            case R.id.sign_out_button:
                signOut();
                break;
            /*case R.id.disconnect_button:
                revokeAccess();
                break;*/
        }
    }

    public class NetworkTaskAuth extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTaskAuth(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "basic";
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                String imageUrl = jsonObject.getString("picture");
                Glide.with(getActivity()).load(imageUrl).into(userImage);

                userName.setText(jsonObject.getString("name"));
                userName2.setText(jsonObject.getString("email"));

                User user = new User();
                facilityList = (FacilityList)getActivity().getApplication();
                user.setId(jsonObject.getString("id"));
                user.setName(jsonObject.getString("name"));
                user.setEmail(jsonObject.getString("email"));
                user.setPicture(jsonObject.getString("picture"));
                facilityList.setUser(user);

                view.findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                view.findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
