(function () {
  const second = 1000,
        minute = second * 60,
        hour = minute * 60,
        day = hour * 24;

  let birthday = "May 12, 2021 18:41:00",
      countDown = new Date(birthday).getTime(),
      x = setInterval(function() {    

        let now = new Date().getTime(),
            distance = countDown - now;

          document.getElementById("days").innerText = Math.floor(distance / (day)),
          document.getElementById("hours").innerText = Math.floor((distance % (day)) / (hour)),
          document.getElementById("minutes").innerText = Math.floor((distance % (hour)) / (minute)),
          document.getElementById("seconds").innerText = Math.floor((distance % (minute)) / second);

        //do something later when date is reached
        if (distance < 0) {
          let headline = document.getElementById("headline"),
              countdown = document.getElementById("countdown"),
              subheadline = document.getElementById("subheadline");

          subheadline.style.display ="";
          countdown.style.display = "none";
          document.getElementById("headline").innerText = "As registration rules have been changed, it will be live Tomorrow!!";
          document.getElementById("subheadline").innerText = "!Sorry for the incovenience!";

          clearInterval(x);
        }
        //seconds
      }, 0)
  }());