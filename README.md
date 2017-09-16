# 3d-passgo-library
An android library providing core functions of 3D PassGo.

3D PassGo is a password scheme derived from Go game, and is believed to have significant stronger security and better usability than tranditional passwords, especially on touch screens. In 3D PassGo, a user draws lines and dots on a 3d grid as a password. The size of the 3d grid is adjustable, from 1x1x1 to 19x19x19, which makes it easy to fine tune the security level. The 3d grid can also be rotated, zoomed, and moved in the 3d space, which brings a very cool 3D user experience.

This library includes:

* PassGoView_Login - A view which can be embedded into a login activity. It performs the core functions of 3D PassGo:
  * show a 3d grid
  * receive user input
  * generate passgo encoding as a string
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
* AuthenticateActivity - An activity which requires a user to authenticate with passgo first, and only if success, then call ChangePassGoActivity.java to allow the user to change his/her passgo
* PassGoFAQActivity - A FAQ activity providing common questions/answers regarding 3D PassGo.

With above compoments, android developers should be able to embed 3D PassGo into an andorid app easily as a password alternative, and then have their users to enjoy the security and usablilty of 3D PassGo. 

More info is available at [wiki](https://github.com/3d-passgo/3d-passgo-library-android/wiki).
<br>
<br>

<p align="center">Login</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/login_1.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/login_2.jpg">
</p>

<br>

<p align="center">Style</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/style_1.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/style_2.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/style_3.jpg">
</p>

<br>

<p align="center">Change PassGo</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_1.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_2.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/p_change_3.jpg">
</p>

<br>

<p align="center">FAQ</p>
<p align="center">
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/faq_1.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/faq_2.jpg">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<img src="https://github.com/3d-passgo/3d-passgo-library-android/blob/master/images/faq_3.jpg">
</p>


