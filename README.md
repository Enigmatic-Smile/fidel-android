# FIDEL Android SDK

This SDK helps you to add card linking technology to your Android apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/a47b1852f029/Screen%252520Recording%2525202018-09-18%252520at%25252004.34%252520PM.gif)

### Install with Jitpack
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
    compile 'com.github.FidelLimited:android-sdk:1.2.1'
}
```

### Manual setup
You can manually download FidelSDK.aar and import it as a new .jar/.aar module with *File / New Module / Import .JAR/.AAR package* command.

And add the following dependencies:

```java
implementation 'io.card:android-sdk:5.5.1'
implementation 'com.google.android.gms:play-services-auth:16.0.0'
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

#### Default country

Set a default country the SDK should use with 
```java
Fidel.country = Fidel.Country.UNITED_KINGDOM;
```
When you set a default country, the card linking screen will not show the country picker UI. The other options, for now, are: `.UNITED_STATES`, `.IRELAND`, `.SWEDEN`, `.JAPAN`.

### Documentation

In test environment use our VISA and Mastercard test card numbers:

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

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

### Feedback

The FIDEL SDK is in active development, we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback
FIDEL Developers Slack Channel - [https://fidel-developers-slack-invites.herokuapp.com](https://fidel-developers-slack-invites.herokuapp.com) - for personal support at any phase of integration
