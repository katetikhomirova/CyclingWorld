package ua.nure.tikhomirova.cyclingworld;

import java.util.Arrays;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class MainActivity extends FragmentActivity {

	@Override
	public void onBackPressed() {
	}

	private LoginButton loginBtn;
	private TextView username;
	private UiLifecycleHelper uiHelper;

	public RouteListFragment fragment;

	private FragmentManager fragmentManager;

	public final static String TAG_1 = "FRAGMENT_1";

	@Override
	public void onCreate(Bundle savedInstanceState) {

		fragmentManager = getSupportFragmentManager();
		fragment = new RouteListFragment();

		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (TextView) findViewById(R.id.username);
		// Manage Facebook button
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("email"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {

			@Override
			public void onUserInfoFetched(GraphUser user) {

				if (user != null) {

					username.setText("Welcome " + user.getName() + "!");

					RouteListFragment fragment = (RouteListFragment) fragmentManager
							.findFragmentByTag(TAG_1);

					if (fragment == null) {
						fragment = new RouteListFragment();
						Bundle bundle = new Bundle();
						bundle.putString("id", user.getId());
						fragment.setArguments(bundle);

						FragmentTransaction fragmentTransaction = fragmentManager
								.beginTransaction();
						fragmentTransaction.replace(R.id.container, fragment,
								TAG_1);
						fragmentTransaction.commit();

					}
				} else {
					if (isNetworkConnected())
						username.setText("You are not logged in.");
					else
						username.setText("No internet connection.");
				}
			}
		});
	}

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {

		@Override
		public void call(Session session, SessionState state,

		Exception exception) {

			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}

		}

	};

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();

	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}

	private boolean isNetworkConnected() {
		@SuppressWarnings("static-access")
		ConnectivityManager cm = (ConnectivityManager) getSystemService(MainActivity.this.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if (ni == null) {
			// There are no active networks.
			return false;
		} else
			return true;
	}
}
