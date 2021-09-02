import React, { useState } from 'react';
import { Text } from '@visx/text';
import { scaleLog } from '@visx/scale';
import { Wordcloud } from '@visx/wordcloud';
import { totoAfricaLyrics } from '../../Mocks/WordCloudMock';

const colors = ['#143059', '#2F6B9A', '#82a6c2'];

function wordFreq(text) {
    const words = text.replace(/\./g, '')
        .split(/\s/);
    const freqMap = {};
    let _i = 0;
    const words_1 = words;
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

function getRotationDegree() {
    const rand = Math.random();
    const degree = rand > 0.5 ? 60 : -60;
    return rand * degree;
}

const words = wordFreq(totoAfricaLyrics);

const fontScale = scaleLog({
    domain: [Math.min(...words.map((w) => w.value)), Math.max(...words.map((w) => w.value))],
    range: [10, 100],
});
const fontSizeSetter = function (datum) {
    return fontScale(datum.value);
};
const fixedValueGenerator = function () {
    return 0.5;
};


function WordCloud(props) {
    return (
        <div className="wordcloud">
            <Wordcloud
                words={words}
                width={800}
                height={800}
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
                        fill={colors[i % colors.length]}
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
