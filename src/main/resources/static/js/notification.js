setNotification();

function clickNotification(){
    let notificationId = $(this).data('id');
    ajaxGetRequest('/notification/confirm/'+notificationId, {}, function (result){
        setNotification();
        $('#notificationDocModal').modal('show');

        $('#notificationRequestTextArea').val(result.requestMessage);
        $('#notificationTitleInput').val(result.responseTitle);
        $('#notificationMessageTextArea').val(result.responseMessage);
    });
}

function setNotification(){
    ajaxGetRequest('/notification/recent', {}, function(result){
        $('#notificationList').empty();
        $.each(result, function(index, el){
            let li = $('<li>\n' +
                '        <a href="#">\n' +
                '         <div class="notification-content">\n' +
                '          <small class="notification-timestamp pull-right">' + el.createDate.substring(0, 10) + '</small>\n' +
                '          <div class="notification-heading">요청 사항</div>\n' +
                '          <div class="notification-text">' + el.requestMessage + '</div>\n' +
                '         </div>\n' +
                '        </a>\n' +
                '       </li>');

            li.data('id', el.notificationId);
            li.on('click', clickNotification);
            $('#notificationList').append(li);
        })
    }, null);
}