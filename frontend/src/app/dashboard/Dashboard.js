import React, { Component }from 'react';

class Dashboard extends Component {
    render() {
        return (
            <div>
                <div className="w3-overlay w3-hide-large w3-animate-opacity" onClick={this.w3_open} style={{cursor:'pointer'}}
                     title="close side menu" id="myOverlay">
                </div>

                <div className="w3-main" style={{marginLeft:'300px',marginTop:'43px'}}>

                    <header className="w3-container" style={{paddingTop :'22px'}}>
                        <h5><b><i className="fa fa-dashboard"></i> My Dashboard</b></h5>
                    </header>

                    <div className="w3-row-padding w3-margin-bottom">
                        <div className="w3-quarter">
                            <div className="w3-container w3-red w3-padding-16">
                                <div className="w3-left"><i className="fa fa-comment w3-xxxlarge"></i></div>
                                <div className="w3-right">
                                    <h3>52</h3>
                                </div>
                                <div className="w3-clear"></div>
                                <h4>Messages</h4>
                            </div>
                        </div>
                        <div className="w3-quarter">
                            <div className="w3-container w3-blue w3-padding-16">
                                <div className="w3-left"><i className="fa fa-eye w3-xxxlarge"></i></div>
                                <div className="w3-right">
                                    <h3>99</h3>
                                </div>
                                <div className="w3-clear"></div>
                                <h4>Views</h4>
                            </div>
                        </div>
                        {/* <div className="w3-quarter">
                            <div className="w3-container w3-teal w3-padding-16">
                                <div className="w3-left"><i className="fa fa-share-alt w3-xxxlarge"></i></div>
                                <div className="w3-right">
                                    <h3>23</h3>
                                </div>
                                <div className="w3-clear"></div>
                                <h4>Shares</h4>
                            </div>
                        </div> */}
                        <div className="w3-quarter">
                            <div className="w3-container w3-orange w3-text-white w3-padding-16">
                                <div className="w3-left"><i className="fa fa-users w3-xxxlarge"></i></div>
                                <div className="w3-right">
                                    <h3>50</h3>
                                </div>
                                <div className="w3-clear"></div>
                                <h4>Users</h4>
                            </div>
                        </div>
                    </div>

                    <hr/>
                    <div className="w3-container">
                        <h5>General Stats</h5>
                        <p>New Visitors</p>
                        <div className="w3-grey">
                            <div className="w3-container w3-center w3-padding w3-green" style={{width:'25%'}}>25%</div>
                        </div>

                        <p>New Users</p>
                        <div className="w3-grey">
                            <div className="w3-container w3-center w3-padding w3-orange" style={{width:'50%'}}>50%</div>
                        </div>

                        <p>Search Rate</p>
                        <div className="w3-grey">
                            <div className="w3-container w3-center w3-padding w3-red" style={{width:'75%'}}>75%</div>
                        </div>
                    </div>

                    <hr/>
                    <div className="w3-container">
                        <h5>Trending keywords</h5>
                        <table className="w3-table w3-striped w3-bordered w3-border w3-hoverable w3-white">
                            <tbody>
                                <tr>
                                    <td>United States</td>
                                    <td>65%</td>
                                </tr>
                                <tr>
                                    <td>Chille</td>
                                    <td>15.7%</td>
                                </tr>
                                <tr>
                                    <td>Swimpool</td>
                                    <td>5.6%</td>
                                </tr>
                                <tr>
                                    <td>Swimpool accident</td>
                                    <td>2.1%</td>
                                </tr>
                                <tr>
                                    <td>India</td>
                                    <td>1.9%</td>
                                </tr>
                                <tr>
                                    <td>Covid</td>
                                    <td>1.5%</td>
                                </tr>
                            </tbody>
                        </table>
                        <br/>
                        <button className="w3-button w3-dark-grey">More Countries
                            <i className="fa fa-arrow-right"></i>
                        </button>
                    </div>

                </div>
            </div>
        )
    }
}

export default Dashboard;