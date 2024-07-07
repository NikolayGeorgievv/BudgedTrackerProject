function testJS(expenseId) {

    document.getElementById("expenseId1").value = expenseId;


    let weeklyBtn1 = document.getElementById("weekly1");
    let monthlyBtn1 = document.getElementById("monthly1");
    let yearlyBtn1 = document.getElementById("yearly1");
    let customBtn1 = document.getElementById("custom1");

    let contentDiv1 = document.getElementById("content1");
    let dateInputField1 = document.getElementById("dateInputField1");
    let datePicker1 = document.getElementById("datepicker1");
    let period1 = document.getElementById("period1");
    datePicker1.style.display = "none";

    weeklyBtn1.addEventListener("click", function () {
        period1.value = 'weekly';
        buttonsBgColourClear1()
        weeklyBtn1.style.backgroundColor = '#0099cc';
        contentDiv1.innerHTML = "";
        dateInputField1.value = "Sunday";
        dateInputField1.type = "text";
        datePicker1.style.display = "none";
        const daysOfWeek1 = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        daysOfWeek1.forEach(day => {
            const option1 = document.createElement('option');
            option1.textContent = day;
            contentDiv1.appendChild(option1);

        });
        contentDiv1.style.display = "block"
        contentDiv1.classList.toggle("show");


    });

    monthlyBtn1.addEventListener("click", function () {
        period1.value = 'monthly';
        buttonsBgColourClear1()
        monthlyBtn1.style.backgroundColor = '#0099cc';
        contentDiv1.innerHTML = "";
        dateInputField1.value = "Last day of month";
        dateInputField1.type = "text";
        datePicker1.style.display = "none";
        const datesOfMonth1 = ['Last day of month', '31th', '30th', '29th', '28th', '27th', '26th', '25th', '24th', '23th', '22nd'
            , '21st', '20th', '19th', '18th', '17th', '16th', '15th', '14th', '13th', '12th', '11th', '10th', '9th', '8th', '7th'
            , '6th', '5th', '4th', '3th', '2nd', '1st'];

        datesOfMonth1.forEach(day => {
            const option1 = document.createElement('option');
            option1.textContent = day;
            contentDiv1.appendChild(option1);

        });

        contentDiv1.style.display = "block"
        contentDiv1.classList.toggle("show");

    });

    contentDiv1.addEventListener("change", function () {
        dateInputField1.value = contentDiv1.options[contentDiv1.selectedIndex].text;
    })
    yearlyBtn1.addEventListener("click", function () {
        period1.value = 'yearly';
        buttonsBgColourClear1()
        yearlyBtn1.style.backgroundColor = '#0099cc';
        dateInputField1.value = "";
        contentDiv1.innerHTML = "";
        contentDiv1.style.display = "none";
        dateInputField1.type = "hidden";
        datePicker1.style.display = "block";
    });

    customBtn1.addEventListener("click", function () {
        period1.value = 'custom';
        buttonsBgColourClear1()
        customBtn1.style.backgroundColor = '#0099cc';
        dateInputField1.value = "";
        contentDiv1.innerHTML = "";
        contentDiv1.style.display = "none";
        dateInputField1.type = "hidden";
        datePicker1.style.display = "block";
    });

    function buttonsBgColourClear1() {
        weeklyBtn1.style.backgroundColor = 'white';
        monthlyBtn1.style.backgroundColor = 'white';
        yearlyBtn1.style.backgroundColor = 'white';
        customBtn1.style.backgroundColor = 'white';
    }

    let informationMark = document.getElementById("editExpenseInfo");
    informationMark.addEventListener("mouseover", function () {
        document.getElementById("editExpenseModalLabel").style.color = "#01154A"
    })
    informationMark.addEventListener("mouseout", function () {
        document.getElementById("editExpenseModalLabel").style.color = "white";
    })
}

