$("#signin-form").bind('submit', (function(event){
    $('#errormessagediv').html("");
    event.preventDefault(); //prevent default action
    var post_url = $(this).attr("action"); //get form action url
    var form_data = getFormData($(this));
    console.log(form_data)
    $.ajax({
        url:getFullUrl(post_url),
        type:"POST",
        data:JSON.stringify(form_data),
        contentType:"application/json; charset=utf-8",
        headers: {
            'Authorization': 'Basic ' + btoa(form_data.email + ":" + form_data.password)
        },
        dataType:"json",
        success: function(response){
            console.log("data was good");
            window.location.href = "dashboard"
        },
        error : function (e) {
            var message = "Please check Input";
            if(isJsonString(e.responseText)) {
                message = JSON.parse(e.responseText).message;
            }
            $('#errormessagediv').text(message);
            console.log("error -", e.message);
        }
    })

}));

