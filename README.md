# 3d-passgo-library
An android library providing core functions of 3D PassGo.

3D PassGo is a password scheme in which a user enters a passgo, including lines and dots, on a 3d grid as a password. The size of the 3d grid can be configured by a user, from 1x1x1 to 19x19x19. The origin and details of 3D PassGo can be found at [passgo.net](http://passgo.net).

This library includes:

* PassGoView_Login - A view which can be embedded into a login activity. It performs the core functions of 3D PassGo:
  * show a 3d grid
  * receive user input
  * allow a user to rotate the 3d grid
  * allow a user to zoom the 3d grid in & out
  * allow a user to move the 3d grid around
  * show passgo as entered
  * compare passgo entered with the passgo stored in SharedPreference
* PassGoStyleActivity - An activity where a user can customize passgo styles, including
  * if show or hide passgo
  * if show or hide the 3d grid lines
  * if show or hide the circles around the 3d grid intersections
  * PassGo line color & thickness
  * PassGo dot color & size
  * grid line color & thickness
  * grid circle color & thickness
  * background color of the view
* ChangePassGoActivity - An activity which allows a user to set up or change his/her passgo by saving the passgo to SharedPreferences.
* AuthenticateActivity - An activity which requires a user to enter his/her current passgo first, and then call ChangePassGoActivity.java only if success. (a more secure way)
* PassGoFAQActivity - A FAQ activity providing common questions/answers regarding passgo.

With above compoments, android developers should be able to embed "3D PassGo" into an andorid app easily. 

More info is available at [wiki](https://github.com/3d-passgo/3d-passgo-library-android/wiki).
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
</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_1.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_2.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_3.jpg">
</p>

