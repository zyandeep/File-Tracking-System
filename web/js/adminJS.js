function myFunction() {

    // handler for page printing 
    $("#printLink").on("click", function () {
        var data = $("#printData").html();
        var myWindow = null;
        var strWindowFeatures = "height=400, width=600, resizable=yes, scrollbars=yes, location=no, status=no";
        var windowTitle = $("title").text();

        myWindow = window.open(" ", " ", strWindowFeatures);

        myWindow.document.write("<html><head><title>" + windowTitle + "</title>");
        myWindow.document.write("<link type='text/css' rel='stylesheet' href='/FTS/css/print.css' />");
        myWindow.document.write("</head><body>");
        myWindow.document.write("<div id='noPrint' align='right'>");
        myWindow.document.write("<button onclick='window.print()'> Print this page </button>");
        myWindow.document.write("<hr />");
        myWindow.document.write("<br />");
        myWindow.document.write("</div>");
        myWindow.document.write(data);
        myWindow.document.write("</body></html>");

        myWindow.document.close(); // necessary for IE >= 10
        myWindow.focus(); // necessary for IE >= 10
    });

    // handle for the delete file-type action
    $(document.forms.deleteFileType).on("submit", function () {

        var ans = window.confirm('Do you want to delete file-type "' + $(this.fileType).val() + '" ?');

        return ans;

    });

    // handle for the delete branch action
    $(".deleteBranch").on("submit", function () {

        var tds = $(this).parent().siblings();
        var ans = window.confirm('Do you want to delete branch "' + $(tds).eq(1).text() + '" ?');

        return ans;

    });

    // handle for admin registration form
    $(document.forms.adminRegFrom).on("submit", function () {
        var ans = true;

        var username = $(this.username).val();
        var password = $(this.password).val();
        var confPass = $(this.confPass).val();

        if (username === "" || password === "" || confPass === "") {
            $("#errorMesssage").html("Form fileds can't be empty").addClass("errMessage");
            ans = false;
        }
        else if (password !== confPass) {
            $("#errorMesssage").html("New and Confirm passwords must be matched").addClass("errMessage");
            ans = false;
        }


        return ans;
    });

    // handle for admin login form
    $(document.forms.adminLoginFrom).on("submit", function () {
        var ans = true;

        var username = $(this.username).val();
        var password = $(this.password).val();

        if (username === "" || password === "") {
            $("#errorMesssage").html("Form fileds can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for branch registration form
    $(document.forms.branchRegFrom).on("submit", function () {
        var ans = true;

        var bn = $(this.branchName).val();
        var hn = $(this.headName).val();
        var username = $(this.username).val();
        var password = $(this.password).val();
        var confPass = $(this.confPass).val();

        if (bn === "" || hn === "" || username === "" || password === "" || confPass === "") {
            $("#errorMesssage").html("Form fields can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for the add file-type form
    $(document.forms.addFileType).on("submit", function () {
        var ans = true;

        if ($(this.fileType).val() === "") {
            $("#errorMesssage").html("Please enter a file type").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for admin password change form
    $(document.forms.adminPwdChForm).on("submit", function () {
        var ans = true;

        var oldPass = $(this.oldPass).val();
        var newPass = $(this.newPass).val();
        var confPass = $(this.confPass).val();

        if (oldPass === "" || newPass === "" || confPass === "") {
            $("#errorMesssage").html("Form fields can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for update branch data form
    $(document.forms.modifyBranchForm).on("submit", function () {
        var ans = true;

        var bn = $(this.branchName).val();
        var hn = $(this.headName).val();
        var un = $(this.username).val();
        var np = $(this.newPass).val();
        var cp = $(this.confPass).val();

        if (bn === "" || hn === "" || un === "") {
            $("#errorMesssage").html("Form fileds can't be empty").addClass("errMessage");
            ans = false;
        }
        else if (np !== cp) {
            $("#errorMesssage").html("New and Confirm passwords must be matched").addClass("errMessage");
            ans = false;
        }

        return ans;
    });


    // handle for track by branch form
    $(document.forms.trackByBranchForm).on("submit", function () {

        if ($(this.branch).val() === null) {
            $("#errorMesssage").html("Please select a branch").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by DoR form
    $(document.forms.dataOfRecieveForm).on("submit", function () {

        if ($(this.dor).val() === "") {
            $("#errorMesssage").html("Please enter a date with the given format").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by DoS form
    $(document.forms.dateOfSendForm).on("submit", function () {

        if ($(this.dos).val() === "") {
            $("#errorMesssage").html("Please enter a date with the given format").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by ID form
    $(document.forms.idForm).on("submit", function () {

        if ($(this.id).val() === "") {
            $("#errorMesssage").html("Please enter a file ID").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by Name form
    $(document.forms.nameForm).on("submit", function () {

        if ($(this.name).val() === "") {
            $("#errorMesssage").html("Please enter a file name").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by No form
    $(document.forms.noFrom).on("submit", function () {

        if ($(this.no).val() === "") {
            $("#errorMesssage").html("Please enter a file no").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

}


// call the function when the DOM is ready
$(document).ready(myFunction);

