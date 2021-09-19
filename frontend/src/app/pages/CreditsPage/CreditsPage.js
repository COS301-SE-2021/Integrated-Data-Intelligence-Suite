import React from 'react';
import { Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import './CreditsPage.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import { SiElectron, SiLeaflet, SiReact, SiReactrouter, SiYarn } from 'react-icons/all';
import SimpleCard from '../../components/SimpleCard/SimpleCard';

export default class CreditsPage extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <Switch>
                    <Route exact path="/credits">
                        <div id={'cred-page-container'}>
                            <SideBar currentPage={'4'}/>
                            <div id={'cred-content-section'}>
                                <div id={'cred-title'}>
                                    Built With These technologies
                                </div>
                                <SimpleSection
                                    cardTitle="Frontend"
                                    cardID="frontend-section"
                                >
                                    <SimpleCard
                                        cardTitle="React"
                                        cardID="frontend-row-card1"
                                    >
                                        <SiReact/>
                                    </SimpleCard>

                                    <SimpleCard
                                        cardTitle="React Router"
                                        cardID="frontend-row-card2"
                                    >
                                        <SiReactrouter/>
                                    </SimpleCard>

                                    <SimpleCard
                                        cardTitle="Leaflet"
                                        cardID="frontend-row-card3"
                                    >
                                        <SiLeaflet/>
                                    </SimpleCard>

                                    <SimpleCard
                                        cardTitle="Electron"
                                        cardID="frontend-row-card4"
                                    >
                                        <SiElectron/>
                                    </SimpleCard>

                                    <SimpleCard
                                        cardTitle="Yarn"
                                        cardID="frontend-row-card5"
                                    >
                                        <SiYarn/>
                                    </SimpleCard>
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'Backend'}
                                    cardID={'backend-section'}
                                >
                                    backend Tech items
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'Tools & Communications'}
                                    cardID={'tools-section'}
                                >
                                    tools and comms tech
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'Documentation'}
                                    cardID={'doc-section'}
                                >
                                    doc tech
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'VCS'}
                                    cardID={'vcs-section'}
                                >
                                    VCS tech and pm
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'Testing'}
                                    cardID={'testing-section'}
                                >
                                    testing tools
                                </SimpleSection>

                                <SimpleSection
                                    cardTitle={'Hosting'}
                                    cardID={'hosting-section'}
                                >
                                    hosting
                                </SimpleSection>
                            </div>
                        </div>
                    </Route>
                </Switch>
            </>
        );
    }
}



