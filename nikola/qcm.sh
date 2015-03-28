#!/bin/tcsh

javac Qcm.java
cat headquiz.html > stories/qcm/quiz.html
java Qcm qcm.txt >> stories/qcm/quiz.html
cat tailquiz.html >> stories/qcm/quiz.html

