<!---
.. link: 
.. description: 
.. tags: 
.. date: 2013/11/15 17:25:15
.. title: JTrans
.. slug: jtrans
-->

JTrans
==================
JTrans is a software that is primarily designed to bring speech alignment (and by extension speech recognition) technology
right to the user, and ready to use. No need to be speech technology expert to use JTrans.

JTrans only works in French for now.

JTrans is open-source, and is version-controlled under GIT. Please contact me to have access to the (undocumented, very fuzzy) source code.

Quick start guide
-----------------

Download the (small) core application here: [jtrans.jar](http://talc1.loria.fr/users/cerisara/jtrans.jar)
and run it by double-clicking on it, or with the command-line:

    java -jar jtrans.jar

Note that when run the first time, this application will download a huge quantity of data (about 1Gb): these are
the acoustic models, phonetic lexicons, language models and phonetisation models.

By default, jtrans loads an example project, which is an excerpt from France Culture, already aligned.
So you just have to press "play" to see the result of the alignment.
You can then move in the text by clicking on any word, while it's playing.

If you want to start a new project, you need to:

* Load a WAV file (it's highly recommended to first convert your sound file to the uncompressed format WAVE, 16 kHz, 16bits signed, little-endian, mono
with another audio software, such as audacity)
* Load a text file that contains the transcription (it's highly recommended to copy/paste the text file from another word processor, in order to avoid potential encoding issues)
* You then want to remove all previous annotations with the menu "process" - "clear all aligns"
* Then, you need to "parse" the text to identify what is pronounced from what is not (punctuation marks, speaker ids, comments...) with the menu "edit" - "parse text regexp"
* You can finally clic on "AutoAlign" to generate the alignment.

Limitations
-----------

* Using a different input sound file format than the recommended one may result in shifted alignment, or even crash.
* It requires java7 and at least 8 Gb of RAM
* There are only French models for now
