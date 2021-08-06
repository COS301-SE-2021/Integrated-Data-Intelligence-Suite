export function showBlueCircleData() {
    console.log("I am new function that's running on click");
    const x = document.querySelectorAll("td.ant-descriptions-item.map_1 " +
        "> div.ant-descriptions-item-container" +
        " > span.ant-descriptions-item-content"
    );
    console.log("X-brooo:" + x);

    //Changing the Value of each Statistic
    x[0].innerHTML='blue';
    x[1].innerHTML='circle';
    x[2].innerHTML='data';
}