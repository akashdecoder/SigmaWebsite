//akash
function insertValue() {
    if(document.getElementById("branch").value == "OTHERS") {
        var branch = document.getElementById("branch");
        var promt = prompt("Please Enter branch (After Entering revisit the dropdown)");
        if(promt == "") {
            alert("Enter value");
        } else {
            var newOption = document.createElement("OPTION");
            var newOptionVal = document.createTextNode(promt);
            branch.remove(branch.selectedIndex)
            newOption.appendChild(newOptionVal);
            branch.insertBefore(newOption, branch.firstChild);
        }   
    }
}

var clock = new Vue({
    el: '#clock',
    data: {
        time: '',
        date: ''
    }
});

var week = ['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'];
var timerID = setInterval(updateTime, 1000);
updateTime();
function updateTime() {
    var cd = new Date();
    clock.time = zeroPadding(cd.getHours(), 2) + ':' + zeroPadding(cd.getMinutes(), 2) + ':' + zeroPadding(cd.getSeconds(), 2);
    clock.date = zeroPadding(cd.getFullYear(), 4) + '-' + zeroPadding(cd.getMonth()+1, 2) + '-' + zeroPadding(cd.getDate(), 2) + ' ' + week[cd.getDay()];
};

function zeroPadding(num, digit) {
    var zero = '';
    for(var i = 0; i < digit; i++) {
        zero += '0';
    }
    return (zero + num).slice(-digit);
}

anime.timeline({loop: true})
  .add({
    targets: '.ml15 .word',
    scale: [14,1],
    opacity: [0,1],
    easing: "easeOutCirc",
    duration: 800,
    delay: (el, i) => 800 * i
  }).add({
    targets: '.ml15',
    opacity: 0,
    duration: 1000,
    easing: "easeOutExpo",
    delay: 1000
  });


  //Disable copy, cut and paste
  $(document).ready(function() {
    var ctrlDown = false,
        ctrlKey = 17,
        cmdKey = 91,
        vKey = 86,
        cKey = 67;

    $(document).keydown(function(e) {
        if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = true;
    }).keyup(function(e) {
        if (e.keyCode == ctrlKey || e.keyCode == cmdKey) ctrlDown = false;
    });

    $(".no-copy-paste").keydown(function(e) {
        if (ctrlDown && (e.keyCode == vKey || e.keyCode == cKey)) return false;
    });
    
    // Document Ctrl + C/V 
    $(document).keydown(function(e) {
        if (ctrlDown && (e.keyCode == cKey)) console.log("Document catch Ctrl+C");
        if (ctrlDown && (e.keyCode == vKey)) console.log("Document catch Ctrl+V");
    });
});




function visibleQuiz() {
    $( "#start" ).remove();
    $( "#s" ).remove();
    document.getElementById("quiz").style.visibility = "visible";
    document.onkeydown = function (e) {
        return false;
    }
}


function visibleQuiz1() {
    var element = document.getElementById("quiz");
    element.parentNode.removeChild(element);
    document.getElementById("quiz2").style.visibility = "visible";
    document.onkeydown = function (e) {
        return false;
    }
}


function coding() {
    var element = document.getElementById("quiz2");
    element.parentNode.removeChild(element);
    document.getElementById("ide").style.visibility = "visible";
    document.onkeydown = function (e) {
        return false;
    }
}

function checkPasswordMatch(fieldConfirmPassword) {
    if (fieldConfirmPassword.value != $("#password").val()) {
        fieldConfirmPassword.setCustomValidity("Passwords do not match!");
    } else {
        fieldConfirmPassword.setCustomValidity("");
    }
}