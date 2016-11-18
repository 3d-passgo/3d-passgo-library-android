# 3d-passgo-library
An android library providing core functions of 3D PassGo, a 3D graphical password scheme. ([passgo.net](http://passgo.net))

This library includes:

* PassGoView_Login - A view which can be embedded into a login activity. It performs the core functions of 3D PassGo:
  * show a 3d grid
  * receive user input
  * allow a user to rotate the 3d grid
  * allow a user to zoom 3d grid in & out
  * allow a user to move the 3d grid around
  * show passgo as entered
  * compare entered passgo with the passgo stored in SharedPreference
* PassGoStyleActivity - An activity where a user can customize passgo styles, such as color, thickness etc.
* ChangePassGoActivity - An activity which allows a user to set up or change his/her passgo by saving the passgo to SharedPreferences.
* AuthenticateActivity - An activity which requires a user to enter his/her current passgo first, and then call ChangePassGoActivity.java only if success. (a more secure way)
* PassGoFAQActivity - A FAQ activity providing common questions/answers regarding passgo.

With above compoments, android developers should be able to embed "3D PassGo" into an andorid app easily. 

How to is available at [wiki](https://github.com/3d-passgo/3d-passgo-library-android/wiki).
<br>
<br>

<p align="center">
Login
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Style
</p>

<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_login.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_style.jpg">
</p>


<br>
<p align="center">
Change PassGo
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
FAQ
</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_gridsize.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_faq.jpg">
</p>

