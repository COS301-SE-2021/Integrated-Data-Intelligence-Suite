import React, { Component, useState } from 'react';
import {
    Layout,
} from 'antd';
import { Redirect, Route, Switch } from 'react-router-dom';
import { AiOutlineUpload, CgFileDocument } from 'react-icons/all';
import { Header } from 'antd/es/layout/layout';
import {
 useRecoilState, useRecoilValue, useResetRecoilState, useSetRecoilState,
} from 'recoil';
import Search from 'antd/es/input/Search';
import { reset } from 'enzyme/build/configuration';
import { cloneDeep } from 'lodash';
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
import templateJson from '../../Mocks/messageMock.json';
import {
    backendDataState,
    currentPdfState,
    userState,
    totalLikedState,
    mostProminentSentimentState,
    numberOfTrendsState,
    numberOfAnomaliesState,
    averageInteractionState,
    overallSentimentState,
    engagementPerProvinceState,
    mapDataState,
    dataFrequencyState,
    wordCloudState,
    dominantWordsState,
    entitiesRelationshipsState,
    patternsRelationshipsState,
    anomaliesState, displayAnalyticsPdfState, isShowingUploadCSVPopupState,
} from '../../assets/AtomStore/AtomStore';

const ChartPage = () => {
    const [searchLoading, setSearchLoading] = useState(false);

    const [backendData, setBackendData] = useRecoilState(backendDataState);
    const [totalLikes, setTotalLikes] = useRecoilState(totalLikedState);
    const [mostProminentWords, setMostProminentWords] = useRecoilState(mostProminentSentimentState);
    const [numberOfTrends, setNumberOfTrends] = useRecoilState(numberOfTrendsState);
    const [numberOfAnomalies, setNumberOfAnomalies] = useRecoilState(numberOfAnomaliesState);
    const [averageInteraction, setAverageInteraction] = useRecoilState(averageInteractionState);
    const [overallSentiment, setOverallSentiment] = useRecoilState(overallSentimentState);
    const [engagementPerProvince, setEngagementPerProvince] = useRecoilState(engagementPerProvinceState);
    const [eapData, setMapData] = useRecoilState(mapDataState);
    const [dataFrequency, setDataFrequency] = useRecoilState(dataFrequencyState);
    const [wordCloud, setWordCloud] = useRecoilState(wordCloudState);
    const [dominantWords, setDominantWords] = useRecoilState(dominantWordsState);
    const [entitiesRelationship, setEntitiesRelationship] = useRecoilState(entitiesRelationshipsState);
    const [patternsRelationship, setPatternsRelationship] = useRecoilState(patternsRelationshipsState);
    const [anomalies, setAnomalies] = useRecoilState(anomaliesState);

    const [showCSV, setShowCSV] = useRecoilState(isShowingUploadCSVPopupState);
    const [showPdf, setShowPdf] = useRecoilState(displayAnalyticsPdfState);
    const [currentPdf, setCurrentPdf] = useRecoilState(currentPdfState);

    const user = useRecoilValue(userState);

    const handleSearch = (value) =>{
        setSearchLoading(true);
        const jsonObj = {
            permission: user.permission,
            username: user.username,
        };

        const abortCont = new AbortController();
        const requestObj = {
            signal: abortCont.signal,
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(jsonObj),
        };
        const url = `http://localhost:9000/main/${value}`;
        fetch(url, requestObj)
            .then((res) =>{
                if (!res.ok) {
                    throw (res.error());
                }
                return res.json();
            })
            .then((data)=>{
                setSearchLoading(false);
                structureBackendData(data);
            })
            .catch((err) => {
                setSearchLoading(false);
                structureBackendData(templateJson);
                console.log(err.message);
            });
    };

    const structureBackendData = (data) => {
        if (data !== null && data.length > 0) {
            setBackendData(data);
            if (data[0].length > 0) {
                setTotalLikes(data[0][0].words);
                setOverallSentiment(data[1][0].words);
                setNumberOfAnomalies(data[3][0].words);
                setNumberOfTrends(data[2][0].words);
            }

            setCurrentPdf(data[data.length - 1][0].report);
        }
    };
    //
    if (!user) return <Redirect to="/login" />;
    return (
        <>
            <Switch>
                <Route exact path="/chart">
                    {
                        showCSV
                            ? (
                                <SimplePopup
                                  closePopup={()=> setShowCSV(false)}
                                  popupTitle="Upload File"
                                >
                                    <UploadDataPage
                                      handleTextChange={structureBackendData}
                                      isAnalyzeCSVPopupShowing={showCSV}
                                    />
                                </SimplePopup>
                            ) :
                            null
                    }
                    {
                        showPdf
                            ? (
                                <ReportPreview
                                  closePopup={() => setShowPdf(false)}
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
                                <Search
                                  placeholder="search..."
                                  onSearch={handleSearch}
                                  loading={searchLoading}
                                />

                                <button
                                  type="button"
                                  id="upload-btn"
                                  className="clickable"
                                  onClick={() => setShowCSV(true)}
                                >
                                    <AiOutlineUpload id="upload-btn-logo" />
                                    Upload
                                </button>

                                <button
                                  type="button"
                                  id="upload-btn"
                                  className="clickable"
                                  onClick={() => setShowPdf(true)}
                                >
                                    <CgFileDocument id="upload-btn-logo" />
                                    Generate Report
                                </button>

                                <UserInfoCard />
                            </Header>
                            {
                                backendData !== null &&
                                (
                                    <div id="content-section">
                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-1"
                                        >
                                            <OverviewSection />
                                        </SimpleSection>

                                        <SimpleSection
                                          cardTitle=""
                                          cardID="row-2"
                                        >
                                            <OverviewGraphSection
                                              text={backendData}
                                              key={backendData}
                                            />
                                        </SimpleSection>

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
                                                        <MapCard text={backendData} />
                                                    </SimpleCard>

                                                    <SimpleCard
                                                      cardTitle="Data Frequency"
                                                      cardID="map-metric-1"
                                                      titleOnTop
                                                    >
                                                        <DraggableBarGraph text={backendData} />
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
                                                  text={backendData}
                                                  key={backendData}
                                                />
                                            </SimpleCard>

                                            <div id="word-cloud-graph-container">
                                                <SimpleCard
                                                  cardTitle="Dominant words"
                                                  cardID="word-graph-2"
                                                  titleOnTop
                                                >
                                                    <PieChart text={backendData} />
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
                                                  text={cloneDeep(backendData)}
                                                  key={cloneDeep(backendData)}
                                                  indexOfData={11}
                                                />
                                            </SimpleCard>

                                            <SimpleCard
                                              cardTitle="Relationship Between Patterns"
                                              cardID="network-graph-patterns"
                                              titleOnTop
                                            >
                                                <NetworkGraphCard
                                                  text={cloneDeep(backendData)}
                                                  key={cloneDeep(backendData)}
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
                                                  text={backendData}
                                                  key={backendData}
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
                                            {/*        text={backendData} */}
                                            {/*        key={backendData} */}
                                            {/*    /> */}
                                            {/* </SimpleCard> */}
                                        </SimpleSection>
                                        {/** !/ */}
                                    </div>
                                )
                            }
                        </Layout>
                    </Layout>
                </Route>
            </Switch>
        </>
    );
};

export default ChartPage;
