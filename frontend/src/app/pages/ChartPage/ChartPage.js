import React, { Component } from 'react';
import {
    Layout,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AiOutlineUpload, CgFileDocument } from 'react-icons/all';
import SideBar from '../../components/SideBar/SideBar';
import MapCard from '../../components/MapCard/MapCard';
import NetworkGraphCard from '../../components/NetworkGraph/NetworkGraphCard';
import '../../components/NetworkGraph/NetworkGraph.css';
import UserInfoCard from '../../components/UserInfoCard/UserInfoCard';
import SearchBar from '../../components/SearchBar/SearchBar';
import TimelineGraph from '../../components/TimelineGraph/TimelineGraph';
import PieChart from '../../components/PieChart/PieChart';
import '../../components/WordCloud/WordCloud.css';
import WordCloud from '../../components/WordCloud/WordCloud';
import 'rc-slider/assets/index.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import DraggableBarGraph from '../../components/DraggableBarGraph/DraggableBarGraph';
import OverviewSection from '../../components/OverviewSection/OverviewSection';
import OverviewGraphSection from '../../components/OverviewGraphSection/OverviewGraphSection';
import SimplePopup from '../../components/SimplePopup/SimplePopup';
import '../../components/UploadButton/UploadButton.css';
import UploadSchemaForm from '../../components/UploadSchemaForm/UploadSchemaForm';
import UploadDataPage from '../UploadDataPage/UploadDataPage';
import ReportPreview from '../../components/ReportPreview/ReportPreview';
import pdfTemplate from '../../Mocks/pdf';

const {
    Header,
} = Layout;

function retrieveData() {
    fetch(`${process.env.REACT_APP_BACKEND_HOST}/retrievePrevious`)
        .then((res) => {
            if (!res.ok) {
                return null;
            }
            return res.json();
        });
}

function getLocalUser() {
    const localUser = localStorage.getItem('user');
    if (localUser) {
        return JSON.parse(localUser);
    }
    return null;
}

class ChartPage extends Component {
    constructor(props) {
        super(props);
        this.handleTextChange = this.handleTextChange.bind(this);
        this.state = {
            text: '',
            isShowingPopup: false,
            showPdf: false,
            currentPdf: null,
        };
        this.state.user = getLocalUser();
        this.state.tempPdf = pdfTemplate();
        this.showPopup = this.showPopup.bind(this);
        this.generateReport = this.generateReport.bind(this);
    }

    handleTextChange(newText) {
        this.setState(({ text: newText }));
    }

    showPopup() {
        // this.setState(({ isShowingPopup: !this.state.isShowingPopup }));
        this.setState(
            (prevState) => ({
                isShowingPopup: !prevState.isShowingPopup,
            }),
            () => console.log(`isPopupShowing: ${this.state.isShowingPopup}`),
        );
    }

    disablePreview() {
        this.setState({
            showPdf: false,
        });
    }

    generateReport(id, pdf, user) {
        this.setState(() => ({
            currentPdf: {
                reportID: id,
                data: pdf,
                userID: user.id,
            },
            showPdf: true,
        }));
    }

    render() {
        const {
            user,
            showPdf,
            currentPdf,
            tempPdf,
        } = this.state;

        if (user) {
            return (
                <>
                    <Switch>
                        <Route exact path="/chart">
                            {
                                this.state.isShowingPopup
                                    ? (
                                        <SimplePopup
                                          closePopup={this.showPopup}
                                          popupTitle="Upload File"
                                        >
                                            <UploadDataPage />
                                        </SimplePopup>
                                    ) :
                                    null
                            }
                            {
                                showPdf
                                    ? (
                                        <ReportPreview
                                          closePopup={()=>this.disablePreview()}
                                          className="pdf"
                                          title="pdf-preview"
                                          currentFile={currentPdf}
                                        />
                                    ) :
                                    null
                            }

                            <Layout
                              id="outer_layout"
                              className="chart-page"
                            >
                                <SideBar currentPage="2" />
                                <Layout id="inner_layout_div">
                                    <Header id="top_bar">
                                        <SearchBar
                                          text={this.state.text}
                                          handleTextChange={this.handleTextChange}
                                        />

                                        <button
                                          id="upload-btn"
                                          className="clickable"
                                          onClick={() => {
                                                this.showPopup(true);
                                            }}
                                        >
                                            <AiOutlineUpload id="upload-btn-logo" />
                                            Upload
                                        </button>

                                        <button
                                          type="button"
                                          id="upload-btn"
                                          className="clickable"
                                          onClick={() => {
                                                this.generateReport(
                                                    this.state.text[this.state.text.length - 1][0].id,
                                                    this.state.text[this.state.text.length - 1][0].pdf,
                                                    user,
                                                );
                                            }}
                                        >
                                            <CgFileDocument id="upload-btn-logo" />
                                            Generate Report
                                        </button>

                                        <UserInfoCard
                                          name="s"
                                        />
                                    </Header>

                                    <div id="content-section">
                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-1"
                                        >
                                            <OverviewSection
                                              text={this.state.text}
                                              key={this.state.text}
                                            />
                                        </SimpleSection>

                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-2"
                                        >
                                            <OverviewGraphSection
                                              text={this.state.text}
                                              key={this.state.text}
                                            />
                                        </SimpleSection>

                                        {/* /!* */}
                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-3"
                                        >
                                            <div id="location-section">
                                                <div id="map-metric-container">
                                                    <SimpleCard
                                                      cardTitle=""
                                                      cardID="world-map"
                                                      titleOnTop
                                                    >
                                                        <MapCard text={this.state.text} />
                                                    </SimpleCard>

                                                    <SimpleCard
                                                      cardTitle="Data Frequency"
                                                      cardID="map-metric-1"
                                                      titleOnTop
                                                    >
                                                        <DraggableBarGraph text={this.state.text} />
                                                    </SimpleCard>
                                                </div>
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-4"
                                        >
                                            <SimpleCard
                                              cardTitle="Word Cloud"
                                              cardID="word-cloud-card"
                                              titleOnTop
                                            >
                                                <WordCloud
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                />
                                            </SimpleCard>

                                            <div id="word-cloud-graph-container">
                                                <SimpleCard
                                                  cardTitle="Dominant words"
                                                  cardID="word-graph-2"
                                                  titleOnTop
                                                >
                                                    <PieChart text={this.state.text} />
                                                </SimpleCard>
                                                {/* <SimpleCard
                                                    cardTitle="Word Sunburst"
                                                    cardID="word-graph-1"
                                                >
                                                    Word Graph1
                                                </SimpleCard> */}
                                            </div>
                                        </SimpleSection>

                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-5"
                                        >
                                            <SimpleCard
                                              cardTitle="Relationship Between Entities"
                                              cardID="network-graph-entities"
                                              titleOnTop
                                            >
                                                <NetworkGraphCard
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                  indexOfData={11}
                                                />
                                            </SimpleCard>

                                            <SimpleCard
                                              cardTitle="Relationship Between Patterns"
                                              cardID="network-graph-patterns"
                                              titleOnTop
                                            >
                                                <NetworkGraphCard
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                  indexOfData={12}
                                                />
                                            </SimpleCard>
                                        </SimpleSection>

                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-6"
                                        >
                                            <SimpleCard
                                              cardTitle="Timeline"
                                              cardID="anomaly-timeline-card"
                                              titleOnTop
                                            >
                                                <TimelineGraph
                                                  text={this.state.text}
                                                  key={this.state.text}
                                                />
                                            </SimpleCard>

                                            {/* <SimpleCard */}
                                            {/*    cardTitle="Scatter Plot" */}
                                            {/*    cardID="anomaly-scatter-plot" */}
                                            {/*    titleOnTop */}
                                            {/* > */}
                                            {/*    Scatter Plot */}
                                            {/* </SimpleCard> */}

                                            {/* <SimpleCard */}
                                            {/*    cardTitle="Line Graph" */}
                                            {/*    cardID="anomaly-line-graph" */}
                                            {/* > */}
                                            {/*    <GraphWithBrushAndZoom */}
                                            {/*        text={this.state.text} */}
                                            {/*        key={this.state.text} */}
                                            {/*    /> */}
                                            {/* </SimpleCard> */}
                                        </SimpleSection>
                                        {/** !/ */}
                                    </div>
                                </Layout>
                            </Layout>
                        </Route>
                    </Switch>
                </>
            );
        }
        return (
            <>
                <Redirect to="/login" />
            </>
        );
    }
}

export default ChartPage;
