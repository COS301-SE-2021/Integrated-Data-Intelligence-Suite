import datapoints from "../resources/graphStructures/mapDatapoints.json"
import {forEach} from "react-bootstrap/ElementChildren";

export function showCircleData(clicked_circle_class_name, json_data_from_backend) {
    // console.log("I am new function that's running on click");
    //Find the all statistic fields in the frontend UI
    const array_of_statistic_fields = document.querySelectorAll("td.ant-descriptions-item.map_1 " +
        "> div.ant-descriptions-item-container" +
        " > span.ant-descriptions-item-content"
    );
    console.log("X-brooo:" + array_of_statistic_fields);


    //Find element with same classname in the json_data_from_backend
    let circle_obj_with_data = json_data_from_backend.find(x => x.classname === clicked_circle_class_name);
    console.log(circle_obj_with_data);


    //Changing the Value of each Statistic field in the frontend
    //x[statistic number]
    array_of_statistic_fields[0].innerHTML = circle_obj_with_data.statistic_1;
    array_of_statistic_fields[1].innerHTML = circle_obj_with_data.statistic_2;
    array_of_statistic_fields[2].innerHTML = circle_obj_with_data.statistic_3;

    let detailsbar = document.getElementById("map_card_sidebar");
    detailsbar.style.display = "block";


    //Change Color of the Clicked Circle

    //Remove The color from the previously clicked circle(if any)
    const array_prev_clicked_circle = document.getElementsByClassName('chosen_circle');
    if (array_prev_clicked_circle.length > 0) {
        array_prev_clicked_circle[0].style.fill = 'red';
        array_prev_clicked_circle[0].style.fillopacity = 0.2;
        array_prev_clicked_circle[0].classList.remove('chosen_circle');
    }


    let clicked_circle_element = document.getElementsByClassName(clicked_circle_class_name);
    clicked_circle_element[0].classList.add('chosen_circle');

}