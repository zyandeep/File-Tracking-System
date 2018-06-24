$(document).ready(function () {

    // fire an AJAX request for autocomplete file names
    $('#name').autocomplete({
        autoFocus: true,
        source: function (request, response) {
            $.ajax({
                url: '/FTS/admin/pages/nameSuggesstion.do',
                data: {term: request.term},
                dataType: 'json',
                cache: false,
                success: function (data) {
                    response(data);
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert("Error in the Database connection!");
                }

            });
        }
    });
});
