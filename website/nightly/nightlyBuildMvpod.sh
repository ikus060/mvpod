#!/bin/sh

svn co --username ikus060 --password xomg000 https://svn.entreprisesmd.homeip.net:8443/svn/mvpod/mvPod/trunk/

cd trunk
ant clean
ant dist

cd dist
ssh ikus060@shell.sourceforge.net rm /home/groups/m/mv/mvpod/htdocs/nightly/*
scp *.tar.gz ikus060@shell.sourceforge.net:/home/groups/m/mv/mvpod/htdocs/nightly/
scp *.zip ikus060@shell.sourceforge.net:/home/groups/m/mv/mvpod/htdocs/nightly/
scp *.exe ikus060@shell.sourceforge.net:/home/groups/m/mv/mvpod/htdocs/nightly/

