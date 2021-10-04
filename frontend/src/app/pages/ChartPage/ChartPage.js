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
                structureBackendData(template_json);
                console.log(err.message);
            });
    };

    const structureBackendData = (data) => {
        setTotalLikesState(data[0]);
        setMostProminentWordsState(data[1]);
        setNumberOfTrendsState(data[2]);
        setNumberOfAnomaliesState(data[3]);

        setAverageInteractionState(data[4]);
        setOverallSentimentState(data[5]);
        setEngagementPerProvinceState(data[6]);

        setMapDataState(data[7]);
        setDataFrequencyState(data[8]);

        setWordCloudState(data[9]);
        setDominantWordsState(data[10]);

        setEntitiesRelationshipState(data[11]);
        setPatternsRelationshipState(data[12]);

        setAnomaliesState(data[13]);
    }
    //
    if (!user) return <Redirect to="/login" />;
    return (
        <>
            <Switch>
                <Route exact path="/chart">
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
                                  onClick={() => {
                                        /* showPopup(true); */
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
                                        // generateReport();
                                    }}
                                >
                                    <CgFileDocument id="upload-btn-logo" />
                                    Generate Report
                                </button>

                                <UserInfoCard />
                            </Header>
                        </Layout>
                    </Layout>
                </Route>
            </Switch>
        </>
    );
};

export default ChartPage;
