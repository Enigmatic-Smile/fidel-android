# FIDEL Android SDK

This SDK helps you to add card linking technology to your Android apps in minutes. It captures credit/debit card numbers securely and links them to your programs.

![Demo GIF](https://cl.ly/a47b1852f029/Screen%252520Recording%2525202018-09-18%252520at%25252004.34%252520PM.gif)

## Installation

### Install with Jitpack

Add [jitpack.io](https://www.jitpack.io) to your root build.gradle at the end of repositories:

```java

allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

In your app/build.gradle file, add Fidel dependency

```java
dependencies {
    implementation 'com.github.FidelLimited:android-sdk:1.7.1'
}
```

## Manual setup

You can manually download FidelSDK.aar and import it as a new .jar/.aar module with *File / New Module / Import .JAR/.AAR package* command.

### Dependencies

Regardless of whether you install using JitPack or choose the manual installation, you must add the following dependencies to your app/build.gradle file:

```java
implementation 'androidx.legacy:legacy-support-v4:1.0.0'
implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
implementation 'io.card:android-sdk:5.5.1'
implementation 'com.google.android.gms:play-services-auth:19.0.0'
implementation 'androidx.core:core-ktx:1.6.0'
```

Then add a new Fidel SDK module to your project.

## Sample code

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


## Options documentation

### bannerImage

Use this option to customize the topmost banner image with the Fidel UI. If you don't set a banner, we'll just free up the space reserved for it on the top of screen.

```java
Fidel.bannerImage = Bitmap(...);
```

### allowedCountries

To set the countries that the users can select, use

```java
Fidel.allowedCountries = new Fidel.Country[]{Fidel.Country.UNITED_KINGDOM, Fidel.Country.JAPAN, Fidel.Country.CANADA};
```

The possible options are: `.CANADA`, `.IRELAND`, `.JAPAN`, `.SWEDEN`, `.UNITED_ARAB_EMIRATES`, `.UNITED_KINGDOM`, `.UNITED_STATES`. You can set one or multiple of these countries. 
If you don't set any allowed countries, the user will be able to choose any of the countries above.
If you set only one country, the card linking screen will not show the country picker UI.
Note that, when you set multiple countries, they will be displayed in the country picker UI in the order that you set them.

### defaultSelectedCountry

Use this parameter to set the country that will be selected by default when opening the card enrollment screen.

```java
Fidel.defaultSelectedCountry = Fidel.Country.UNITED_KINGDOM;
```

The possible options are: `.CANADA`, `.IRELAND`, `.JAPAN`, `.SWEDEN`, `.UNITED_ARAB_EMIRATES`, `.UNITED_KINGDOM`, `.UNITED_STATES`. The `defaultSelectedCountry` has to be part of the `allowedCountries` list.

The default value of this option is `Fidel.Country.UNITED_KINGDOM`.

### supportedCardSchemes
We currently support _Visa_, _Mastercard_ and _AmericanExpress_, but you can choose to support only one, two or all three. You can do that by using `supportedCardSchemes`. Check the example below:

```java
Fidel.supportedCardSchemes = EnumSet.of(Fidel.CardScheme.AMERICAN_EXPRESS, Fidel.CardScheme.VISA, Fidel.CardScheme.MASTERCARD);
```
The default value of this property includes all three card schemes (_Visa_, _Mastercard_ and _AmericanExpress_)

### autoScan

Set this property to `true`, if you want to open the card scanning UI immediately after executing `Fidel.present`. The default value is `false`.

```java
Fidel.autoScan = true;
```

### metaData

Use this option to pass any other data with the card data:

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

### companyName

Set your company name as it will appear in our consent checkbox text. Please set it to a maximum of 60 characters.

```java
Fidel.companyName = "Your Company Name Inc."; //(Maximum 60 characters);
```

The default for `companyName` is `"Company Name"`.

### deleteInstructions

Write your custom opt-out instructions for your users. They will be displayed in the consent checkbox text as well.

```java
Fidel.deleteInstructions = "Your delete instructions"; //(Maximum 60 characters);
```

The default for `deleteInstructions` is `"going to your account settings"`.

### privacyURL

This is the privacy policy URL that you can set for the consent checkbox text.

```java
Fidel.privacyURL = "https://yourcompany.com/privacyURL"; //(must be a valid URL)
```

### programName (applied to the consent text only for USA and Canada)

Set your program name as it will appear in the consent text. Note that **this parameter is optional** and used when you set United States and/or Canada as allowed countries or don't set any allowed countries (meaning that the user is free to select United States or Canada as their country). Please set it to a maximum of 60 characters.

```java
Fidel.programName = "your program name"; //(Maximum 60 characters);
```

### termsConditionsURL (applied to the consent text only for USA and Canada)

This is the terms & conditions URL that you can set for the consent text. Note that **this parameter is mandatory** when you set United States and/or Canada as allowed countries or don't set any allowed countries (meaning that the user is free to select United States or Canada as their country)

```java
Fidel.termsConditionsURL = "https://yourcompany.com/termsConditionsURL"; //(Maximum 60 characters);
```

## Customizing the consent text

In order to properly set the consent text, please follow these steps:

1. **Set the company name**

This parameter is optional, but we recommended setting it. If you don't set a company name, we'll show the default value in the consent text: ```Your Company Name```

2. **Set the privacy policy URL**

This is an optional parameter. It is added as a hyperlink to the ```privacy policy``` text. Please see the full behaviour below.

3. **Set the delete instructions**

Optional parameter whose default value is ```going to your account settings```. This default value is applied for both consent texts - for the USA & Canada as well as for the rest of the world.

4. **Set the card scheme name**

By default, we allow the user to input card numbers from either Visa, Mastercard or American Express, but you can control which card networks you accept. The consent text changes based on what you define or based on what the user inputs. Please see the full behaviour below.

5. **Set the program name (applied to the consent text only for USA and Canada)**

This parameter is taken into account only for USA and Canada. The default value for program name is ```our```. 

6. **Set the terms and conditions URL (applied to the consent text only for USA and Canada)**

This parameter is mandatory for USA and Canada. Once set, it will be applied as a hyperlink on the ```Terms and Conditions``` text.


Note that the consent text has a different form depending on the allowed countries you set or the country the user can select. Below you can find the specifics for each case.

### Consent text for United States and Canada

When you set United States and/or Canada as allowed countries or don't set any countries (meaning that the user is free to select United States or Canada as their country), a different consent text will be applied.

For USA & Canada, the following would be an example Terms & Conditions text for ```Cashback Inc``` (an example company) that uses ```Awesome Bonus``` as their program name:

*By submitting your card information and checking this box, you authorize ```card_scheme_name``` to monitor and share transaction data with Fidel (our service provider) to participate in ```Awesome Bonus``` program. You also acknowledge and agree that Fidel may share certain details of your qualifying transactions with ```Cashback Inc``` to enable your participation in ```Awesome Bonus``` program and for other purposes in accordance with the ```Cashback Inc``` Terms and Conditions, ```Cashback Inc``` privacy policy and Fidel’s Privacy Policy. You may opt-out of transaction monitoring on the linked card at any time by ```deleteInstructions```.*

There are two specific parameters that you can set for this consent text:

#### 1. termsConditionsURL
This parameter is mandatory when you set United States and/or Canada as allowed countries or don't set any countries (meaning that the user is free to select United States or Canada as their country). When you set this parameter, the ```Terms and Conditions``` from the consent text will get a hyperlink with the URL you set.

```java
Fidel.termsConditionsURL = "https://yourcompany.com/termsConditionsURL";
```

If you don't set this parameter, you'll get an error when trying to open the card linking interface: ```You have included a North American country in the list of allowed countries or you allow the user to select a North American country. For North American countries it is mandatory for you to provide the Terms and Conditions URL.```

#### 2. programName
This parameter is optional when you set United States and/or Canada as allowed countries or don't set any countries. If you don't set a program name, we'll use ```our``` as the default value (for example, in the text above, you would see *...to monitor and share transaction data with Fidel (our service provider) to participate in ```our``` program...*)

```java
Fidel.programName = "your program name";
```

#### Consent text behaviour for card scheme name

If you don't set a card scheme (meaning the user can input either Visa, Mastercard or American Express cards) *OR* set 2 or 3 card scheme names, the default value used will be ```your payment card network``` (e.g. _you authorize ```your payment card network``` to monitor and share transaction data with Fidel (our service provider)_). When the user starts typing in a card number, ```your payment card network``` will be replaced with the scheme name of the card that they typed in (e.g. Visa).

If you set one card scheme name, it will be displayed in the consent text (e.g. for Mastercard it would be _you authorize ```Mastercard``` to monitor and share transaction data with Fidel (our service provider)_) This value - ```Mastercard``` - will not change when the user starts typing in a card number.

#### Consent text behaviour for privacy policy

Notice the following excerpt from the consent text above: _in accordance with the ```Cashback Inc``` Terms and Conditions, ```Cashback Inc``` privacy policy and Fidel’s Privacy Policy._ If you set a ```privacyURL```, this is the text that will be displayed, along with a hyperlink set on *privacy policy*.

If you do not set a ```privacyURL```, the text will become _in accordance with the ```Cashback Inc``` Terms and Conditions and Fidel’s Privacy Policy._

### Consent text for the rest of the world

When you set Ireland, Japan, Sweden, United Arab Emirates and/or United Kingdom as allowed countries or the user is able to select one of these countries from the list, a consent text specific for these countries will be applied.

The following would be an example Terms & Conditions text for ```Cashback Inc``` (an example company):

*I authorise ```card_scheme_name``` to monitor my payment card to identify transactions that qualify for a reward and for ```card_scheme_name``` to share such information with ```Cashback Inc```, to enable my card linked offers and target offers that may be of interest to me. For information about ```Cashback Inc``` privacy practices, please see the privacy policy. You may opt-out of transaction monitoring on the payment card you entered at any time by ```deleteInstructions```.*

#### Consent text behaviour for card scheme name

If you don't set a card scheme (meaning the user can input either Visa, Mastercard or American Express cards) *OR* set 2 or 3 card scheme names, the default value used will be ```my card network``` (e.g. _I authorise ```my card network``` to monitor my payment card_). When the user starts typing in a card number, ```my card network``` will be replaced with the scheme name of the card that they typed in (e.g. Visa).

If you set one card scheme name, it will be displayed in the consent text (e.g. for Mastercard it would be _I authorise ```Mastercard``` to monitor my payment card_) This value - ```Mastercard``` - will not change when the user starts typing in a card number.

#### Consent text behaviour for privacy policy

If you do not set a privacy policy URL, the privacy policy related phrase will be removed from the text.

Notice the following excerpt from the consent text above: _...may be of interest to me. For information about ```Cashback Inc``` privacy practices, please see the privacy policy. You may opt-out of..._ If you set a ```privacyURL```, this is the text that will be displayed, along with a hyperlink set on *privacy policy*.

If you do not set a ```privacyURL```, the text will become _...may be of interest to me. You may opt-out of..._

## Localisation

The SDK's default language is English, but it's also localised for French and Swedish languages. When the device has either `Français (Canada)` or `Svenska (Sverige)` as its language, the appropriate texts will be displayed. Please note that developer error messages are in English only and they will not be displayed to the user.

## Test card numbers

In the test environment please use our VISA, Mastercard or American Express test card numbers:

VISA: _4444000000004***_ (the last 3 numbers can be anything)

Mastercard: _5555000000005***_ (the last 3 numbers can be anything)

American Express: _3400000000003**_ or _3700000000003**_ (the last 2 numbers can be anything)


### Possible errors

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
- `INVALID_URL` - If you provide an invalid `Fidel.privacyURL` or `Fidel.termsConditionsURL`, you will receive this error. Please make sure your URL matches the `Patterns.WEB_URL` pattern.
- `STRING_OVER_THE_LIMIT` - We send this error in case your `Fidel.deleteInstructions` or `Fidel.companyName` or `Fidel.programName` exceed *60* characters.
- `MISSING_MANDATORY_INFO` - Some of the mandatory information necessary to configure the SDK were not provided. The following are the mandatory info you need to provide:
	1. `Fidel.apiKey`
	2. `Fidel.programId`
	3. `Fidel.supportedCardSchemes`. The default value includes _Visa_, _Mastercard_ and _AmericanExpress_, but if you set this property to `null` or to an empty set, you'll receive the `MISSING_MANDATORY_INFO` error.
	4. `Fidel.termsConditionsURL`. This is mandatory when you set United States and/or Canada as allowed countries **or** don't set any allowed countries (meaning that the user is free to select United States or Canada as their country). 
    5. `Fidel.allowedCountries`. The default value includes _CANADA_ _IRELAND_, _JAPAN_, _SWEDEN_, _UNITED_ARAB_EMIRATES_, _UNITED_KINGDOM_ and _UNITED_STATES_, but if you set this property to `null` or to an empty array you'll receive the `MISSING_MANDATORY_INFO` error.

## Feedback

The FIDEL SDK is in active development, we welcome your feedback!

Get in touch:

GitHub Issues - For SDK issues and feedback

Fidel Developers Forum - [https://community.fidel.uk](https://community.fidel.uk) - for personal support at any phase of integration
