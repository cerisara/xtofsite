#!/bin/tcsh

# This script shall only be run by the git user on TALC1;
# It is actually automatically run when anyone make a "git push"
# It then compiles and deploys the new site on the TALC1 web server

set d = ""`pwd`

set outdir = "/var/www/html/xtof/siteperso/"

nikola build

cp -r output/* $outdir

