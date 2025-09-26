import { useState } from 'react';
import type { Provider } from './model';

export const useProviders = () => {
    const [providers, setProviders] = useState<Provider[]>([]);
    const [error, setError] = useState<string | undefined>();
    const [loading, setLoading] = useState<boolean>(false);

    const fetchProviders = async (all?: boolean) => {
        setLoading(true);

        fetch(`/api/providers${all ? '/all' : ''}`, {
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
                        .catch(() => setError('Failed to load providers'));
                } else {
                    response
                        .json()
                        .then((error: { message: string }) => setError(error.message))
                        .catch(() => setError('Failed to load providers'));
                }
            })
            .catch((error) => {
                console.error(error);
                setError('Failed to load providers');
            })
            .finally(() => setLoading(false));
    };

    const saveProvider = (
        provider: Provider,
        onSuccess: (provider: Provider) => void,
        onFailure: (message: string) => void
    ) => {
        fetch('/api/providers', {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(provider)
        })
            .then((response) => {
                if (response.ok) {
                    response
                        .json()
                        .then((savedProvider) => onSuccess(savedProvider))
                        .catch(() => onFailure('Failed to create provider'));
                } else {
                    response
                        .json()
                        .then((error: { message: string }) => onFailure(error.message))
                        .catch(() => onFailure('Failed to create provider'));
                }
            })
            .catch((error) => {
                console.error(error);
                onFailure('Failed to create provider');
            });
    };

    const updateProvider = (
        provider: Provider,
        onSuccess: (provider: Provider) => void,
        onFailure: (message: string) => void
    ) => {
        fetch(`/api/providers/${provider.id}`, {
            method: 'PUT',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(provider)
        })
            .then((response) => {
                if (response.ok) {
                    response
                        .json()
                        .then((savedProvider) => onSuccess(savedProvider))
                        .catch(() => onFailure('Failed to update provider'));
                } else {
                    response
                        .json()
                        .then((error: { message: string }) => onFailure(error.message))
                        .catch(() => onFailure('Failed to update provider'));
                }
            })
            .catch((error) => {
                console.error(error);
                onFailure('Failed to update provider');
            });
    };

    return { providers, error, loading, fetchProviders, saveProvider, updateProvider };
};
