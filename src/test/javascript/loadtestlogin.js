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

// Define a function to generate random user data
function generateUserData() {
    const randomNum = Math.floor(Math.random() * 1000) + 1;
    return {
        id: randomNum,
        username: `testuser${randomNum}`,
        firstName: `FirstName${randomNum}`,
        lastName: `LastName${randomNum}`,
        email: `user${randomNum}@example.com`,
        password: `testpassword`,
        phone: `1234567890`,
        userStatus: 1,
    };
}

export default function () {
    // Generate user data
    const userData = generateUserData();

    // Define the user creation request payload and options
    const userPayload = JSON.stringify(userData);
    const userOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Perform the user creation request
    const userResponse = http.post('http://localhost:8080/api/v3/user', userPayload, userOptions);

    // Check the user creation response
    check(userResponse, {
        'User creation status is 200': (r) => r.status === 200,
    });

    // Define the login request payload
    const loginPayload = JSON.stringify({
        username: userData.username,
        password: userData.password,
    });

    // Define the login request options
    const loginOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Perform the login request
    const loginResponse = http.post('http://localhost:8080/api/v3/user/login', loginPayload, loginOptions);

    // Check the login response
    check(loginResponse, {
        'Login status is 200': (r) => r.status === 200,
        'Response time < 200ms': (r) => r.timings.duration < 200,
    });

    // Simulate user think time
    sleep(1);
}