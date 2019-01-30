## 1.2.3
- Added the Canada country option.
- Remove `allowBackup` and `supportsRtl` flags from the SDK's `AndroidManifest.xml` file to allow you to choose to set them as you'd like.

## 1.2.2
- Added support for linking American Express cards

## 1.2.1
- Added the Japan country option.
- Hidden the PayPal logo in the card scanning UI.
- Disabled CardIO manual card details entry forms.
- Improved testing mode user experience and the overall UX.
- Allow the user to select the consent checkbox, even before filling in any information.
- Tapping anywhere on the screen dismisses the keyboard.
- If you don't set a banner image, we'll hide the top space reserved for it.

## 1.2.0
- Added the Sweden country option
- Returns an error, when the SDK encounters it, with the `LinkResult`. Get it with `linkResult.getError()` getter.
- Now you can customize the final consent text with the following API:

    `Fidel.companyName = "Your Company Name Inc.";` (Maximum 60 characters)
    
    `Fidel.privacyURL = "https://yourcompany.com/privacyURL";` (must be a valid URL)
    
    `Fidel.deleteInstructions = "Your delete instructions";` (Maximum 60 characters)
    
- If the data above is not valid, the UI will not be displayed and you will get an error with the `LinkResult` in the activity result.
- Set a default country the SDK should use with `Fidel.country = Fidel.Country.UNITED_KINGDOM`. When you set a default country, the card linking screen will not show the country picker UI.
- Add support for more test cards. Anything with the following format:

    Visa: _4444000000004***_
    
    Mastercard: _5555000000005***_


## 1.1.0
- Added the United States country option
- Updated gradle and build tools
- Removed the 'com.afollestad.material-dialogs:core:0.9.4.5' dependency
- Removed the 'com.koushikdutta.ion:ion:2.+' dependency
- Doesn't use the Gson library anymore for specifying meta data. Please use the `org.json.JSONObject` object instead of `com.google.gson.JsonObject`
- More protection against SSL exploits by using Google Play Services. When using the library on an emulator, make sure to use a emulator that has the Google Play Services.
- Updated `appcompat-v7` library to version `26.1.0`
- Support the use of all test card numbers. Previously some errors occured while using some of the test cards.
- Updated checkbox consent text
- Improved error message when the user tries to link a card that's already linked.
- Made only the color resources used by the SDK public. The rest of the resources are private now. This will prevent future resource conflicts.
- Added an error color resource, which you can adjust. It's called `colorError`.