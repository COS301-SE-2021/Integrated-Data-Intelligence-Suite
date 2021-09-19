const { useEffect } = require('react');
const { useState } = require('react');

const useGet = (url) => {
  const [data, setData] = useState(null);
  const [isPending, setIsPending] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const abortCont = new AbortController();

    fetch(`http://20.108.56.39:80${url}`, { signal: abortCont.signal })
      .then((res) => {
        if (!res.ok) {
          console.log(res);
          throw Error(res.error());
        }
        return res.json();
      })
      .then((data) => {
        setData(data);
        console.log('data is here', data);
        setIsPending(false);
        setError(null);
      })
      .catch((err) => {
        if (err.name === 'AbortError') console.log('Fetch Aborted');
        else {
          // console.log(err.message)
          setError(err.message);
          setIsPending(false);
        }
      });

    return () => abortCont.abort();
  }, [url]);
  return { data, isPending, error };
};

export default useGet;
