function inputEventHandlers(){

    document.addEventListener("DOMContentLoaded", function() {
        const assignValue = document.getElementById("assign-input-id");
        const dateDueValue = document.getElementById("dateDue-input-id");
        const availableValue = document.getElementById("available-input-id");

        alert("TEST")

        assignValue.addEventListener("input", function() {
            sendDataToController(assignValue.value);
        })

        dateDueValue.addEventListener("input", function() {
            sendDataToController(dateDueValue.value);
        });

        availableValue.addEventListener("input", function() {
            sendDataToController(availableValue.value);
        });

    });
    function sendDataToController(data) {

        fetch('/save-data', {
            method: 'POST',
            body: JSON.stringify({ data: data }),
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

}