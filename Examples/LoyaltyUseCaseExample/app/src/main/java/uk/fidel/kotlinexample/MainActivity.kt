package uk.fidel.kotlinexample

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.fidelapi.Fidel
import com.fidelapi.entities.CardScheme
import com.fidelapi.entities.Country
import com.fidelapi.entities.FidelResult
import com.fidelapi.entities.abstraction.OnResultObserver
import org.json.JSONException
import org.json.JSONObject
import java.util.EnumSet

class MainActivity : AppCompatActivity() {

    companion object {
        private const val FIDEL_DEBUG_TAG: String = "Fidel.Debug"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Check this function to see an example of Fidel SDK configuration
        setupFidelSDK()

        // On click of the button present the Fidel card linking UI
        val linkCardButton = findViewById<Button>(R.id.btn_link_card)
        linkCardButton.setOnClickListener { Fidel.start(this@MainActivity) }
    }

    private fun setupFidelSDK() {
        Fidel.programId = "Your program ID. Please copy it from your Fidel dashboard."

        /**
         * For a real project, it is recommended to store the SDK key on your backend and retrieve it
         * before starting to use the SDK, after the user authenticates.
         */
        Fidel.sdkKey = "pk_test_your_sdk_key"

        // Show your banner image on top of the card linking UI
        Fidel.bannerImage = BitmapFactory.decodeResource(resources, R.drawable.fidel_test_banner)
        // The countries that you will allow users to pick from.
        // If only one country is set, users will not need to pick a country.
        Fidel.allowedCountries = Country.values().toSet()
        // Sets the default selected country when the user opens the card enrollment screen.
        Fidel.defaultSelectedCountry = Country.UNITED_STATES
        // The company name will be mentioned in the card linking consent text
        Fidel.companyName = "Cashback Inc."
        // The privacy URL used in the card linking consent text
        Fidel.programName = "https://www.fidel.uk/"
        // If a north american country is part of the `allowedCountries` Set,
        // then this Terms & Conditions URL is mandatory
        Fidel.termsAndConditionsUrl = "https://fidel.uk/docs/"
        // The program name will be mentioned in the card linking consent text
        Fidel.programName = "Fidélité"
        // The card schemes that you are supporting (use Fidel.defaultSupportedCardSchemes to allow
        // all Fidel supported card schemes)
        Fidel.supportedCardSchemes = EnumSet.of(
            CardScheme.AMERICAN_EXPRESS,
            CardScheme.VISA,
            CardScheme.MASTERCARD
        )
        // The delete instructions which will be written in the card linking consent text
        Fidel.deleteInstructions = "going to your account settings"

        // Meta data associated with the linked card.
        val jsonMeta = JSONObject()
        try {
            jsonMeta.put("id", "this-is-the-metadata-id")
            jsonMeta.put("customKey1", "customValue1")
            jsonMeta.put("customKey2", "customValue2")
        } catch (e: JSONException) {
            val exceptionMessage = e.localizedMessage
            if (exceptionMessage != null) {
                Log.e(FIDEL_DEBUG_TAG, exceptionMessage)
            }
        }
        Fidel.metaData = jsonMeta

        // Add a card linking observer to handle a successful or failing card linking result.
        Fidel.onResult = OnResultObserver { result ->
            when (result) {
                is FidelResult.Enrollment -> {
                    Log.d(FIDEL_DEBUG_TAG, "The card ID = " + result.enrollmentResult.cardId)
                }

                is FidelResult.Error -> {
                    Log.e(FIDEL_DEBUG_TAG, "Error message = " + result.error.toString())
                }

                else -> {
                    Log.d(FIDEL_DEBUG_TAG, "Other result")
                }
            }
        }
        Fidel.onMainActivityCreate(this)
    }
}
