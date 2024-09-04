import http from 'k6/http';
import { check, sleep } from 'k6';

// Configuration options
export const options = {
    stages: [
        { duration: '30s', target: 20 }, // Ramp up to 20 users over 30 seconds
        { duration: '1m', target: 20 },  // Stay at 20 users for 1 minute
        { duration: '30s', target: 0 },  // Ramp down to 0 users over 30 seconds
    ],
};

// Define a function to generate random user data
function generateUserData() {
    const randomNum = Math.floor(Math.random() * 1000) + 1; // Generate a random number between 1 and 1000
    return {
        id: randomNum,
        username: `user${randomNum}`,
        firstName: `FirstName${randomNum}`,
        lastName: `LastName${randomNum}`,
        email: `user${randomNum}@example.com`,
        password: `Password${randomNum}`,
        phone: `1234567890`,
        userStatus: 1
    };
}

// Define a function to add a user and check the response
export default function () {
    // Generate random user data
    const userData = generateUserData();

    // Define the request options
    const options = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Perform the POST request to add the user
    const response = http.post('http://localhost:8080/api/v3/user', JSON.stringify(userData), options);

    // Check the response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response contains user id': (r) => r.json().id === userData.id,
    });

    // Sleep for a short period to simulate user think time
    sleep(1);
}