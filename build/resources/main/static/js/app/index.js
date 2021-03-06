var main ={
    init : function (){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });
        $('#btn-update').on('click', function(){
           _this.update();
        });

        $('#btn-delete').on('click', function(){
            _this.delete();
        });
    },

    save : function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/board',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (){
            alert('성공적으로 등록되었습니다.');
            window.location.href='/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });

    },

    update : function (){
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/board/' + id,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify(data)
        }).done(function (){
            alert("수정완료")
            window.location.href='/';
        }).fail(function (){
            alert(JSON.stringify(error));
        });
    },

    delete : function(){
        var id = $('#id').val();
        $.ajax({
            type:'DELETE',
            url:'/api/v1/board/' + id,
            contentType: 'application/json; charset=utf-8',
            dataType: 'json'
        }).done(function(){
            alert('삭제 성공')
            window.location.href='/';
        }).fail(function(){
            alert(JSON.stringify(error));
        });
    }

};

main.init();