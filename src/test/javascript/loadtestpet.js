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

// Define a function to generate random pet data
function generatePetData() {
    const randomNum = Math.floor(Math.random() * 1000) + 1; // Generate a random number between 1 and 1000
    return {
        id: randomNum,
        name: `Pet${randomNum}`,
        status: 'available',
        category: {
            id: 34,
            name: 'Sporting Group'
        },
        tags: [
            { id: 34, name: 'Sporting' }
        ],
        photoUrls: [`https://images.dog.ceo/breeds/labrador/n02099712_${randomNum}.jpg`]
    };
}

// Define a function to add a pet and check the response
export default function () {
    // Generate random pet data
    const petData = generatePetData();

    // Define the request options
    const options = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    // Perform the POST request to add the pet
    const response = http.post('http://localhost:8080/api/v3/pet', JSON.stringify(petData), options);

    // Check the response
    check(response, {
        'status is 200': (r) => r.status === 200,
        'response contains pet id': (r) => r.json().id === petData.id,
    });

    // Sleep for a short period to simulate user think time
    sleep(1);
}