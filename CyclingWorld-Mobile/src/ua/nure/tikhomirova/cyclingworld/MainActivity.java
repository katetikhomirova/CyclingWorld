package ua.nure.tikhomirova.cyclingworld;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;

public class MainActivity extends FragmentActivity {

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

		/*
		if (savedInstanceState == null) { // при первом запуске программы
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction(); // добавляем в контейнер при помощи
											// метода add()
			fragmentTransaction.add(R.id.container, fragment, TAG_1);
			fragmentTransaction.commit();
		}*/

		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		username = (TextView) findViewById(R.id.username);
		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
		loginBtn.setReadPermissions(Arrays.asList("email"));
		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {

			@Override
			public void onUserInfoFetched(GraphUser user) {

				if (user != null) {

					username.setText("You are currently logged in as "
							+ user.getName());
					
					RouteListFragment fragment = (RouteListFragment) fragmentManager
							.findFragmentByTag(TAG_1);

					if (fragment == null) {
						fragment = new RouteListFragment();
						Bundle bundle = new Bundle();
						bundle.putString("id",
								user.getId());
						fragment.setArguments(bundle);

						FragmentTransaction fragmentTransaction = fragmentManager
								.beginTransaction();
						fragmentTransaction.replace(R.id.container, fragment,
								TAG_1);
						fragmentTransaction.commit();

					} else {
						//fragment.setMsg("Первый фрагмент уже загружен");
					}

				} else {

					username.setText("You are not logged in.");

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
}
