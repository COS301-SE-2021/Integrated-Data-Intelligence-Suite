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
                    cardTitle={'Step 2: xxx'}
                    titleOnTop
                >
                    <div id={'upload-schema-form-container'}>
                        <div id={'upload-radio-btn-container'}>
                            <input
                                type="radio"
                                value="social-media"
                                name="upload-type"
                                id={'social-media-radio-btn'}
                                onClick={() => this.setSocialMediaChecked(true)}
                            />
                            Social Media
                            <input
                                type="radio"
                                value="news"
                                name="upload-type"
                                id={'news-radio-btn'}
                                onClick={() => this.setSocialMediaChecked(false)}
                            />
                            News
                        </div>

                        {isSocialMediaChecked
                            ? (
                                <div id={'upload-input-container'}>
                                    <p>social media is checked</p>
                                    <InputBoxWithLabel
                                        inputLabel={'date'}

                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'interactions'}
                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'text'}
                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'location'}
                                    />
                                </div>
                            ) : (
                                <div id={'upload-input-container'}>
                                    <p>news is checked</p>
                                    <InputBoxWithLabel
                                        inputLabel={'date'}
                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'content'}
                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'title'}
                                    />

                                    <InputBoxWithLabel
                                        inputLabel={'description'}
                                    />
                                </div>
                            )
                        }
                    </div>
                </SimpleCard>
            </>
        );
    }

}
