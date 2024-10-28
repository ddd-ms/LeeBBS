Trace some ideas...
1. Activation token is used for only once, 
no need to store permanently. 
By using Redis and set an expiration time can save much disk memory and
advance security.
2. How to sync session with nginx? -- Redis