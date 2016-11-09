document.body.addEventListener("click", function(event) {
    var request = new XMLHttpRequest();
    request.open("GET", "http://127.0.0.1:8888/example", true);
    request.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText)
            data = JSON.parse(this.responseText);
            console.log(data);
        }
    };
    request.send();
})