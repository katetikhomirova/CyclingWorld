package ua.nure.tikhomirova.cyclingworld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class RouteListFragment extends Fragment {

	TextView textMsg;
	String result = "";
	String id;
	ListView lvRoutes;
	Handler h;
	public View fragmentView;
	final String tag = "Your Logcat tag: ";
	// JSON Node names
	private static final String TAG_ROUTE_NAME = "name";
	private static final String TAG_ID = "id";
	private static final String TAG_DISTANCE = "distance";
	private static final String TAG_COORDS = "coordsString";
	
	ArrayList<HashMap<String, String>> routeList;
	ArrayList<HashMap<String, String>> coords;
	SimpleAdapter adapter;

	@SuppressLint("HandlerLeak")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fragmentView = inflater.inflate(R.layout.route_list_fragment,
				container, false);

		routeList = new ArrayList<HashMap<String, String>>();
		coords = new ArrayList<HashMap<String, String>>();

		lvRoutes = (ListView) fragmentView.findViewById(R.id.list_of_routes);

		h = new Handler() {
			public void handleMessage(android.os.Message msg) {
				if (msg.what == 1) {
					lvRoutes.setAdapter(adapter);
				}
			}
		};

		Bundle bundle = getArguments();
		if (bundle != null) {
			String msg = bundle.getString("id");
			if (msg != null) {
				id = msg;
				Thread thread = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							HttpClient httpclient = new DefaultHttpClient();
							HttpGet request = new HttpGet(
									"http://cyclingworld-service.cfapps.io/rest/getPolyLines/"
											+ id);
							ResponseHandler<String> handler = new BasicResponseHandler();
							Log.i(tag, "handler created");
							try {
								result = httpclient.execute(request, handler);

							} catch (ClientProtocolException e) {
								Log.i(tag, "clientprotocoleception");
								e.printStackTrace();
							} catch (IOException e) {
								Log.i(tag, "ioeception");
								e.printStackTrace();
							}
							httpclient.getConnectionManager().shutdown();
							if (result != null) {
								JSONArray routes = new JSONArray(result);
								for (int i = 0; i < routes.length(); i++) {
									JSONObject c = routes.getJSONObject(i);

									String name = c.getString(TAG_ROUTE_NAME);
									String distance = "Distance : "
											+ c.getString(TAG_DISTANCE) + " km";

									String routeCoords = c
											.getString(TAG_COORDS);
									HashMap<String, String> toCoords = new HashMap<String, String>();
									toCoords.put(TAG_ROUTE_NAME, name);
									toCoords.put(TAG_COORDS, routeCoords);
									coords.add(toCoords);

									HashMap<String, String> route = new HashMap<String, String>();
									route.put(TAG_ROUTE_NAME, name);
									route.put(TAG_DISTANCE, distance);

									routeList.add(route);
								}

								adapter = new SimpleAdapter(getActivity(),
										routeList, R.layout.list_item,
										new String[] { TAG_ROUTE_NAME,
												TAG_DISTANCE }, new int[] {
												R.id.routeName, R.id.distance }) {
									@Override
									public View getView(int position,
											View convertView, ViewGroup parent) {
										View v = super.getView(position,
												convertView, parent);
										Button b = (Button) v
												.findViewById(R.id.goToMap);
										b.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View view) {
												LinearLayout vwParentRow = (LinearLayout) view
														.getParent();

												TextView routeName = (TextView) vwParentRow
														.getChildAt(0);
												Log.i(tag, (String) routeName
														.getText());
												Log.i(tag, coords.size() + "");
												Intent intent = new Intent(
														getActivity(),
														Map.class);
												intent.putExtra(TAG_ID, id);
												for (HashMap<String, String> e : coords) {
													if (e.get(TAG_ROUTE_NAME) == routeName
															.getText())

														intent.putExtra(
																TAG_COORDS,
																e.get(TAG_COORDS));

												}

												getActivity().startActivity(
														intent);
											}
										});
										return v;
									}

								};
								h.sendEmptyMessage(1);
							}
							Log.i(tag, result);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});

				thread.start();
			}
		}
		return fragmentView;
	}
}
