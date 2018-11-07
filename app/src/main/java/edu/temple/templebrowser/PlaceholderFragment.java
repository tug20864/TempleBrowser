package edu.temple.templebrowser;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class PlaceholderFragment extends Fragment implements FragmentCommunitcator {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    View rootView;
    private WebView webView;
    private String url;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * this method will load the fragment layout
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //HOOK UP web view
        webView = (WebView)rootView.findViewById(R.id.webViewWidget);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //SETUP FORWARD AND BACKWARD BUTTONS
        //Button Initialization
        final Button backButton =(Button) rootView.findViewById(R.id.BrowserBackButton);
        final Button forwardButton =(Button) rootView.findViewById(R.id.browserForwardButton);

//Back Button Action
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Going back if canGoBack true
                if(webView.canGoBack()){
                    webView.goBack();
                }
            }
        });
//Forward Button Action
        forwardButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Go Forward if canGoForward is frue

                if(webView.canGoForward()){
                    webView.goForward();
                }
            }
        });


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onResume(){
        super.onResume();


    }
    public String getUrl(){
        return url;
    }

    /**
     * this method will get bundle passed to it and extract
     * the url string and then load the webpage
     */
    @Override
    public void passDataToFragment() {

        //access bundle and update the web browser
        TextView textView = rootView.findViewById(R.id.webFragTextView);
        //access the text in the bundle
        Bundle bundle = getArguments();
        // post the text in the web text
        url = bundle.getString("url");
        //set the text view
        textView.setText(url);
        //attempt to load web url
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient());
    }
}
