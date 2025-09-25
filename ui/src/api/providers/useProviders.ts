import { useState } from 'react';

type Provider = {
    id: number;
    name: string;
    active: boolean;
};
export const useProviders = () => {
    const [providers, setProviders] = useState<Provider[]>([]);
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchProviders = async () => {
        setLoading(true);

        fetch('/api/providers', {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    response
                        .json()
                        .then(setProviders)
                        .catch(() => setError('Failed to parse providers'));
                } else {
                    response
                        .json()
                        .then((error: {message: string}) => setError(error.message))
                        .catch(() => setError('Failed to load providers'));
                }
            })
            .catch((error) => {
                console.error(error);
                setError(error);
            })
            .finally(() => setLoading(false));
    };

    return { providers, error, loading, fetchProviders };
};
