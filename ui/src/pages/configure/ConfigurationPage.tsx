import { useProviders } from '../../api/providers/useProviders';

export const ConfigurationPage = () => {
    const { providers, loading, error, fetchProviders } = useProviders();
    return (
        <div>
            <h1>Configuration</h1>
            <div>Loading: {loading ? 'True' : 'False'}</div>
            <div>Providers:</div>
            <ul>
                {providers.map((p) => (
                    <li key={p.id}>
                        ID: {p.id} - Name: {p.name} - Active: {p.active ? 'True' : 'False'}
                    </li>
                ))}
            </ul>
            <button onClick={fetchProviders}>Fetch providers</button>
            <div>Error: {error}</div>
        </div>
    );
};
