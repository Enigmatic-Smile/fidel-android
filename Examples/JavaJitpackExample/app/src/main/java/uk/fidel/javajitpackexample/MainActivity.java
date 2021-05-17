package uk.fidel.javajitpackexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.fidel.sdk.Fidel;
import com.fidel.sdk.LinkResult;
import com.fidel.sdk.LinkResultError;
import com.fidel.sdk.data.abstraction.FidelCardLinkingObserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check this function to see an example of Fidel SDK configuration
        setupFidelSDK();

        // On click of the button present the Fidel card linking UI
        Button linkCardButton = findViewById(R.id.btn_link_card);
        linkCardButton.setOnClickListener(v -> Fidel.present(MainActivity.this));
    }

    private void setupFidelSDK() {
        Fidel.programId = "Your program ID. Please copy it from your Fidel dashboard.";
        Fidel.apiKey = "pk_test_your_sdk_key";

        // Show your banner image on top of the card linking UI
        Fidel.bannerImage = BitmapFactory.decodeResource(getResources(), R.drawable.fidel_test_banner);
        // Set the autoScan flag to true, if you'd like the SDK to open the card scanning UI
        // immediately after presenting.
        Fidel.autoScan = false;
        // The countries that you will allow users to pick from.
        // If only one country is set, users will not need to pick a country.
        Fidel.allowedCountries = Fidel.Country.values();
        // The company name will be mentioned in the card linking consent text
        Fidel.companyName = "Cashback Inc.";
        // The privacy URL used in the card linking consent text
        Fidel.privacyURL = "https://www.fidel.uk/";
        // If a north american country is part of the `allowedCountries` Set,
        // then this Terms & Conditions URL is mandatory
        Fidel.termsConditionsURL = "https://fidel.uk/docs/";
        // The program name will be mentioned in the card linking consent text
        Fidel.programName = "Fidélité";
        // The card schemes that you are supporting (use Fidel.defaultSupportedCardSchemes to allow
        // all Fidel supported card schemes)
        Fidel.supportedCardSchemes = EnumSet.of(
                Fidel.CardScheme.AMERICAN_EXPRESS,
                Fidel.CardScheme.VISA,
                Fidel.CardScheme.MASTERCARD
        );
        // The delete instructions which will be written in the card linking consent text
        Fidel.deleteInstructions = "going to your account settings";

        // Meta data associated with the linked card.
        JSONObject jsonMeta = new JSONObject();
        try {
            jsonMeta.put("id", "this-is-the-metadata-id");
            jsonMeta.put("customKey1", "customValue1");
            jsonMeta.put("customKey2", "customValue2");
        }  catch(JSONException e) {
            Log.e(Fidel.FIDEL_DEBUG_TAG, e.getLocalizedMessage());
        }
        Fidel.metaData = jsonMeta;

        // Add a card linking observer to handle a successful or failing card linking result.
        // The alternative is to use the `onActivityResult` overridden function (see below), but that approach
        // will send you the result only once the UI is closed.
        Fidel.setCardLinkingObserver(new FidelCardLinkingObserver() {
            @Override
            public void onCardLinkingFailed(LinkResultError linkResultError) {
                Log.e("Fidel Error", "Error message = " + linkResultError.message);
            }

            @Override
            public void onCardLinkingSucceeded(LinkResult linkResult) {
                Log.d("Fidel Debug Info", "The link ID = " + linkResult.id);
            }
        });
    }

    // Override this function in your activity if you want to get the result after the card linking UI finishes
    // The alternative (and you can get more "status" updates while the activity is still presented)
    // is to set an observer via  Fidel.setCardLinkingObserver(FidelCardLinkingObserver). See an example above
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Use this request code to identifier the result you're trying to handle
        if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
            if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
                LinkResult linkResult = data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
                LinkResultError error = linkResult.getError();
                if (error != null) {
                    Log.e("Fidel Error", "Error message = " + error.message);
                } else {
                    Log.d("Fidel Debug Info", "The link ID = " + linkResult.id);
                }
            }
        }
    }
}