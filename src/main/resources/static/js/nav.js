document.getElementById("enterKeyword").addEventListener("keydown", function (e) {
    if(e.key === "Enter") {
        search();
    }
})
function search() {
    let k = document.getElementById("enterKeyword").value;

    if(k.trim().length !== 0)
        location.href = '/program/search?keyword=' + k;
    else
        alert("검색어를 입력해주세요");
}