// Multiple tabs --------------------------------------

document.getElementById("defaultOpen").click();

var letterTypeIdGlobal;

function showLetters(evt, letterTypeId) {
    // Declare all variables
    var i, tabcontent, tablinks;
    letterTypeIdGlobal = letterTypeId;

    fire_ajax_submit(letterTypeId, null, null);

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    document.getElementById(letterTypeId).style.display = "block";
    evt.currentTarget.className += " active";
}



// Modal window ----------------------------------------

// Get the modal
var modal = document.getElementById("myModal");

// Get the button that opens the modal
var btn = document.getElementById("myBtn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks the button, open the modal
btn.onclick = function() {
    modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}



// Load information clicking on the tab -----------------------

// Ajax
function fire_ajax_submit(letterTypeId, size, pageNumber) {
   var criteria = {}
   criteria["id"] = letterTypeId;
   criteria["size"] = size;
   criteria["page"] = pageNumber;

    var tabcontent = document.getElementById(letterTypeId);
    var table = tabcontent.getElementsByTagName("table").item(0);

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

function displayLetters(table, data) {
    table.innerHTML = "";

    if (data.msg !== "Success") {
        table.innerHTML = data.msg;
        return;
    }

    var result = "<tr>\n" +
        "<th>Name</th>\n" +
        "<th>Due Date</th>\n" +
        "<th>Status</th>\n" +
        "<th>Location</th>\n" +
        "<th>Action</th>\n" +
        "</tr>";

    $.each(data.result.content, function(index,value){
        result += "<tr><td>" + value.name + "</td>" +
            "<td>" + value.dueDate + "</td>" +
            "<td>" + value.letterStatus + "</td>" +
            "<td>" + value.city + "</td>" +
            "<td><a href='/main/letter/edit?id=" + value.id + "'>Edit</a> | <a class='approve' onclick='approveGeneratedLetter(event, " + value.id + ")' >Approve</a></td></tr>";
    });

    if (data.result.length === 0) result = data.msg;
    table.innerHTML = result;
}

function approveGeneratedLetter(event, letterId) {
    var criteria = {}
    criteria["id"] = letterId;

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/main/approveLetter",
        data: JSON.stringify(criteria),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            fire_ajax_submit(letterTypeIdGlobal, null, null);
            console.log("SUCCESS : ", data);
        },
        error: function (e) {
            // table.innerHTML = "<span style='color:red;'>Something went wrong!!!</span>"
            console.log("ERROR : ", e);
        }
    });
}

function displayPaginator(data) {
    var letterPage = data.result;
    var paginator = document.getElementById("paginator");
    paginator.innerHTML = "";
    if (letterPage.totalPages > 0) {
        $.each(data.pageNumbers, function(index,value){
            var className = "";
            if (value === letterPage.number + 1) {
                className = "class='active'";
            }
            paginator.innerHTML +=
                "<a onclick=\"clickPagination(event," + letterPage.size + "," + value + ");\" " + className + ">"
                + value + "</a>";
        });
    }
}

function clickPagination(event, size, pageNumber) {
    fire_ajax_submit(letterTypeIdGlobal, size, pageNumber);
}