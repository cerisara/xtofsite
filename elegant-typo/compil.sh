#!/bin/tcsh

cd jcompil
javac src/jcompil/*.java
mv -f src/jcompil/*.class bin/jcompil/
java -cp bin jcompil.JCompil

cd ..
foreach i (index jobs links jsafran software jtrans research news go publis)
  iconv --from=UTF-8 --to=ISO-8859-1 $i".html" | sed 's,UTF-8,ISO-8859-1,g' >! ~/web/$i".html"
end

cp *.jpg ~/web/
cp -r styles ~/web/
cp -r images ~/web/
cd ~/web
