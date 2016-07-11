Too Human Skill Calculator by Imran Merchant
Current Version: 4.0
Contact: imerchant@gmail.com
http://pyreflies.nu/hench/TooHuman

Java program designed to simulate the skill and alignment trees from the
Xbox 360 video game Too Human, developed by Silicon Knights and published
by Microsoft. They retain all their trademarks and copyrights to it.

Can save the trees either as a file to open in the plotter, or as a PNG
image.

REQUIRES JAVA RUNTIME ENVIRONMENT. Latest (1.6) is recommended, but will
probably work in 1.5. The program will run on any system with Java
installed, regardless of operating system.

To run the program, save the JAR (TooHumanCalc.jar) it will run like an
executable. If Java is installed correctly the program should run just fine.
Make sure to save the file as .jar; do not decompress/unzip the archive.

Please report any bugs you encounter.

================================
Note to Internet Explorer users:
================================
When saving the plotter (TooHumanCalc.jar), save the file as a .jar. IE
sometimes wants to change the filetype to .zip or something else. THE
PROGRAM WILL NOT WORK IN THIS CASE. The filetype must be .jar.

==========
Changelog:
==========

Version 4.0
* New skill, class, and alignment icons courtesy of SK.net member Axeman87!
  This includes proper Human ruiner icons for all the classes. The new icons are
  a lot more detailed, with raised metal effects and shine. In the process, some
  incorrect or slightly wrong icons are fixed!
* Export now includes the (visible) notes field and information about how many
  points are used in the displayed tree.
* Skill icons now grey out with tree progression just like the numbers.
* New option: disable skill icons greying out with tree progression.
* New icons for items in the Options menu.
* Small presentation changes to the loading screen.
* Changed default start location of the main window from (0,0) to (40,40).
* Loading time now displayed in the status bar of the main window.
* Updated the About window: new information, and text boxes now automatically
  select all the text when clicked for easier copy/pasting.
* FindDialog: Unchecking "All" now deselects all the options.
* ClassPanel: Changed the text color of the Character points label to something
  readable. Can't believe I left it that bad for that long.

Version 3.1
* Rearranged some menu options, moving Export to PNG and Find-a-Skill to new
  "Tools" menu, along with the legacy open/save.
* Added the ability to hide the left Profile Pane (Ctrl+Alt+H), making the calc
  display better on non-widescreen monitors.
* New icons for the Commando, Defender, and Ruiner nodes from AKAtheDopeman of
  the SiliconKnights.net fora! Much better quality than the ones I had, and brings
  the whole interface to parity.
* Redesigned and updated the "About" dialog box with text boxes that allow
  copy/paste-ing of the e-mail and web addresses.
* Changed the stat bars to use multiple bars similar to the design of them in-game.
* Changed menu items in Class menu to use JCheckBoxMenuItems instead of
  JRadioButtonMenu Items. In Windows Classic and Windows XP, the RadioButtons used
  a really bad graphic for the bullet, while the CheckBoxs are much cleaner.

Version 3.0
* Major UI revision: the class and alignment buttons on the left have been
  replaced by drop down menus.
* Minor UI revision: "Notes" field added to class trees. Use the space for
  detailed descriptions of your builds or for whatever. The contents of the
  text box save in the files along with the trees. The box is fully capable
  of copy/paste functions with the system clipboard, as well.
* Small change to FindDialog: addition of WindowFocusListener; when the
  FindDialog gets focus, focus moves to the search box JTextField always.
* Small change to the READ_ME dialog box: it now uses the same kind of text box
  as the notes text box on the trees, so the READ_ME dialog box now has full
  support for cut/copy/paste and all that. If you care for such a thing.
* Redefined setView() to be more useful; as of 2.8 the function of the method
  was spread out and not at all useful. I've consolidated its functions from
  open() and actionPerformed() into setView(), and it now does what it should
  have done all along.

Version 2.8
* Added the ability to filter the Find-a-Skill by class and alignment. Also
  did some rearrangement of the GUI on the Find-a-Skill dialog.
* Added some detail to the spiders and battle-cry descriptions, and fixed some
  small errors in a few other skill nodes.
* Added Tango icons to some menu options that were lacking them.
* Changed the READ_ME viewer frame to use the system LAF font, rather than
  whatever it wanted.

Version 2.7
* Added the ability to search for skills based on the title and description.
  The find box pops up as a separate window, and the results are formatted
  similarly to an older version of the calculator. Search results include
  skill title, icon, and description, as well as the class or alignment
  icon to make identification easier. Hit CTRL+F at any time to try it out!

Version 2.6
* More detail on the Alt+click box for changing skill points.
* New Feature: CTRL+E when viewing a tree allows for the export of the 
  currently viewable trees as a PNG image.
* Fixed the save feature on the files and PNG export to properly handle
  overwriting files and the like. The past implementation was lacking
  considerably.
* Updated some tango icons to more appropriate ones, as well as adding those
  for the export to PNG function.
* Added ability to view this Read Me within the program. It can also be
  found in the JAR archive.
* Bug fix: finally fixed phantom points showing up and not going away on a
  Reset. Had to do with how it was registering the clicks.
* Bug fix: Massive bug: a reset command is done when re-checking spider
  skills after lowering its parent below the pre-req. Don't know how long its
  been doing that, but seems a long time. Glad that's fixed now. The path
  restriction should work perfectly fine now.

Version 2.5
* Small bug fixes.
* Added some icons from the Tango Desktop Project
  (http://tango.freedesktop.org/Tango_Desktop_Project). Really more an
  aesthetic choice.

Version 2.4
* Bugfix: Hitting "Reset" didn't clear some points, preventing full
  builds from being made. App had to be restarted to fix the problem. I
  changed how the reset function works, I believe it is fixed now.
* Updated the skill details using the retail version of the game.
  Commando tree, and both Alignment trees contain the heaviest changes.

Version 2.3
* Added progression bar to the trees. Shows percent of available points
  used. Can be hidden.
* New options menu. Only one so far, but hoping for more.
* Some optimization changes.
* Alt-clicks provide more input options. See help.

Version 2.2
* Updated with new character point total of 95. Also the level calculator
  had to be re-written.
* New loading screen with loading details.
* Can now save/load a file with all five classes. Still supports
  single-class files.

Version 2.1
* Optimization and bugfixes.
* Small change to the Human ruiner node. Previous information based on
  the wiki was wrong. (That is, that the node increased Ruiner range and
  damage. It really only increases range.)

Version 2.0
* Redesigned layout very similar to the actual trees in-game.
* Updated all class and skill details (titles, stats, descriptions) based
  on the recently released Too Human demo.
* Should display much better on none-widescreen displays. I still
  recommend full-screening it, though.
* Click or use the mouse wheel to add or remove points from skills.
* No longer can you add more than 107 points to the trees.

Version 1.0
* BioEngineer stats now match up with the demo.
* Now works on Macs!! (Big thanks to Mark!)
* Class/Alignment icons now in a better place.
* Smaller left pane to allow more screen space for the trees.
* Revamped layout, using skill icons and more detailed descriptions from
  from the Too Human wiki
* Class descriptions from the actual game!
* Class stat bars
* Class icons
* Alignment descriptions
* Total point allocation
* Level calculator
* Splash screen

Version 0.8
* All skill trees
* Both alignment trees
* Path restriction
* Save/load of character trees