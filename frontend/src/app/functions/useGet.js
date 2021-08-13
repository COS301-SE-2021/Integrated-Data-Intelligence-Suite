const {useEffect} = require("react");
const {useState} = require("react");

const useGet = (url) => {
    const [data, setData] = useState(null)
    const [isPending , setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>{

        const abortCont = new AbortController();

        setTimeout(() => {
            fetch(url, { signal: abortCont.signal })
                .then(res => {
                    if(!res.ok){
                        throw Error('Server Error')
                    }
                    // console.log(res)
                    return res.json()
                })
                .then((data ) => {
                    setData(data)
                    setIsPending(false)
                    setError(null)
                })
                .catch((err) =>{

                    if(err.name === "AbortError")
                        console.log("Fetch Aborted")
                    else{
                        // console.log(err.message)
                        setError(err.message)
                        setIsPending(false)
                    }
                })
        }, 1000)

        return () => abortCont.abort();
    }, [url]);
    return { data, isPending, error}
}

export default useGet;