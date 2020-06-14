$("#register-form").bind('submit', (function (event) {
    event.preventDefault(); //prevent default action
    var post_url = $(this).attr("action"); //get form action url
    var form_data = JSON.stringify(getFormData($(this))); //Encode form elements for submission
    console.log(form_data)
    $.ajax({
        url: getFullUrl(post_url),
        type: "POST",
        data: form_data,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (response) {
            window.location.href = "sign-in"

        },
        error: function (e) {
            var message = "Please check Input";
            if(isJsonString(e.responseText)) {
                message = JSON.parse(e.responseText).message;
            }
            $('#errormessagediv').text(message);
            console.log("error -", e.message);
        }
    })

}));

