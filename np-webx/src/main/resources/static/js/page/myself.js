function addSpan(obj) {
    document.getElementById(obj).className = "glyphicon glyphicon-trash";
}

function rmSpan(obj) {
    document.getElementById(obj).className = "";
}

function removes(lyricsid, userlyricsid) {
    art.dialog.confirm('你确定要删除这个最爱主题吗？', function () {
        window.location.href = "/lyrics/remove/" + lyricsid + "/" + userlyricsid;
    }, function () {
        return;
    });
}
