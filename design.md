# SDK Design

Principles followed when designing this SDK:
1. Do the simplest possible thing that works.  Iterative improvements can be done at a later time, once the initial version has been released and interested parties have a chance to use it.
2. Generate code where possible.  No need to write code if it can be generated.
3. Use the backend Node.js service as the source of truth.
4. Use standards and widely used libraries where possible.  Don't reinvent the wheel.

My strategy in coming up with this SDK was to reverse-engineer the backend Node.js service (reference principles #1 and #3 above).  I was able to check out the source code and spin up the servicce locally, loading data from the provided .bson files into a locally running Mongo.

In order to fully understand the endpoints and how they worked with pagination, sorting, filtering and security, I used Postman to craft requests to the locally running instance.

Once I understood the nuances of the three endpoints, I created an OpenAPI specification that describes the backend.  I used the existing Postman queries to validate that this spec accurately describes the backend.  (principle #4)

Once I had an accurate OpenAPI spec, I used an open source library called OpenAPI Generator to generate Java client code (api interfaces and domain objects) (principle #3).  I wrote a facade class (`LotrClient`) in front of these generated classes that is the entry point to the SDK.  I tested this class by writing integration tests using widely used Java testing tools (Junit & Maven Failsafe).

Lastly, I generated as much documentation as possible from the source code using the Maven Javadoc and Maven Site plugins.

