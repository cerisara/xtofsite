#!/bin/tcsh

cd jcompil
java -cp bin jcompil.JCompil

cd ..
cp index.html ~/web/
cp jobs.html ~/web/
cp links.html ~/web/
cp software.html ~/web/
cp jsafran.html ~/web/
cp research.html ~/web/
cp news.html ~/web/
cp go.html ~/web/
cp publis.html ~/web/

