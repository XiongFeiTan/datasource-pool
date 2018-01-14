In JVM-based background development, in high-concurrency scenarios, there are often some objects such as database connections, threads ... which take a long time to create and initialize. When using these objects in large quantities, If you do not take some technical optimization, it will cause some efficiency and performance issues.

The common simple optimization for this problem is to use the object pool, each created object is not actually destroyed, but cached in the object pool, the next time you use it, you do not have to re-create, you can directly from the object pool cache In the take, you can change the time through the space, do not have to create and close the object each time.

This project  is based on commons-pool2 using Java language to achieve high availability of the object pool

You can make the appropriate changes to the project to suit your own environment, at the same time you can use the same development to achieve other pool of objects.

env:
jdk:1.8
redis-client:2.9.x
hbase:1.2.0
mysql:5.1.38
 