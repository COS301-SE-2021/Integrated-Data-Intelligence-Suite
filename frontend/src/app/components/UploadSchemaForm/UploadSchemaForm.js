import React from 'react';
import SimpleCard from '../SimpleCard/SimpleCard';
import './UploadSchemaForm.css';
import InputBoxWithLabel from '../InputBoxWithLabel/InputBoxWithLabel';

export default class UploadSchemaForm extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            isSocialMediaChecked: false
        };
    }

    setSocialMediaChecked(param) {
        // this.isSocialMediaChecked = param;
        this.setState({ isSocialMediaChecked: param });
        console.log(this.state.isSocialMediaChecked);
    }

    render() {
        const isSocialMediaChecked = this.state.isSocialMediaChecked;
        return (
            <>
                <SimpleCard
                    cardID={'upload-schema-form-card'}
                    cardTitle={''}
                    titleOnTop
                >
                    <div id={'upload-schema-form-container'}>
                        {/*<div id={'upload-radio-btn-container'}>*/}
                        {/*    <div id={'upload-radio-btn-heading'}>*/}
                        {/*        Choose the type of data your uploading:*/}
                        {/*    </div>*/}
                        {/*    <div id={'upload-radio-btn-wrapper'}>*/}
                        {/*        <div>*/}

                        {/*            <input*/}
                        {/*                type="radio"*/}
                        {/*                value="social-media-type"*/}
                        {/*                name="upload-type-radio-btn"*/}
                        {/*                id={'social-media-radio-btn'}*/}
                        {/*                onClick={() => this.setSocialMediaChecked(true)}*/}
                        {/*            />*/}
                        {/*            Social Media*/}
                        {/*        </div>*/}
                        {/*        <div>*/}
                        {/*            <input*/}
                        {/*                type="radio"*/}
                        {/*                value="news-type"*/}
                        {/*                name="upload-type-radio-btn"*/}
                        {/*                id={'news-radio-btn'}*/}
                        {/*                onClick={() => this.setSocialMediaChecked(false)}*/}
                        {/*            />*/}
                        {/*            News*/}
                        {/*        </div>*/}
                        {/*    </div>*/}
                        {/*</div>*/}

                        <div id={'upload-input-container'}>
                            {/*<p>social media is checked</p>*/}
                            <InputBoxWithLabel
                                inputLabel={'Text Message'}
                                inputName={'schema-edit-box'}
                                inputID={'upload-input-text-msg'}
                            />

                            <InputBoxWithLabel
                                inputLabel={'Location'}
                                inputName={'schema-edit-box'}
                                inputID={'upload-input-location'}
                            />

                            <InputBoxWithLabel
                                inputLabel={'Likes'}
                                inputName={'schema-edit-box'}
                                inputID={'upload-input-likes'}
                            />

                            <InputBoxWithLabel
                                inputLabel={'Date'}
                                inputName={'schema-edit-box'}
                                inputID={'upload-input-date'}
                            />
                        </div>
                    </div>
                </SimpleCard>
            </>
        );
    }

}
