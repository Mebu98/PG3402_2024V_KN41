export async function getChallengesCount(count){
    await fetch(`http://localhost:8080/analytics/api/v1/challenges/count`,
        {
            method: 'GET'
        }
    ).then(response => response.json().then(data => {
        count.value = data;
    }))
}