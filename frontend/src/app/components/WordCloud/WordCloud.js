import React, { useEffect, useState } from 'react';
import { Text } from '@visx/text';
import { scaleLog } from '@visx/scale';
import { Wordcloud } from '@visx/wordcloud';
import { useRecoilValue } from 'recoil';
import { totoAfricaLyrics } from '../../Mocks/WordCloudMock';
import { wordCloudState } from '../../assets/AtomStore/AtomStore';

const colors = [
    '#E80057',
    '#208AAE',
    '#FF5400',
    '#FFBD00',
    '#19ac77',
    '#18FF6D',
    '#FF337E',
    '#FFDA22',
    '#89AAE6',
    '#e60793',
    '#900ed6',
    '#FF0800',
    '#5fa7c8',
];

function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min)) + min;
}

function wordFreq(text) {
    if (typeof text !== 'undefined') {
        const words = text.replace(/\./g, '')
            .split(/\s/);
        const freqMap = {};
        let i = 0;
        const wordList = words;
        // console.log('There maybe an error here');
        for (; i < wordList.length; i = i + 1) {
            const w = wordList[i].toLowerCase();
            if (w.length > 2) {
                if (!freqMap[w]) {
                    freqMap[w] = 0;
                }
                freqMap[w] += 1;
            }
        }

        const max = Math.max(...Object.values(freqMap));

        return Object.keys(freqMap)
            .map((word) => {
                return ({
                    text: word,
                    value: Math.floor((freqMap[word] / max) * 50),
                });
            });
    }

    return null;
}

const words = wordFreq(totoAfricaLyrics);

const fontScale = scaleLog({
    domain: [2, 1000],
    range: [20, 150],
});

function getWindowSize() {
    if (window.innerWidth > 500) {
        return Math.floor(window.innerWidth * 0.35);
    }
    return 200;
}

function WordCloud() {
    const [wordsArray, setWordsArray] = useState(words);
    const worldCloud = useRecoilValue(wordCloudState);
    const [windowWidth] = useState(getWindowSize());

    useEffect(() => {
        if (worldCloud) {
            const lst = wordFreq(worldCloud.words);
            setWordsArray(lst);
        }
    }, [worldCloud]);

    return (
        <div className="wordcloud" id="word-cloud-outer-container">
            <Wordcloud
              key={wordsArray}
              words={wordsArray}
              width={windowWidth * 0.7}
              height={windowWidth * 0.6}
              font="Impact"
              padding={2}
              fontSize={(datum) => fontScale(datum.value)}
              spiral="archimedean"
              rotate={0}
              random={()=>{ return 0.5; }}
            >
                {(cloudWords) => cloudWords.map((w) => (
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
