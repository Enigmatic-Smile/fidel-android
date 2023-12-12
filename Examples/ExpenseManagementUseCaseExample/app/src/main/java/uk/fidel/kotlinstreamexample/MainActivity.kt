package uk.fidel.kotlinstreamexample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.fidelapi.Fidel
import com.fidelapi.entities.CardScheme
import com.fidelapi.entities.FidelResult
import com.fidelapi.entities.ProgramType
import com.fidelapi.entities.abstraction.OnResultObserver
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fidel.programId = "your program id"
        /**
         * For a real project, it is recommended to store the SDK key on your backend and retrieve it
         * before starting to use the SDK, after the user authenticates.
         */
        Fidel.sdkKey = "pk_test_your_sdk_key"
        Fidel.supportedCardSchemes =
            setOf(CardScheme.VISA, CardScheme.MASTERCARD)
        Fidel.companyName = "[Developer company]"
        Fidel.termsAndConditionsUrl = "https://fidel.uk"
        Fidel.privacyPolicyUrl = "https://fidel.uk"
        Fidel.deleteInstructions = "[Developer set delete instructions]"
        Fidel.programType = ProgramType.TRANSACTION_STREAM
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
        Fidel.bannerImage =
            ContextCompat.getDrawable(this, R.drawable.fidel_test_banner)?.toBitmap()

        val startEnrollmentButton = findViewById<View>(R.id.btn_link_card) as Button
        startEnrollmentButton.setOnClickListener { Fidel.start(this@MainActivity) }
        Fidel.onResult = OnResultObserver { result ->
            when (result) {
                is FidelResult.Enrollment -> {
                    Log.d(FIDEL_DEBUG_TAG, "The card ID = " + result.enrollmentResult.cardId)
                }

                is FidelResult.Verification -> {
                    Log.d(FIDEL_DEBUG_TAG, "The card ID = " + result.verificationResult.cardId)
                }

                is FidelResult.Error -> {
                    Log.e(FIDEL_DEBUG_TAG, "Error message = " + result.error.message)
                }
            }
        }

        Fidel.onMainActivityCreate(this)
    }

    private companion object {
        private const val FIDEL_DEBUG_TAG = "fidel.debug"
    }
}
