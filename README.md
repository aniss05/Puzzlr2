## Description

Puzzlr is a secure image sharing application like Snapchat. However, unlike Snapchat, the pictures are encrypted before being sent to the server.
We used as server an Open Block Chain implementation which was developped using Go language.
When you register, an RSA key pair is going to be generated. The public key is going to be sent to the server to be stored. On the other hand, the private key is securely stored on your device.
Then when you want to send a picture, your application is going to generate an AES session key and and IV and a MAC key, encrypt the image with the AES key, generate a MAC tag on this ciphertext and the IV, and finally encrypt the AES key and the MAC key and the other user's username with the other correspondant's RSA public key which is retreived from the server.

These whole messages are then cnverted to Base 64 String format and sent to the server which will forward it to the other user.

The other user will receive the message from the server, recovers the AES key using his/her private key, checks if the MAC tag is valid, and then decrypts the image using the recovered AES key.


## Motivation

This is initially a final project for our course. This version is still under developpement and needs to be improved. 

## Installation

## Java installation

First, before beginning, you need to install Oracle-Java on your computer. For better results, avoid installing Open-JDK if you are on Ubuntu. 


For Ubuntu or other Debian Linux distribution just enter the following commands on your terminal:



sudo apt-add-repository ppa:webupd8team/java


sudo apt-get update


sudo apt-get install oracle-java8-installer


To set the previously installed version of Java as default, do as follows:



sudo update-alternatives --config java

You will be prompted several choices, if you have several versions of Java installed. Just choose the number which describes the Oracle-Java8 version that you just installed.

##Android studio installation

Note that for Android developpement, you can useseveral Integrated Developpement Environments,like Eclipse, or Android Studio, or IntelliJ. For this project, we used Android Studio which can be installed on Debian distributions as follows:



sudo apt-add-repository ppa:paolorotolo/android-studio



sudo apt-get update




sudo apt-get install android-studio


##Android SDK installation

Once Android Studio is successfully installed, you need to open it and this one will guide you to download and install the Android SDK.

##Genymotion installation

For testing your project, you need to test it first on a virtual device before installing it on a physical device. To do so, there are two major ways. The first one consists of using the Android Studio virtual device manager which we do not recommend, because of several bugs we encountered. The second one which we used is Genymotion, which guarantee a fast and easy way to emulate Android applications. To install it, just go on Genymotion's website and download the version that best fits with your operating system (Note: you have to register first).

##Puzzlr integration

Once all these tools are installed on your machine, just do the following command if you are on Linux:




git clone https://github.com/aniss05/Puzzlr2.git

##Note

You need to install Git on your computer first:




sudo apt-get install git




Also you need to install VirtualBox in order to use Genymotion:




sudo apt-get install virtualbox




Then open your Android Studio if you did not yet, and choose the option which says to import an existing Android Studio project. This will prompt to you a window in which you have to give the location of the cloned Puzzlr location.

##Note

After doing this last step, you will probably have errors saying that Gradle build failed. If it is the case, you will see a notification on the top right of your Android Studio window that gives you a link. Click on it and you will be prompted to a new window, in which you need to check all versions of Android SDK that are shown and click on apply.

This will download all Android SDK versions and install them. Wait until it finishes then restart your Android Studio.


Last but not least, you need to install Android-NDK. To do so, go to your Project Structure on Android Studio and in the SDK Location on the top left, you will find Android NDK location, where there will be a link. Click on it and this will download and install it for you. Also, in the same window, choose the JDK Location to the directory where Oracle-Java8 is installed. You will need to restart Android Studio after the installation of the NDK is complete. 


Finally, you will need to install Genymotion's plugin on Android Studio. Go to your Project Settings this time and you will find Plugins. In the new window, search for Genymotion (this will give you no result, click on Browse Repository) and you will find it. Install it and then restart Android Studio again. 


##Collaborators
We are two students working on this project:
Aniss Chohra: https://github.com/aniss05
Quentin Le Sceller:  https://github.com/search?q=quentinlesceller&type=Code&utf8=%E2%9C%93


