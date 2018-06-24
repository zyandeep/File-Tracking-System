// fire an AJAX request for autocomplete file names
var ajaxCall = function () {
    
    $('#name').autocomplete({
        autoFocus: true,
        source: function (request, response) {
            $.ajax({
                url: '/FTS/branch/nameSuggestion.do',
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
}; 

// call this function when the DOM is ready
$(document).ready(ajaxCall);


