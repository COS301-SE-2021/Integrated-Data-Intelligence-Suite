export function showBlueCircleData() {
    console.log("I am new function that's running on click");
    const x = document.querySelectorAll("td.ant-descriptions-item.map_1 " +
        "> div.ant-descriptions-item-container" +
        " > span.ant-descriptions-item-content"
    );
    console.log("X-brooo:" + x);

    //Changing the Value of each Statistic
    x[0].innerHTML='bro';
    x[1].innerHTML='did this';
    x[2].innerHTML='work?';
}