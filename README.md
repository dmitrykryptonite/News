# News
Android sample project with REST using Clean Architecture/VIPER, MVP, Retrofit, Moxy, RxJava, SQLite, MaterialDesign.

In this project I used RecyclerView, SharedPreferences, Singleton pattern etc

So, this is a project with the ability of displaying, searching, adding to favorites and deleting from favorites. The news can be shared and opened in a browser. News is loaded by
ip (geolocation) of the phone. The language in the application depends on the system settings of the phone.

So, Home screen:

<img src="https://github.com/dmitrykryptonite/News/blob/master/1.jpg" width="250" height="411" alt="Home screen" />

Let's open the news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/2.jpg" width="250" height="411" alt="Opened news" />

Let's click on star (to add to favorite):

<img src="https://github.com/dmitrykryptonite/News/blob/master/3.jpg" width="250" height="411" alt="Permission" />

Click allow:

<img src="https://github.com/dmitrykryptonite/News/blob/master/4.jpg" width="250" height="411" alt="News added to favorites" />

Our news added to favorites.

Let's checking:

<img src="https://github.com/dmitrykryptonite/News/blob/master/5.jpg" width="250" height="411" alt="Checking news in favorites" />

Our news in list favorites.

Open it:

<img src="https://github.com/dmitrykryptonite/News/blob/master/6.jpg" width="250" height="411" alt="Opening favorite news" />

Works fine.
Remove news from favorites and check if it will be displayed as favorites in the list of actual news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/7.jpg" width="250" height="411" alt="News removed from favorites" /> <img
src="https://github.com/dmitrykryptonite/News/blob/master/8.jpg" width="250" height="411" alt="News is not favorite" />

Works fine.

Let's again added news to favorite and remove it from this window.

<img src="https://github.com/dmitrykryptonite/News/blob/master/9.jpg" width="250" height="411" alt="News added to favorites" /> <img
src="https://github.com/dmitrykryptonite/News/blob/master/10.jpg" width="250" height="411" alt="News removed from favorites" />

Let's check search news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/11.jpg" width="250" height="411" alt="Start searching" /> <img
src="https://github.com/dmitrykryptonite/News/blob/master/12.jpg" width="250" height="411" alt="Query search" /> <img
src="https://github.com/dmitrykryptonite/News/blob/master/13.jpg" width="250" height="411" alt="Finish search" />

Works fine.
Click back. Home:

<img src="https://github.com/dmitrykryptonite/News/blob/master/14.jpg" width="250" height="411" alt="Home screen" />

Then disconnect internet connection :

<img src="https://github.com/dmitrykryptonite/News/blob/master/15.jpg" width="250" height="411" alt="Disconnect internet connection" />

The news hasn't disappeared. Detail displaying of the news is possible, because it was loaded earlier. But when we click back (open list actual news):

<img src="https://github.com/dmitrykryptonite/News/blob/master/16.jpg" width="250" height="411" alt="No internet connection" />

Let's search news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/17.jpg" width="250" height="411" alt="No internet connection after search" />

Works fine. Click back to open home screen:

<img src="https://github.com/dmitrykryptonite/News/blob/master/18.jpg" width="250" height="411" alt="No internet connection after open home" />

Without connecting to the Internet, open favorite news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/19.jpg" width="250" height="411" alt="List favorite news without connecting to the Internet" />

Let's open favorite news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/20.jpg" width="250" height="411" alt="Detail displaying news without connecting to the Internet" />

Works fine.

What if we first click on "prevent saving files on the device"?

Let's check:) 

<img src="https://github.com/dmitrykryptonite/News/blob/master/21.jpg" width="250" height="411" alt="Deny permissions" /> <img
src="https://github.com/dmitrykryptonite/News/blob/master/22.jpg" width="250" height="411" alt="News added to favorite" />

News added to favorites, but image not displaying:

<img src="https://github.com/dmitrykryptonite/News/blob/master/23.jpg" width="250" height="411" alt="No image on favorite news" />

Let's fix it. Click on the news:

<img src="https://github.com/dmitrykryptonite/News/blob/master/24.jpg" width="250" height="411" alt="No image on favorite news in detail screen" />

Click on "Tap to change":

<img src="https://github.com/dmitrykryptonite/News/blob/master/25.jpg" width="250" height="411" alt="Settings" />

Click on permissions, then click on permission to allow:

<img src="https://github.com/dmitrykryptonite/News/blob/master/26.jpg" width="250" height="411" alt="Permissions" />

Let's check:

<img src="https://github.com/dmitrykryptonite/News/blob/master/27.jpg" width="250" height="411" alt="Fixed in list news" />

Fixed:) Check detail:

<img src="https://github.com/dmitrykryptonite/News/blob/master/28.jpg" width="250" height="411" alt="Fixed in detail" />

Excellent:)
