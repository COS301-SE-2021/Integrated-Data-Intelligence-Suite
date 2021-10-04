import { useEffect, useState } from 'react';

export default function usePost(user, body, header) {
    const [data, setData] = useState(null);
    const [isPending, setIsPending] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const abortCont = new AbortController();

        fetch(`${process.env.REACT_APP_BACKEND_HOST}${url}`,
            {
                signal: abortCont.signal,
                method: 'POST',
                headers: header,
                body: JSON.stringify(body),
            })
            .then((res) => {
                if (!res.ok) {
                    throw Error(res.error);
                }
                return res.json();
            })
            .then((data) => {
                setData(data);
                setIsPending(false);
                setError(null);
            })
            .catch((error) =>{
                if (error.name !== 'AbortError') {
                    setError(error.message);
                    setIsPending(false);
                }
            });
    }, [url]);
    return { data, isPending, error };
}
