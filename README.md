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
    compile 'com.github.FidelLimited:android-sdk:1.2.0'
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
When you set a default country, the card linking screen will not show the country picker UI. The other options, for now, are: `.UNITED_STATES`, `.IRELAND`, `.SWEDEN`.

### Documentation

In test environment use our VISA and Mastercard test card numbers:

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

### Feedback

The FIDEL SDK is in active development, we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback
FIDEL Developers Slack Channel - [https://fidel-developers-slack-invites.herokuapp.com](https://fidel-developers-slack-invites.herokuapp.com) - for personal support at any phase of integration
