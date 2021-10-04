import React from 'react';
import ContentLoader from 'react-content-loader';

export default function ModelCardLoader(props) {
    return (
        <>
            <ContentLoader
                speed={2}
                width={400}
                height={460}
                viewBox="0 0 400 460"
                backgroundColor="#888a85"
                foregroundColor="#ecebeb"
                {...props}
            >
                <rect x="11" y="12" rx="10" ry="10" width="352" height="378"/>
                <rect x="11" y="399" rx="10" ry="10" width="352" height="56"/>
            </ContentLoader>
        </>
    );

}

