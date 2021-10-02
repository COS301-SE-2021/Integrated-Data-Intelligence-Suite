import React from 'react';
import { Route } from 'react-router-dom';
import Switch from 'react-bootstrap/Switch';
import SideBar from '../../components/SideBar/SideBar';
import './ManageModelsPage.css';
import SimpleCard from '../../components/SimpleCard/SimpleCard';
import ModelCard from '../../components/ModelCard/ModelCard';

export default function ManageModelsPage(props) {

    return (
        <>
            <Switch>
                <Route exact path="/manageModels">
                    <div id={'manage-models-page-container'}>
                        <SideBar currentPage={'6'}/>
                        <div id={'manage-models-page-content'}>
                            <SimpleCard
                                cardID={'manage-models-card'}
                                cardTitle={'Manage Your Models'}
                                titleOnTop
                            >
                                <div id={'manage-models-btn-row'}>
                                    Button Row
                                </div>
                                <div id={'manage-models-card-row'}>
                                    <ModelCard/>
                                </div>
                            </SimpleCard>
                        </div>
                    </div>
                </Route>
            </Switch>
        </>
    );
}
