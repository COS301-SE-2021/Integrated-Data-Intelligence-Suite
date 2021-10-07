function getRandomSeriesData(total) {
    const result = [];
    let lastY = Math.random() * 40 - 20;
    let y;
    const firstY = lastY;
    for (let i = 0; i < Math.max(total, 3); i++) {
        y = Math.random() * firstY - firstY / 2 + lastY;
        result.push({
            left: i,
            top: y,
        });
        lastY = y;
    }
    return result;
}
