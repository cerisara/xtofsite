#!/bin/tcsh

# This script shall only be run by the git user on TALC1;
# It is actually automatically run when anyone make a "git push"
# It then compiles and deploys the new site on the TALC1 web server

set d = ""`pwd`

set outdir = "/var/www/users/cerisara/"

nikola build

set host=`hostname`
if ("$host" == "talc1") then
  rm -rf $outdir/*
  cp -r output/* $outdir
  chmod -R 755 $outdir
  cp access $outdir/stories/intranet/.htaccess
  cp passwd $outdir/stories/intranet/.htpasswd
  chmod 775 $outdir/stories/intranet/.htaccess
  chmod 664 $outdir/stories/intranet/.htpasswd
endif

