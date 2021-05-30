function quizWindow() {
    window.location = 'quiz.html';
}

function showQuiz1Score() {
    var score = 0;
    $( "#questions_apti" ).remove();
    $( "#questions_apti1" ).remove();
    var op1 = document.getElementsByName('s1q1');
    var op2 = document.getElementsByName('s1q2');
    var op3 = document.getElementsByName('s1q3');
    if(op1[3].i) {
        score = score + 1;
    }

    document.getElementById("end").innerText = "Total Score Round 1: " + score.toString();
    document.onkeydown = function (e) {
        return false;
    }
    console.log(score);
}