import React, {Component} from 'react';
import Search from "antd/es/input/Search";




class SearchBar extends Component {
    changeColorToGreen() {
        // console.log('here');
        // if(document.getElementById('top_bar') != null){
        //
        //     let search_button = document.getElementById('top_bar').childNodes[0].childNodes[1];
        //     search_button.style.borderTop = ' 2px solid #15B761';
        //     search_button.style.borderRight = ' 2px solid #15B761';
        //     search_button.style.borderLeft = ' 2px solid #15B761';
        // }else{
        //     console.log('bug');
        //
        // }
    }


    render() {
        return (
            <Search id={'search_input'}
                    placeholder="Looking for something?"
                    allowClear={false}
                    onMouseEnter={this.changeColorToGreen()}
                    onFocus={this.changeColorToGreen()}
            />

        );
    }
}


export default SearchBar; 