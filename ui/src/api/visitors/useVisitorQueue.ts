import { useState } from 'react';
import type { VisitorQueue } from './model';

export const useVisitorQueue = () => {
    const [queue, setQueue] = useState<VisitorQueue>({ entries: [] });
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchVisitorQueue = () => {
        setLoading(true);

        fetch('/api/queue', {
            method: 'GET',
            headers: {
                Accept: 'application/json'
            }
        })
            .then((response) => {
                if (response.ok) {
                    response
                        .json()
                        .then(setQueue)
                        .catch(() => setError('Failed to load queue'));
                } else {
                    response
                        .json()
                        .then((error: { message: string }) => setError(error.message))
                        .catch(() => setError('Failed to load queue'));
                }
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to load queue');
            })
            .finally(() => setLoading(false));
    };

    return { queue, error, loading, fetchVisitorQueue };
};
