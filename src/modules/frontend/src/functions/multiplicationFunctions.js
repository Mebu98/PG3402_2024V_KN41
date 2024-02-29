export async function generateMultiplicationChallenge(userName, difficulty, challenge){
    await fetch(`http://localhost:8080/multiplication/api/v1/generate/username/${userName}/difficulty/${difficulty}`,
        {
            method: 'GET'
        }
    ).then(response => response.json().then(data => {
        challenge.value = data;
    }))
}

export async function submitMultiplicationChallenge(challenge, userAnswer, submitResponse){
    challenge.userAnswer = userAnswer;

    await fetch(`http://localhost:8080/multiplication/api/v1/submit`,
        {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(challenge)
        }
    ).then(response => response.json().then(data => {
        submitResponse.value = data;
    }))
}