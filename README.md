# FIDEL Android SDK

### Setup

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
    compile 'com.github.FidelLimited:android-sdk:1.1.0'
}
```

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

You can pass additional data with [com.google.gson.JsonObject](https://static.javadoc.io/com.google.code.gson/gson/2.6.2/com/google/gson/JsonObject.html):

```java
JsonObject jsonMeta = new JsonObject();

jsonMeta.addProperty("id", "this-is-the-metadata-id");
jsonMeta.addProperty("customKey1", "customValue1");
jsonMeta.addProperty("customKey2", "customValue2");
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

[![](https://jitpack.io/v/FidelLimited/android-sdk.svg)](https://jitpack.io/#FidelLimited/android-sdk)
