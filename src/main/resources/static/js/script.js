// Default tab
document.getElementById("defaultOpen").click();

// Global variable for the selected letter type
var selectedLetterTypeId;

// Multiple tabs --------------------------------------
function showLetters(evt, letterTypeId) {
    // Hide all tab content
    var tabContents = document.getElementsByClassName("tabcontent");
    for (var i = 0; i < tabContents.length; i++) {
        tabContents[i].style.display = "none";
    }

    // Remove 'active' class from all tab links
    var tabLinks = document.getElementsByClassName("tablinks");
    for (var i = 0; i < tabLinks.length; i++) {
        tabLinks[i].classList.remove("active");
    }

    // Show the selected tab and set it as active
    var selectedTabContent = document.getElementById(letterTypeId);
    selectedTabContent.style.display = "block";
    evt.currentTarget.classList.add("active");

    // Update the global selectedLetterTypeId variable
    selectedLetterTypeId = letterTypeId;

    // Load data for the selected tab
    fireAjaxSubmit(letterTypeId, null, null);
}


// Modal window

// Get the modal
var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function () {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function () {
    closeModal();
};

// When the user clicks anywhere outside of the modal, close it
window.onclick = function (event) {
    if (event.target == modal) {
        closeModal();
    }
};

function closeModal() {
    modal.style.display = "none";
    setDefaultLocations();
}

// AJAX function to load data for a tab
function fireAjaxSubmit(letterTypeId, size, pageNumber) {
    var criteria = {
        id: letterTypeId,
        size: size,
        page: pageNumber
    };

    var tabContent = document.getElementById(letterTypeId);
    var table = tabContent.querySelector("table");

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/main/letterType",
        data: JSON.stringify(criteria),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            displayLetters(table, data);
            displayPaginator(data);
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            table.innerHTML = "<span style='color:red;'>Something went wrong!!!</span>"
            console.log("ERROR : ", e);
        }
    });
}

// Function to display letters in a table
function displayLetters(table, data) {
    table.innerHTML = "";

    if (data.msg !== "Success") {
        table.innerHTML = data.msg;
        return;
    }

    let result = "<tr>\n" +
        "<th>Name</th>\n" +
        "<th>Due Date</th>\n" +
        "<th>Status</th>\n" +
        "<th>Location</th>\n" +
        "<th>Action</th>\n" +
        "</tr>";

    $.each(data.result.content, function (index, value) {
        result += "<tr><td>" + value.name + "</td>" +
            "<td>" + value.dueDate + "</td>" +
            "<td>" + value.letterStatus + "</td>" +
            "<td>" + value.city + "</td>" +
            "<td><a href='/main/letter/edit?id=" + value.id + "'>Edit</a> | <a class='approve' onclick='approveGeneratedLetter(event, " + value.id + ")' >Approve</a></td></tr>";
    });

    if (data.result.length === 0) result = data.msg;
    table.innerHTML = result;
}

// Function to approve a generated letter
function approveGeneratedLetter(event, letterId) {
    let criteria = {
        id: letterId
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/main/approveLetter",
        data: JSON.stringify(criteria),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            fireAjaxSubmit(selectedLetterTypeId, null, null);
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            console.log("ERROR : ", e);
        }
    });
}

// Function to display pagination links
function displayPaginator(data) {
    let letterPage = data.result;
    let paginator = document.getElementById("paginator");
    paginator.innerHTML = "";

    if (letterPage.totalPages > 0) {
        $.each(data.pageNumbers, function (index, value) {
            var className = value === letterPage.number + 1 ? "class='active'" : "";
            paginator.innerHTML +=
                "<a onclick=\"clickPagination(event," + letterPage.size + "," + value + ");\" " + className + ">"
                + value + "</a>";
        });
    }
}

function clickPagination(event, size, pageNumber) {
    fireAjaxSubmit(selectedLetterTypeId, size, pageNumber);
}

const checkbox = document.getElementById('checkedAll')
checkbox.addEventListener('change', (event) => {
    var modalContent = document.querySelector(".modal-content");
    var inputs = modalContent.querySelectorAll("input[type='checkbox']");
    var isChecked = event.currentTarget.checked;

    for (var i = 1; i < inputs.length; i++) {
        inputs[i].checked = isChecked;
    }
});

$(document).ready(function ($) {
    $(document).on('submit', '#locationForm', function (event) {
        event.preventDefault();

        var citiesJson = {cities: []};

        var locationForm = document.getElementById("locationForm");
        var inputs = locationForm.querySelectorAll("input[type='checkbox']");

        for (var i = 0; i < inputs.length; i++) {
            if (inputs[i].checked && inputs[i].value !== 'All') {
                citiesJson.cities.push(inputs[i].value);
            }
        }

        citiesJson.id = selectedLetterTypeId;

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/main/lettersByCities",
            data: JSON.stringify(citiesJson),
            dataType: "json",
            cache: false,
            timeout: 600000,
            success: function (data) {
                let tabContent = document.getElementById(selectedLetterTypeId);
                let table = tabContent.querySelector("table");
                displayLetters(table, data);
                displayPaginator(data);
                console.log(data);
            },
        });
        modal.style.display = "none";
    });
});

$(document).ready(function ($) {
    $(document).on('reset', '#locationForm', function (event) {
        event.preventDefault();
        setDefaultLocations();
    });
});

function setDefaultLocations() {
    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "/main/locationsDefault",
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            console.log(data);
            var modalContent = document.querySelector(".modal-content");
            var inputs = modalContent.querySelectorAll("input[type='checkbox']");

            inputs.forEach(function (input) {
                input.checked = data.cities.includes(input.value);
            })
        },
        error: function (e) {
            // table.innerHTML = "<span style='color:red;'>Something went wrong!!!</span>"
            console.log("ERROR : ", e);
        }
    });
}

