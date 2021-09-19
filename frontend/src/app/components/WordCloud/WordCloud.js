import React, { useEffect, useState } from 'react';
import { getStringWidth, Text } from '@visx/text';
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
const index = 9;
// let data_from_backend;
// const words_from_backend = [];

function wordFreq(text) {
    if (typeof text !== 'undefined') {
        const words = text.replace(/\./g, '')
            .split(/\s/);
        const freqMap = {};
        let _i = 0;
        const words_1 = words;
        console.log('There maybe an error here');
        for (; _i < words_1.length; _i++) {
            const w = words_1[_i].toLowerCase();
            if (!freqMap[w]) {
                freqMap[w] = 0;
            }
            freqMap[w] += 1;
        }

        console.log('HEre lies a freq map');
        console.log(freqMap);
        const max = Math.max(...Object.values(freqMap));
        const lst = Object.keys(freqMap)
            .map(function (word) {
                return ({
                    text: word,
                    value: Math.floor((freqMap[word] / max) * 50),
                });
            });
        console.log(lst);
        return lst;
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
    domain: [2, 1000],
    range: [20, 150],
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

const getDataFromProps = function (dataArray) {
    if (dataArray) {
        if (dataArray[index] && dataArray[index].length > 0) {
            return wordFreq(dataArray[index][0].words);
        }
    }
    return words;
};

function WordCloud(props) {
    const [wordsArray, setWordsArray] = useState(words);
    const [windowWidth, setWindowWidth] = useState(getWindowSize());

    useEffect(()=>{
        const lst = getDataFromProps(props.text);
        console.log('works array found to be ');
        console.log(lst);
        setWordsArray(lst);

        // function handleResize() {
        //     setWindowWidth(getStringWidth());
        //     const lst = getDataFromProps(props.text);
        //     console.log('works array found to be ');
        //     console.log(lst);
        //     setWordsArray(lst);
        // }

        // window.addEventListener('resize', handleResize);
    }, [props.text]);

    return (
        <div className="wordcloud" id="word-cloud-outer-container">
            <Wordcloud
              key={wordsArray}
              words={wordsArray}
              width={windowWidth}
              height={windowWidth * 0.4}
              font="Impact"
              padding={2}
              fontSize={(datum) => fontScale(datum.value)}
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
