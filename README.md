# Nasa
We are going to use nasa free api in this project
## Objective:
Use the Astronomy photo of the day(APOD) API of Nasa and cover the following use cases:
1. Given: The NASA APOD API is up (working) AND the phone is connected to the internet When:
   The user arrives at the APOD page for the first time today Then: The page should display the
   image of Astronomy Picture of the Day along with the title and explanation, for that day
2. Given: The user has already seen the APOD page once AND the phone is not connected to
   the internet When: The user arrives at the APOD page on the same day Then: The page
   should display the image of Astronomy Picture of the Day along with the title and explanation,
   for that day
3. Given: The user has not seen the APOD page today AND the phone is not connected to the
   internet When: The user arrives at the APOD page Then: The page should display an error
   "We are not connected to the internet, showing you the last image we have." AND The page
   should display the image of Astronomy Picture of the Day along with the title and explanation,
   that was last seen by the user
4. Given: The NASA APOD API is up (working) AND the phone is connected to the internet When:
   The APOD image loads fully on the screen Then: The user should be able to see the complete
   image without distortion or clipping
   
## How to test with the APK:
The APK is uploaded at root level with file name "app-debug.apk' so anyone can push or install the apk 
in emulator or physical device and can run the app.
run command:
1. adb install '**bold filepath **' where the apk downloaded
2. Or direct download it in the device and allow install from untrusted source and click on install

   
## Project architecture design:
 MVVM with repository pattern.
 Language: Kotlin
 Network call: using HTTPUrlConnection
 Persistence: Shared Preference and Internal storage
 Asynchronous behavior: Coroutines
 DataBinding used: yes

### Improvement areas:
1. Views can be bind in xml files itself so that activity can be more clean
2. Repository class can be more clean by taking the decision that how to get data from persistence or 
    from the network.
    
 
### TradeOffs:
1. Used shared preference for saving the date, explanation and title due to less amount of time.
    but i think its good if we only need one image's primitive details to save.
   
2. Used Internal storage for image saving and fetching if user is not connected to the internet
    and made put the file name as date. I hope its a good idea to use internal storage just 
   because the storage will be private to app and easy to access.
   


   

