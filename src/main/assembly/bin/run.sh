#!/bin/sh
java -jar -Xmx512M -Xms512M -XX:PermSize=128M -XX:MaxPermSize=128M -XX:+HeapDumpOnOutOfMemoryError ../lib/weixin-0.0.1-SNAPSHOT.jar &
echo $! > pid