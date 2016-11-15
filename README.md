# 3d-passgo-library
An android library providing core functions of 3D PassGo, a 3D graphical password scheme. (passgo.net)

This library includes:
* ChangePassGoActivity.java - An activity which allows a user to set up or change his/her passgo, and save the passgo to SharedPreferences.
* AuthenticateActivity.java - An activity which require a user to enter his/her current passgo, and call ChangePassGoActivity.java if success.
* PassGoView_Style.java - An activity where a user can customize passgo styles, such as color, thickness etc.
* PassGoView_Login - A View which can be embed into a login activity. It performs the core functions of 3d passgo: showing a 3d grid, receiving user input, showing passgo as entered, comparing entered passgo with stored passgo, and so on. For example, it is embeded into a Lock Screen App in this way:

                    <net.passgo.passgo3d.PassGoView_Login
                        android:id="@+id/System_Lockscreen_PassGoView_Login"
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:layout_alignParentBottom="true"
                        android:layout_centerHorizontal="true"
                        android:layout_marginBottom="0.0dip" />

For details regarding how to use this library, please refer to the wiki page.

