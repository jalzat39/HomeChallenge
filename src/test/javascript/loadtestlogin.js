import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuration options
export const options = {
    stages: [
        { duration: '30s', target: 10 }, // Ramp up to 10 users over 30 seconds
        { duration: '1m', target: 10 },  // Stay at 10 users for 1 minute
        { duration: '30s', target: 0 },  // Ramp down to 0 users over 30 seconds
    ],
};

export default function () {
    // Define the login request payload
    const loginPayload = JSON.stringify({
        username: 'testuser',
        password: 'testpassword',
    });

    // Define the login request options
    const loginOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Perform the login request
    const response = http.post('http://localhost:8080/api/v3/user/login', loginPayload, loginOptions);

    // Check the response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response time < 200ms': (r) => r.timings.duration < 200,
    });

    // Sleep for a short period to simulate user think time
    sleep(1);
}
