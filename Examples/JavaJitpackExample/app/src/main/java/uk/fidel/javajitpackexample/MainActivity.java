package uk.fidel.javajitpackexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.fidelapi.Fidel;
import com.fidelapi.entities.CardScheme;
import com.fidelapi.entities.Country;
import com.fidelapi.entities.FidelError;
import com.fidelapi.entities.FidelResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumSet;

public class MainActivity extends AppCompatActivity {

    public static final String FIDEL_DEBUG_TAG = "Fidel.Debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check this function to see an example of Fidel SDK configuration
        setupFidelSDK();

        // On click of the button present the Fidel card linking UI
        Button linkCardButton = findViewById(R.id.btn_link_card);
        linkCardButton.setOnClickListener(v -> Fidel.start(MainActivity.this));
    }

    private void setupFidelSDK() {
        Fidel.programId = "Your program ID. Please copy it from your Fidel dashboard.";

        /*
         * For a real project, it is recommended to store the SDK key on your backend and retrieve it
         * before starting to use the SDK, after the user authenticates.
         */
        Fidel.sdkKey = "pk_test_your_sdk_key";

        // Show your banner image on top of the card linking UI
        Fidel.bannerImage = BitmapFactory.decodeResource(getResources(), R.drawable.fidel_test_banner);

        // The countries that you will allow users to pick from.
        // If only one country is set, users will not need to pick a country.
        Fidel.allowedCountries = EnumSet.of(Country.UNITED_STATES, Country.CANADA, Country.IRELAND);
        // Sets the default selected country when the user opens the card enrollment screen.
        Fidel.defaultSelectedCountry = Country.UNITED_STATES;
        // The company name will be mentioned in the card linking consent text
        Fidel.companyName = "Cashback Inc.";
        // The privacy URL used in the card linking consent text
        Fidel.privacyPolicyUrl = "https://www.fidel.uk/";
        // If a north american country is part of the `allowedCountries` Set,
        // then this Terms & Conditions URL is mandatory
        Fidel.termsAndConditionsUrl = "https://fidel.uk/docs/";
        // The program name will be mentioned in the card linking consent text
        Fidel.programName = "Fidélité";
        // The card schemes that you are supporting (use Fidel.defaultSupportedCardSchemes to allow
        // all Fidel supported card schemes)
        Fidel.supportedCardSchemes = EnumSet.of(
                CardScheme.AMERICAN_EXPRESS,
                CardScheme.VISA,
                CardScheme.MASTERCARD
        );
        // The delete instructions which will be written in the card linking consent text
        Fidel.deleteInstructions = "going to your account settings";

        // Meta data associated with the linked card.
        JSONObject jsonMeta = new JSONObject();
        try {
            jsonMeta.put("id", "this-is-the-metadata-id");
            jsonMeta.put("customKey1", "customValue1");
            jsonMeta.put("customKey2", "customValue2");
        } catch (JSONException e) {
            Log.e(FIDEL_DEBUG_TAG, e.getLocalizedMessage());
        }
        Fidel.metaData = jsonMeta;

        // Add a card linking observer to handle a successful or failing card linking result.
        Fidel.onResult = fidelResult -> {
            if (fidelResult instanceof FidelResult.Enrollment) {
                String cardId = ((FidelResult.Enrollment) fidelResult).getEnrollmentResult().cardId;
                Log.d(FIDEL_DEBUG_TAG, "The card ID = " + cardId);
            } else if (fidelResult instanceof FidelResult.Error) {
                FidelError error = ((FidelResult.Error) fidelResult).getError();
                Log.e(FIDEL_DEBUG_TAG, "Error message = " + error);
            } else {
                Log.d(FIDEL_DEBUG_TAG, "Other result: " + fidelResult.toString());
            }
        };
        Fidel.onMainActivityCreate(this);
    }
}
