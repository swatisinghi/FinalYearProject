set -e

CATALINA_HOME=/usr/share/tomcat6
cd res;
cp local_log4j.properties log4j.properties ;
cp local_sms.properties sms.properties ;
cd ..;
ant;
rm -rf $CATALINA_HOME/webapps/sms*;
cp build/sms.war $CATALINA_HOME/webapps/;
cd res;
cp prod_sms.properties sms.properties ; cp prod_log4j.properties log4j.properties;
cd ..;

cp jsp/index.jsp jsp/index-copy.html;

$CATALINA_HOME/bin/catalina.sh stop -force;
sleep 2;
$CATALINA_HOME/bin/catalina.sh start
