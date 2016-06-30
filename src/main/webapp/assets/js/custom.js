function showNotification(message,type){
    var options = {
        'message': message,
        'type' : type
    };
    $('body').pgNotification(options).show();
}