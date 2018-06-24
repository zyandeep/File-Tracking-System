$(document).ready(function () {

    // handle for file registration form
    $(document.forms.fileRegForm).on("submit", function () {

        var ans = true;

        var fileNo = $(this.fileNo).val();
        var fileName = $(this.fileName).val();
        var fileType = $(this.fileType).val();

        if (fileNo === "" || fileName === "" || fileType === "") {
            $("#errorMesssage").html("Form fields can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });


    // handle for file reject form
    $(".rejectForm").on("submit", function () {
        var ans = true;

        var cause = $(this.textArea).val();

        if (cause === "") {
            $("#errorMesssage").html("You must give the cause of rejection").addClass("errMessage");
            ans = false;
        }

        return ans;
    });


    // handle for change file status form
    $(".changeStatusForm").on("submit", function () {
        var ans = true;

        var fileStatus = $(this.fileStatus).val();

        if (fileStatus === null) {
            console.log("error");
            $("#errorMesssage").html("Please select file status").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for send file form
    $(".sendFileForm").on("submit", function () {
        var ans = true;

        var branchToForward = $(this.branchToForward).val();

        if (branchToForward === null) {
            $("#errorMesssage").html("Please select a branch").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for edit file form
    $(document.forms.fileEditForm).on("submit", function () {
        var ans = true;

        var fileNo = $(this.no).val();
        var fileName = $(this.name).val();
        var fileType = $(this.type).val();

        if (fileNo === "" || fileName === "" || fileType === "") {
            $("#errorMesssage").html("Form fields can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

    // handle for branch password change form
    $(document.forms.passChangeForm).on("submit", function () {
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

    // handle for track by DoR form
    $(document.forms.trackDoRForm).on("submit", function () {

        if ($(this.dor).val() === "") {
            $("#errorMesssage").html("Please enter a date with the given format").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by DoS form
    $(document.forms.trackDoSForm).on("submit", function () {

        if ($(this.dos).val() === "") {

            $("#errorMesssage").html("Please enter a date with the given format").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by ID form
    $(document.forms.trackIdForm).on("submit", function () {

        if ($(this.id).val() === "") {
            $("#errorMesssage").html("Please enter a file ID").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by Name form
    $(document.forms.trackNameForm).on("submit", function () {

        if ($(this.name).val() === "") {
            $("#errorMesssage").html("Please enter a file name").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for track by No form
    $(document.forms.trackNoForm).on("submit", function () {

        if ($(this.no).val() === "") {
            $("#errorMesssage").html("Please enter a file no").addClass("errMessage");
            return false;
        }
        else {
            return true;
        }

    });

    // handle for admin login form
    $(document.forms.branchLoginFrom).on("submit", function () {
        var ans = true;

        var username = $(this.username).val();
        var password = $(this.password).val();

        if (username === "" || password === "") {
            $("#errorMesssage").html("Form fileds can't be empty").addClass("errMessage");
            ans = false;
        }

        return ans;
    });

});
