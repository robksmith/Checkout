## Introduction

This simple Android demo app implements Secure3D payments for Checkout.com

## Running and Building

This app was built with Android Studio version 3.5.3

## Overview

The app uses the Android architectural components (Navigation, ViewModel and Livedata) to put together a simple flow for the user to make a card payment. 

It is basically made up of one activity with 4 fragments as shown here:

1. **Input Fragment** => Solicits card details

2. **Card Payment Fragment** => Makes the POST API call

3. **Secure 3D Fragment** => Webview with the Secure3D url inside it which is returned from the POST call

4. **Result Fragment** => Shows the result of the payment operation (a tick or a cross)

## Note / to improve

* The card control and expiry date control are very basic and need improving - for example, card scheme detection could automatically be done to display the card icon.
* There is not much in the way of error checking - there is none on cvv for example and more could be done on card number and expiry date
* Strings are hardcoded
* Comments are lacking
* Regular expressions have not been tested fully - mainly for the sake of this small demo, take from a RegEx site
* Exceptions not handled correctly
* Views are basic and would need improving upon
* Loads more could be added / improved upon

## Testing

There are a few demo integration tests included using the FragmentScenario class and Espresso and Mockito. Although loads more tests could be written.  

## Other libraries used

* RxJava
* Dagger2
* Retrofit


