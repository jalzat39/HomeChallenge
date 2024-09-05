# API Testing
Everything related to the tests performed on the APIs provided by the application is found in the folder with the path *src/test/java*, in which the Pet and User APIs are validated, both for good cases and edge cases. To execute these tests, the Java language was used with the Rest Assured tool.
We can see with the execution of the suite a good implementation except for the part of Logging in with an incorrect password, but correct username, that the status code is 200 instead of 400.

# Performance Tests
The performance tests may seem somewhat simple and only 3, although I know the JavaScript language, I had not used the k6 tool, this was my first approach, made especially for the challenge.
The performance tests are in the "src/test/javascript" path, which was tested with a medium load, which can be scaled, and the JavaScript language was used with the k6 tool.
- In the first test we have a load test against the API that allows us to create users, giving the result of:
    ```
  execution: local
        script: loadtestadduser.js
        output: -

     scenarios: (100.00%) 1 scenario, 20 max VUs, 2m30s max duration (incl. graceful stop):
              * default: Up to 20 looping VUs for 2m0s over 3 stages (gracefulRampDown: 30s, gracefulStop: 30s)


     ✓ status is 200
     ✓ response contains user id

     checks.........................: 100.00% ✓ 3606     ✗ 0
     data_received..................: 940 kB  7.8 kB/s
     data_sent......................: 574 kB  4.8 kB/s
     http_req_blocked...............: avg=9.46µs   min=0s med=0s     max=3.7ms    p(90)=0s       p(95)=0s
     http_req_connecting............: avg=4.31µs   min=0s med=0s     max=1.01ms   p(90)=0s       p(95)=0s
     http_req_duration..............: avg=1.53ms   min=0s med=1.51ms max=102.91ms p(90)=2.07ms   p(95)=2.52ms
       { expected_response:true }...: avg=1.53ms   min=0s med=1.51ms max=102.91ms p(90)=2.07ms   p(95)=2.52ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1803
     http_req_receiving.............: avg=155.48µs min=0s med=0s     max=3.01ms   p(90)=905.17µs p(95)=1ms
     http_req_sending...............: avg=22.92µs  min=0s med=0s     max=5.6ms    p(90)=0s       p(95)=0s
     http_req_tls_handshaking.......: avg=0s       min=0s med=0s     max=0s       p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=1.35ms   min=0s med=1.05ms max=102.91ms p(90)=1.85ms   p(95)=2.22ms
     http_reqs......................: 1803    14.95938/s
     iteration_duration.............: avg=1.01s    min=1s med=1.01s  max=1.1s     p(90)=1.01s    p(95)=1.01s
     iterations.....................: 1803    14.95938/s
     vus............................: 1       min=1      max=20
     vus_max........................: 20      min=20     max=20

                                                                                                                                                                                                             
  running (2m00.5s), 00/20 VUs, 1803 complete and 0 interrupted iterations                                                                                                                                     
  default ✓ [======================================] 00/20 VUs  2m0s   
  ```

  - **Overall Performance:** The test executed successfully with no failed checks and very low average response times, indicating a well-performing system under the specified load.
  - **Scalability and Load Handling:** The system handled 20 virtual users effectively, with minimal latency and no errors.
  - **Network Load:** The amount of data exchanged is reasonable given the volume of requests.

 - In the second one, executed against the login API for valid credentials we had the following result:
   ```
   execution: local
        script: loadtestlogin.js
        output: -

     scenarios: (100.00%) 1 scenario, 10 max VUs, 2m30s max duration (incl. graceful stop):
              * default: Up to 10 looping VUs for 2m0s over 3 stages (gracefulRampDown: 30s, gracefulStop: 30s)


     ✓ User creation status is 200
     ✗ Login status is 200
      ↳  0% — ✓ 0 / ✗ 909
     ✓ Response time < 200ms

     checks.........................: 66.66% ✓ 1818      ✗ 909
     data_received..................: 856 kB 7.1 kB/s
     data_sent......................: 478 kB 4.0 kB/s
     http_req_blocked...............: avg=6.73µs   min=0s med=0s       max=5ms     p(90)=0s       p(95)=0s
     http_req_connecting............: avg=2.25µs   min=0s med=0s       max=1ms     p(90)=0s       p(95)=0s
     http_req_duration..............: avg=515.98µs min=0s med=509µs    max=26.79ms p(90)=1.05ms   p(95)=1.22ms
       { expected_response:true }...: avg=755.04µs min=0s med=511.49µs max=26.79ms p(90)=1.14ms   p(95)=1.37ms
     http_req_failed................: 50.00% ✓ 909       ✗ 909
     http_req_receiving.............: avg=75.7µs   min=0s med=0s       max=1.53ms  p(90)=507µs    p(95)=512.4µs
     http_req_sending...............: avg=14.14µs  min=0s med=0s       max=1.33ms  p(90)=0s       p(95)=0s
     http_req_tls_handshaking.......: avg=0s       min=0s med=0s       max=0s      p(90)=0s       p(95)=0s
     http_req_waiting...............: avg=426.13µs min=0s med=508.29µs max=26.79ms p(90)=844.97µs p(95)=1.05ms
     http_reqs......................: 1818   15.103464/s
     iteration_duration.............: avg=1.01s    min=1s med=1.01s    max=1.05s   p(90)=1.01s    p(95)=1.01s
     iterations.....................: 909    7.551732/s
     vus............................: 1      min=1       max=10
     vus_max........................: 10     min=10      max=10

                                                                                                                                                                                                             
    running (2m00.4s), 00/10 VUs, 909 complete and 0 interrupted iterations                                                                                                                                      
    default ✓ [======================================] 00/10 VUs  2m0s     
   ```
    - User Creation Success: All user creation requests were successful, which is a good sign for that part of the API. The status code 200 indicates that the API handled the requests as expected.
    - Login Failures: The login endpoint did not return a status code of 200 for any of the 909 attempts. This is a serious issue and suggests that the login functionality is not working as intended.
    - Response Time: Despite the failures, response times were within the acceptable range (<200ms), indicating that the server was not overloaded and was responding quickly to requests.
    - HTTP Request Failures: A 50% failure rate for HTTP requests indicates a significant issue with processing requests. This could be due to server errors, misconfigured endpoints, or other issues.

  - The third load test is run against the API in charge of adding pets, we had the following result:
    ```
    execution: local
        script: loadtestpet.js
        output: -

     scenarios: (100.00%) 1 scenario, 20 max VUs, 2m30s max duration (incl. graceful stop):
              * default: Up to 20 looping VUs for 2m0s over 3 stages (gracefulRampDown: 30s, gracefulStop: 30s)


     ✗ status is 200
      ↳  62% — ✓ 1135 / ✗ 668
     ✗ response contains pet id
      ↳  62% — ✓ 1135 / ✗ 668

     checks.........................: 62.95% ✓ 2270      ✗ 1336
     data_received..................: 943 kB 7.8 kB/s
     data_sent......................: 623 kB 5.2 kB/s
     http_req_blocked...............: avg=12.08µs  min=0s med=0s       max=5.64ms  p(90)=0s      p(95)=0s
     http_req_connecting............: avg=5.49µs   min=0s med=0s       max=918µs   p(90)=0s      p(95)=0s
     http_req_duration..............: avg=881.54µs min=0s med=512.59µs max=50.52ms p(90)=1.21ms  p(95)=1.51ms
       { expected_response:true }...: avg=787.69µs min=0s med=511.8µs  max=50.52ms p(90)=1.12ms  p(95)=1.35ms
     http_req_failed................: 37.04% ✓ 668       ✗ 1135
     http_req_receiving.............: avg=121.86µs min=0s med=0s       max=38ms    p(90)=509.1µs p(95)=601.07µs
     http_req_sending...............: avg=17.2µs   min=0s med=0s       max=1.52ms  p(90)=0s      p(95)=0s
     http_req_tls_handshaking.......: avg=0s       min=0s med=0s       max=0s      p(90)=0s      p(95)=0s
     http_req_waiting...............: avg=742.47µs min=0s med=510.4µs  max=24.03ms p(90)=1.05ms  p(95)=1.23ms
     http_reqs......................: 1803   14.951971/s
     iteration_duration.............: avg=1.01s    min=1s med=1.01s    max=1.15s   p(90)=1.01s   p(95)=1.01s
     iterations.....................: 1803   14.951971/s
     vus............................: 1      min=1       max=20
     vus_max........................: 20     min=20      max=20

                                                                                                                                                                                                             
    running (2m00.6s), 00/20 VUs, 1803 complete and 0 interrupted iterations                                                                                                                                     
    default ✓ [======================================] 00/20 VUs  2m0s
    ```
    - High Failure Rates:
      - Status 200 Failures: 62% of requests failed to return a 200 status code.
      - Response Content Failures: 62% of responses did not include the expected pet ID.
      These high failure rates indicate that there may be an issue with the /api/v3/pet endpoint. This could be due to server-side errors, incorrect API configurations, or issues with the request payload or endpoint.
    - Response Times: Despite the failures, response times are relatively low, averaging under 1ms. This suggests that the server is responding quickly, but the content of the responses is not as expected.
    - Data Transfer: The data transfer rates (both received and sent) are consistent with the number of requests and responses, but the high failure rate overshadows these metrics.
