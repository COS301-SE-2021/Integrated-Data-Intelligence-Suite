import React, { useState } from 'react';
import { Text } from '@visx/text';
import { scaleLog } from '@visx/scale';
import { Wordcloud } from '@visx/wordcloud';
import { totoAfricaLyrics } from '../../Mocks/WordCloudMock';

// const colors = ['#143059', '#2F6B9A', '#82a6c2'];
const colors = [
    '#25bd52',
    '#786e64',
    '#8e8073',
    '#e66c85',
    '#244a99',
    '#be8abf',
    '#b3b456',
    '#d79525',
    '#bd6341',
    '#709a04',
    '#5fa7c8',
    '#f3cd6c',
    '#FF0800',
    '#5fa7c8',
];
function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}
let data_from_backend;
let words_from_backend = [];

function wordFreq(text) {
    if (typeof text !== 'undefined') {
        const words = text.replace(/\./g, '')
            .split(/\s/);
        const freqMap = {};
        let _i = 0;
        const words_1 = words;
        console.log('There maybe an error here');
        for (; _i < words_1.length; _i++) {
            const w = words_1[_i];
            if (!freqMap[w]) {
                freqMap[w] = 0;
            }
            freqMap[w] += 1;
        }

        console.log('HEre lies a freq map');
        console.log(freqMap);
        return Object.keys(freqMap)
            .map(function (word) {
                return ({
                    text: word,
                    value: freqMap[word],
                });
            });
    }

    return null;
}

function getRotationDegree() {
    const rand = Math.random();
    const degree = rand > 0.5 ? 60 : -60;
    return rand * degree;
}

const words = wordFreq(totoAfricaLyrics);

const fontScale = scaleLog({
    domain: [Math.min(...words.map((w) => w.value)), Math.max(...words.map((w) => w.value))],
    range: [20, 200],
});
const fontSizeSetter = function (datum) {
    // console.log(datum);
    console.log('scaling...');
    console.log(fontScale(datum.value));
    return fontScale(datum.value);
};
const fixedValueGenerator = function () {
    return 0.5;
};

const getWindowSize = function () {
    if (window.innerWidth > 500) {
        return Math.floor(window.innerWidth * 0.35);
    }
    return 200;
};




function WordCloud(props) {
    if (typeof props.text === 'undefined') {
        data_from_backend = [];
    } else if (typeof props.text[7] === 'undefined') {
        data_from_backend = [];
    } else if (props.text[7].length === 0) {
        data_from_backend = [];
    } else if (props.text[7].length > 0) {
        // console.log("Reached-here");
        // console.log(props.text[7][0].words);
        data_from_backend = props.text[7][0].words;
        words_from_backend = wordFreq(data_from_backend);
        // console.log("XXXXXX___XXXXXX");
        // console.log(words_from_backend);
    }
    const [windowWidth, setWindowWidth] = useState(getWindowSize());

    return (
        <div className="wordcloud">
            <Wordcloud
              key={words_from_backend}
              words={words_from_backend}
              width={windowWidth * 0.8}
              height={windowWidth * 0.6}
              font="Impact"
              padding={2}
              fontSize={fontSizeSetter}
              spiral="archimedean"
              rotate={0}
              random={fixedValueGenerator}
            >
                {(cloudWords) => cloudWords.map((w, i) => (
                    <Text
                      key={w.text}
                      fill={colors[getRndInteger(0, colors.length)]}
                      textAnchor="middle"
                      transform={`translate(${w.x}, ${w.y}) rotate(${w.rotate})`}
                      fontSize={w.size}
                      fontFamily={w.font}
                    >
                        {w.text}
                    </Text>
                ))}
            </Wordcloud>
        </div>
    );
}

export default WordCloud;
