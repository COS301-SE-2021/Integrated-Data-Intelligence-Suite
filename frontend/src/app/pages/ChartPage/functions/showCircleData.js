import datapoints from "../resources/graphStructures/mapDatapoints.json"
export function showCircleData(clicked_circle_class_name, json_data_from_backend) {
    // console.log("I am new function that's running on click");
    //Find the all statistic fields in the frontend UI
    const x = document.querySelectorAll("td.ant-descriptions-item.map_1 " +
        "> div.ant-descriptions-item-container" +
        " > span.ant-descriptions-item-content"
    );
    console.log("X-brooo:" + x);


    //Find element with same classname in the json_data_from_backend
    let circle_obj_with_data = json_data_from_backend.find(x => x.classname === clicked_circle_class_name);
    console.log(circle_obj_with_data);


    //Changing the Value of each Statistic field
    //x[statistic number]
    x[0].innerHTML= circle_obj_with_data.statistic_1;
    x[1].innerHTML= circle_obj_with_data.statistic_2;
    x[2].innerHTML= circle_obj_with_data.statistic_3;

    let detailsbar = document.getElementById("map_card_sidebar")
    detailsbar.style.display="block";
}