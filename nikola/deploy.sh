#!/bin/tcsh

# This script shall only be run by the git user on TALC1;
# It is actually automatically run when anyone make a "git push"
# It then compiles and deploys the new site on the TALC1 web server

set d = ""`pwd`

set outdir = "/var/www/users/cerisara/"

/home/xtof/softs/nikola-5.4.4/scripts/nikola build

scp -r output/* talc1:$outdir

scp access talc1:$outdir/stories/intranet/.htaccess
scp passwd talc1:$outdir/stories/intranet/.htpasswd
