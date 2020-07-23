# FIDEL Android SDK

This SDK helps you to add card linking technology to your Android apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/a47b1852f029/Screen%252520Recording%2525202018-09-18%252520at%25252004.34%252520PM.gif)

## Install with Jitpack

[![](https://jitpack.io/v/FidelLimited/android-sdk.svg)](https://jitpack.io/#FidelLimited/android-sdk)

Add [jitpack.io](https://www.jitpack.io) to your root build.gradle at the end of repositories:

```java

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

And finally add Fidel dependency

```java
dependencies {
    implementation 'com.github.FidelLimited:android-sdk:1.4.0'
}
```

## Manual setup

You can manually download FidelSDK.aar and import it as a new .jar/.aar module with *File / New Module / Import .JAR/.AAR package* command.

And add the following dependencies:

```java
implementation 'io.card:android-sdk:5.5.1'
implementation 'com.google.android.gms:play-services-auth:18.1.0'
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
```

Then add a new Fidel SDK module to your project.

### Sample code

First, set up a programId and an apiKey (can be found in your dashboard):

```java
Fidel.programId = "your program id";
Fidel.apiKey = "your api key";
```

You can pass additional data with [org.json.JSONObject](https://stleary.github.io/JSON-java/org/json/JSONObject.html):

```java
JSONObject jsonMeta = new JSONObject();
try {
    jsonMeta.put("id", "this-is-the-metadata-id");
    jsonMeta.put("customKey1", "customValue1");
    jsonMeta.put("customKey2", "customValue2");
}
    catch(JSONException e) {
    Log.e(Fidel.FIDEL_DEBUG_TAG, e.getLocalizedMessage());
}

Fidel.metaData = jsonMeta;
```

Then, present the Fidel activity:

```java
Fidel.present(YourActivityClass.this);
```

To automatically start credit card scanning, use:

```java
Fidel.autoScan = true;
```

You can retrieve a card object, in case a card is successfully added:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
        if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
            LinkResult card = (LinkResult)data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);

            Log.d("d", "CARD ID = " + card.id);
        }
    }
}
```

You can customize the topmost banner image:

```java
Fidel.bannerImage = Bitmap(...);
```

If you don't set a banner, we'll just free up the space reserved for it on the top of screen.

For customizing the checkbox consent, please use the following APIs:

```java
Fidel.companyName = "Your Company Name Inc."; //(Maximum 60 characters);
Fidel.privacyURL = "https://yourcompany.com/privacyURL"; //(must be a valid URL)
Fidel.deleteInstructions = "Your delete instructions"; //(Maximum 60 characters);
```

The default for `companyName` is `"Company Name"`.
The default for `deleteInstructions` is `"going to your account settings"`.

#### Supported card schemes

We currently support _Visa_, _Mastercard_ and _AmericanExpress_, but you can choose to support only one, two or all three. You can do that by using `supportedCardSchemes`. Check the example below:

```java
Fidel.supportedCardSchemes = EnumSet.of(Fidel.CardScheme.AMERICAN_EXPRESS, Fidel.CardScheme.VISA, Fidel.CardScheme.MASTERCARD);
```

The default value of this property includes all three card schemes (_Visa_, _Mastercard_ and _AmericanExpress_)

#### Default country

To set a default country for the SDK you should use:

```java
Fidel.country = Fidel.Country.UNITED_KINGDOM;
```

When you set a default country, the card linking screen will not show the country picker UI. The other options, for now, are: `.UNITED_STATES`, `.IRELAND`, `.SWEDEN`, `.JAPAN`, `.CANADA`.

## Consent text for United States and Canada

When you set United States or Canada as the default country **or** don't set a default country (meaning that the user is free to select United States or Canada as their country), a different consent text will be applied. In addition to the parameters described above, you can set the following parameters in this consent text:

```java
Fidel.programName = "your program name"; // (Maximum 60 characters);
Fidel.termsConditionsURL = "https://yourcompany.com/termsConditionsURL"; // (must be a valid URL)
```

The default value for `programName` is `"our"` (in English; for other languages, the words adjust to make sense). The `termsConditionsURL` is mandatory in this case.
If you don't set a privacy policy URL (which is different than the terms & conditions URL), the corresponding wording will not be displayed.

For USA & Canada, the following would be an example Terms & Conditions text, for Cashback Inc (an example company name):

*By submitting your card information and checking this box, you authorize Visa to monitor and share transaction data with Fidel (our service provider) to participate in  program. You also acknowledge and agree that Fidel may share certain details of your qualifying transactions with Cashback Inc to enable your participation in  program and for other purposes in accordance with the Cashback Inc Terms and Conditions, Cashback Inc privacy policy and Fidel’s Privacy Policy. You may opt-out of transaction monitoring on the linked card at any time by contacting support.*

For the rest of the world:

*I authorise Visa to monitor my payment card to identify transactions that qualify for a reward and for Visa to share such information with Cashback Inc, to enable my card linked offers and target offers that may be of interest to me. For information about Cashback Inc privacy practices, please see the privacy policy. You may opt-out of transaction monitoring on the payment card you entered at any time by contacting support.*

## Localisation

The SDK's default language is English, but it's also localised for French and Swedish languages. When the device has either `Français (Canada)` or `Svenska (Sverige)` as its language, the appropriate texts will be displayed. Please note that developer error messages are in English only and they will not be displayed to the user.

## Documentation

In the test environment please use our VISA, Mastercard or American Express test card numbers:

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

American Express: _3400000000003**_ or _3700000000003**_ (the last 2 numbers can be anything)

#### Possible errors

If you configured Fidel correctly, you will not receive errors after presenting the Card Linking activity. However, we respond with some suggestive errors in case something goes wrong. Please make sure that you test the integration manually as well. It's best to make sure that you configured everything correctly in your app.

In case something is not configured correctly, after attempting to present the Fidel card linking activity, you can also subscribe for checking for any errors:

```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
super.onActivityResult(requestCode, resultCode, data);
	if(requestCode == Fidel.FIDEL_LINK_CARD_REQUEST_CODE) {
		if(data != null && data.hasExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD)) {
			LinkResult linkResult = data.getParcelableExtra(Fidel.FIDEL_LINK_CARD_RESULT_CARD);
			LinkResultError error = linkResult.getError();
			if (error != null) {
			    Log.e("Fidel Error", "error message = " + error.message);
			}
		}
	}
}
```

In case an error is encountered we send a `LinkResultError` object with the `LinkResult` object. Retrieve the `LinkResultError` object by calling `linkResult.getError()` as demonstrated above.

#### The `LinkResultError` object

The `LinkResultError` object has the `message` and `errorCode` properties which might be useful for you. The `errorCode` property has the following codes:

```java
public enum LinkResultErrorCode {
    USER_CANCELED,
    INVALID_URL,
    STRING_OVER_THE_LIMIT,
    MISSING_MANDATORY_INFO
}
```

- `USER_CANCELED` - Sometimes it's useful to know if the user canceled the card linking process so please check for this error, if that's the case.
- `INVALID_URL` - If you provide an invalid `Fidel.privacyURL`, you will receive this error. Please make sure your URL matches the `Patterns.WEB_URL` pattern.
- `STRING_OVER_THE_LIMIT` - We send this error in case your `Fidel.deleteInstructions` or `Fidel.companyName` exceed *60* characters.
- `MISSING_MANDATORY_INFO` - Some of the mandatory information necessary to configure the SDK were not provided. The following are the mandatory info you need to provide:
	1. `Fidel.apiKey`
	2. `Fidel.programId`
	3. `Fidel.supportedCardSchemes`. The default value includes _Visa_, _Mastercard_ and _AmericanExpress_, but if you set this property to `null` or to an empty set, you'll receive the `MISSING_MANDATORY_INFO` error.

## Feedback

The FIDEL SDK is in active development, we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback

Fidel Developers Forum - [https://community.fidel.uk](https://community.fidel.uk) - for personal support at any phase of integration
