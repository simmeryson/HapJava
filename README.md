###HapJava
HapJava is a Java implementation of HomeKit Accessory Protocol server.

####Need to do
1. Advertiser implements re-advertise when accessory or service changing with Bonjour TXT record "c#" update.
2. In Pair Setup M1,M3,M5, need to verify the request parameters and response with kTLVError.
3. More response ,like MultiStatus e.g. need to implement
4. SubscriptionManager maybe exist issue when multi-connection subscript.
5. SubscriptionManager exists issue when lost connection.
6. AccessoryHandler.connection maybe exist issue concurrently.