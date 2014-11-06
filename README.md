setuid-jetty-test
=================

Build an uberjar:
```
lein uberjar
```

Run it as root:
```
ᐅ su root
# java -jar target/setuid-jetty-test-0.1.0-SNAPSHOT-standalone.jar \
  -b test-resources/bootstrap.cfg \
  -c test-resources/config.conf
```

Observe that the process is owned by root:
```
ᐅ pgrep -lf -u root java
10219 /usr/bin/java -jar target/setuid-jetty-test-0.1.0-SNAPSHOT-standalone.jar -c test-resources/config.conf -b test-resources/bootstrap.cfg
```

Double check, like state farm + aaron rodgers:
```
~/code/setuid-jetty-test
ᐅ ps aux | grep java
root            10219   0.0  1.7  7099028 284252 s000  S+    2:18PM   0:14.59 /usr/bin/java -jar target/setuid-jetty-test-0.1.0-SNAPSHOT-standalone.jar -c test-resources/config.conf -b test-resources/bootstrap.cfg
```

Poke it:
```
ᐅ curl http://localhost:443/hello
There will be no reprieve for the thieves.%
```

Notice that the server logs this:
```
My UID will change in 10 seconds...
SUCCESFULLY CHANGED MY UID
```

Now it's owned by user with uid 501!
```
ᐅ pgrep -lf -u root java
(no results)

ᐅ ps aux | grep java
501             10219   0.1  1.7  7102136 287760 s000  S+    2:18PM   0:14.69 /usr/bin/java -jar target/setuid-jetty-test-0.1.0-SNAPSHOT-standalone.jar -c test-resources/config.conf -b test-resources/bootstrap.cfg
```

... and it can still handle requests:
```
ᐅ curl http://localhost:443/hello
There will be no reprieve for the thieves.%
```
