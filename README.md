# 3d-passgo-library
An android library providing core functions of 3D PassGo, a 3D graphical password scheme. (passgo.net)

This library includes:

* PassGoView_Login - A View which can be embed into a login activity. It performs the core functions of 3d passgo:
  * showing a 3d grid
  * receiving user input
  * showing passgo as entered
  * comparing entered passgo with the passgo stored in SharedPreference
* ChangePassGoActivity - An activity which allows a user to set up or change his/her passgo by saving the passgo to SharedPreferences.
* AuthenticateActivity - An activity which requires a user to enter his/her current passgo first, and then call ChangePassGoActivity.java only if success. (a more secure way)
* PassGoView_Style - An activity where a user can customize passgo styles, such as color, thickness etc.
* PassGoFAQActivity - A FAQ activity providing common questions/answers regarding passgo.

With above compoments, android developers should be able to embed "3D PassGo" into an andorid app easily as another option for user authentication. 

For details regarding how to use this library, please refer to the wiki page.

![](https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_login.jpg)     ![](https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_style.jpg)     ![](https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_gridsize.jpg)  
