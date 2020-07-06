var input_count = 0
function add(){

     var parent_object = document.getElementById("piyo");
     parent_object.appendChild(ele);
    //id="alertString1"であるhidden項目の値を取得し、
    //ダイアログを表示
    ret = prompt("URLを入力", "http://www.google.co.jp/");
    ret = prompt("URLを入力", "http://www.google.co.jp/");
    if (ret != null) {
        location.href = ret;
    }
    var alertStr = document.getElementById("alertString1").value;
    alert(alertStr);
}