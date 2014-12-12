Team W
======
Lobster and shrimp and a glass of moscato


git cheatsheet
==============
- **Pull before making changes!!!**.

Pulling Changes and Conflict Resolution
---------------------------------------------------------------------------------
If you haven't made any changes since the last time you pulled, it's safe to just pull again.

	$ git pull 	// if this results in an error, keep reading! if not, you're good to go.

But, if you've been working on something, but want to get a new version from github that someone just pushed, you need to do a few extra steps.

First, stage your changes. As a precaution, check to see whether you got everything. You should see all the files you've made changes to under "Files staged for commit."

	$ git add .
	$ git status

(If it doesn't look right, try git add from the top-level directory.) Now do 

	$ git stash

This saves all the changes you've made and cleans your working directory. Now it's safe to pull. 

	$ git pull

If that's successful, do

	$ git stash pop

You probably will see errors about conflicts. You'll have to resolve them manually. Go into eclipse and you'll see files with errors and markers (=== and >>>) indicating differences. Decide what you want to keep. 

You can also try: 

	$ git mergetool	

Once you've fixed all the errors, commit your changes.

	$ git commit -m "Resolving conflicts."

You're done! You should have all the latest changes AND your code should be intact. :)

Pushing changes
---------------
Stage your changes. If you've made changes in a lot of places, **switch to the top-level (/winers) directory first**. This command adds any changes made in the CURRENT directory:

    $ git add . 

Commit your changes. 
	
	$ git commit 

This will open a text editor, probably vim. At the top, type a brief description of your changes. Now push your changes.

	$ git push

If you have problems, it might be because you haven't pulled the latest version. 

guide
-------
- [stack overflow](http://stackoverflow.com/questions/315911/git-for-beginners-the-definitive-practical-guide)

Standards
================
Quick coding conventions.

XML Conventions
-----------------

- Use underscores to separate words, not camel case. e.g. wine_of_the_day

Java Conventions
------------------

- Use camel case. e.g. MainActivity.java
- Capitalize class names, lowercase for everything else.
- 80 characters per line
- Use javadoc for methods and classes. 

Commenting - Methods
---------------------

Method name: doSomething()
Description: Does something descriptive.
Author: Your Name
@param int num, String something
@return int num

Commenting - Classes
---------------------

Description: 
@author Your Name, Jessica Rabbit, Frank Ocean

Common problems and fixes
==========================

All bugs
--------
Go to Window > Show View > Problems for hints.

Error only on the last file / "page contains invalid characters"
----------------------------------------------------------------
1. See whether your project.properties file exists.
2. If it doesn't, create one (in the top level directory) and add the line "target=android-17"
3. Go to ADT Preferences > Android
4. Set your SDK location (even if it's already set). 
5. Right click the winetastic project > build config > Android and check Android 4.2 and add the Facebook SDK at the bottom.  
6. Clean and refresh your project. Your project.properties file should have the target and sdk location.
7. The project should display normally now. Clear up any other errors. 
8. If still not working, check your Problems panel and repeat steps as needed. 