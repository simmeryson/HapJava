#HapJava
HapJava is a Java/Android implementation of HomeKit Accessory Protocol server.

#Get Started
Compile HapJava in application module of `build.gradle`.
```
    compile 'com.guok.hapjava:hap-java:0.1.0'
```
Include HapJava in Maven pom.xml
```
<dependency>
  <groupId>com.guok.hapjava</groupId>
  <artifactId>hap-java</artifactId>
  <version>0.1.0</version>
</dependency>
```
##Need to do
1. Advertiser implements re-advertise when accessory or service changing with Bonjour TXT record "c#" update.
2. In Pair Setup M1,M3,M5, need to verify the request parameters and response with kTLVError.
3. More response ,like MultiStatus e.g. need to implement
4. SubscriptionManager exists issue when lost connection.
5. AccessoryHandler.connection maybe exist issue concurrently.
6. **Characteristics permission exits big problem.**
7. `/identify` request does not implement Page 88. 
8. `/paring` request with method LIST_PAIRINGS need to implement.