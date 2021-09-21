const PieDataMock = () =>{
    let bad = Math.floor(Math.random() * 100 + 1);
    let good = Math.floor(Math.random() * 100 + 1);
    let neutral = Math.floor(Math.random() * 100 + 1);

    const sum = bad+ good + neutral;

    bad = Math.round((bad/sum)*100);
    good = Math.round((good/sum)*100);
    neutral = Math.round((neutral/sum)*100);

    return [
        {
            x: "Good",
            y: good,
        },
        {
            x: "Bad",
            y: bad,
        },
        {
            x: "Neutral",
            y:neutral,
        },
    ];
};

export default PieDataMock