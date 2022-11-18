#!/bin/bash

# back
# svn
rm -rf /zzz/hr/test/svn/test/back
svn checkout https://180.169.88.114:6443/svn/hr/trunk/dev/back /zzz/hr/test/svn/test/back --username yangzhouchuan --password Yzc135.

# mvn
cd /zzz/hr/test/svn/test/back/hr
mvn -s "/zzz/hr/test/maven/conf/settings_hr_test.xml" clean install package -Dmaven.test.skip=true

# run back
DAY=`date +%Y-%m-%d`
pid=`ps -ef|grep hr-test.jar|grep -v grep|awk '{print $2}'`
if [ -n "$pid" ]
then
echo 'The pid: server' $pid ' will be killed....'
kill -15 $pid
fi
rm -rf /zzz/hr/test/exec/back/hr-test.jar
cp /zzz/hr/test/svn/test/back/hr/target/hr.jar /zzz/hr/test/exec/back/hr-test.jar
cd /zzz/hr/test/exec/back
nohup java -jar hr-test.jar --spring.profiles.active=test --server.port=9101 > /zzz/hr/test/exec/back/hr-test.log &


# front
# svn
rm -rf /zzz/hr/test/svn/test/front
svn checkout https://180.169.88.114:6443/svn/hr/trunk/dev/front /zzz/hr/test/svn/test/front --username yangzhouchuan --password Yzc135.

# run front
rm -rf /zzz/hr/test/exec/front/*
cd /zzz/hr/test/svn/test/front/hr-web
npm i
npm run build
cp -rf dist/* /zzz/hr/test/exec/front

echo "finished"
