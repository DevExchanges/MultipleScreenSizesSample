package info.devexchanges.multiplescreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ListViewAdapter adapter;
    private WebView webView;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_item);
        webView = (WebView) findViewById(R.id.web);
        adapter = new ListViewAdapter(this, R.layout.item_list);

        //adding data to adapter
        adapter.add(new Country("Vietnam", R.drawable.vn));
        adapter.add(new Country("China", R.drawable.cn));
        adapter.add(new Country("Singapore", R.drawable.sg));
        adapter.add(new Country("India", R.drawable.in));
        adapter.add(new Country("Iran", R.drawable.ir));
        adapter.add(new Country("North Korea", R.drawable.kp));
        adapter.add(new Country("South Korea", R.drawable.kr));
        adapter.add(new Country("Thailand", R.drawable.th));
        adapter.add(new Country("Malaysia", R.drawable.my));

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (webView == null) { //always check null for web view
                    //do nothing
                } else {
                    WebSettings settings = webView.getSettings();
                    settings.setJavaScriptEnabled(true);
                    webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

                    final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                    progressBar = ProgressDialog.show(MainActivity.this, "WebView Example", "Loading...");

                    webView.setWebViewClient(new WebViewClient() {
                        public boolean shouldOverrideUrlLoading(WebView view, String url) {
                            view.loadUrl(url);
                            return true;
                        }

                        public void onPageFinished(WebView view, String url) {
                            if (progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                        }

                        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                            Toast.makeText(MainActivity.this, "Oh no! " + description, Toast.LENGTH_SHORT).show();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage(description);
                            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    return;
                                }
                            });
                            alertDialog.show();
                        }
                    });
                    webView.loadUrl("https://en.wikipedia.org/wiki/" + ((Country) parent.getItemAtPosition(position)).getName());
                }
            }
        });
    }

    private class ListViewAdapter extends ArrayAdapter<Country> {

        public ListViewAdapter(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_list, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            //setting data to view
            holder.countryName.setText(getItem(position).getName());
            holder.countryFlag.setImageResource(getItem(position).getFlagId());

            return convertView;
        }
    }

    private class ViewHolder {
        private TextView countryName;
        private ImageView countryFlag;

        public ViewHolder(View v) {
            countryFlag = (ImageView) v.findViewById(R.id.flag);
            countryName = (TextView) v.findViewById(R.id.name);
        }
    }
}
