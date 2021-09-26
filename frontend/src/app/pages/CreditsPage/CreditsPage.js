import React from 'react';
import { Route, Switch } from 'react-router-dom';
import SideBar from '../../components/SideBar/SideBar';
import './CreditsPage.css';
import SimpleSection from '../../components/SimpleSection/SimpleSection';
import {
    SiApachespark, SiDiscord, SiDocker,
    SiElectron, SiGithub, SiIntellijidea,
    SiJava, SiJest, SiKubernetes,
    SiLeaflet, SiMicrosoftazure, SiOverleaf, SiPostman,
    SiReact,
    SiReactrouter, SiSonarcloud,
    SiSpring, SiTwitter, SiWebstorm,
    SiYarn
} from 'react-icons/all';
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
                            <SimpleSection
                                cardID={'cred-content-section'}
                                cardTitle={'Our Tech Stack'}
                            >
                                {/*<div id={'cred-title'}>*/}
                                {/*    Built With These Awesome technologies*/}
                                {/*</div>*/}
                                {/*<SimpleSection*/}
                                {/*    cardTitle="Frontend"*/}
                                {/*    cardID="frontend-section"*/}
                                {/*>*/}
                                {/*<div*/}
                                {/*    id={'frontend-row-1'}*/}
                                {/*>*/}
                                <SimpleCard
                                    cardTitle="React"
                                    cardID="credits-card-1"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiReact className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="React Router"
                                    cardID="credits-card-2"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiReactrouter className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Leaflet"
                                    cardID="credits-card-3"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiLeaflet className={'tech-stack-icons'}/>
                                </SimpleCard>
                                {/*</div>*/}

                                {/*<div*/}
                                {/*    id={'frontend-row-2'}*/}
                                {/*>*/}
                                <SimpleCard
                                    cardTitle="Electron"
                                    cardID="credits-card-4"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiElectron className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Yarn"
                                    cardID="credits-card-5"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiYarn className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Java"
                                    cardID="credits-card-6"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiJava className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Spring boot"
                                    cardID="credits-card-7"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiSpring className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Apache Spark"
                                    cardID="credits-card-8"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiApachespark className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Discord"
                                    cardID="credits-card-10"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiDiscord className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Intellij Idea"
                                    cardID="credits-card-9"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiIntellijidea className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Webstorm"
                                    cardID="credits-card-11"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiWebstorm className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Twitter"
                                    cardID="credits-card-12"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiTwitter className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Overleaf"
                                    cardID="credits-card-13"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiOverleaf className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Github"
                                    cardID="credits-card-14"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiGithub className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Jest"
                                    cardID="credits-card-15"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiJest className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Sonar Cloud"
                                    cardID="credits-card-16"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiSonarcloud className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Microsoft Azure"
                                    cardID="credits-card-17"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiMicrosoftazure className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Docker"
                                    cardID="credits-card-18"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiDocker className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Kubernetes"
                                    cardID="credits-card-19"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiKubernetes className={'tech-stack-icons'}/>
                                </SimpleCard>

                                <SimpleCard
                                    cardTitle="Postman"
                                    cardID="credits-card-20"
                                    titleOnTop={false}
                                    extraClassName={'credit-card'}
                                >
                                    <SiPostman className={'tech-stack-icons'}/>
                                </SimpleCard>


                                {/*</div>*/}


                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'Backend'}*/}
                                {/*    cardID={'backend-section'}*/}
                                {/*>*/}
                                {/*    backend Tech items*/}
                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'Tools & Communications'}*/}
                                {/*    cardID={'tools-section'}*/}
                                {/*>*/}
                                {/*    tools and comms tech*/}
                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'Documentation'}*/}
                                {/*    cardID={'doc-section'}*/}
                                {/*>*/}
                                {/*    doc tech*/}
                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'VCS'}*/}
                                {/*    cardID={'vcs-section'}*/}
                                {/*>*/}
                                {/*    VCS tech and pm*/}
                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'Testing'}*/}
                                {/*    cardID={'testing-section'}*/}
                                {/*>*/}
                                {/*    testing tools*/}
                                {/*</SimpleSection>*/}

                                {/*<SimpleSection*/}
                                {/*    cardTitle={'Hosting'}*/}
                                {/*    cardID={'hosting-section'}*/}
                                {/*>*/}
                                {/*    hosting*/}
                                {/*</SimpleSection>*/}
                            </SimpleSection>
                        </div>
                    </Route>
                </Switch>
            </>
        );
    }
}



