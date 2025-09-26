import type { Provider } from 'src/api/providers/model';
import { useEffect, useState } from 'react';
import { useProviders } from '../../api/providers/useProviders';
import { Header } from '../../components/header/Header';
import { Loading } from '../../components/loading/Loading';
import styles from './ManageProvidersPage.module.scss';
import { ProviderForm } from './form/ProviderForm';

export const ManageProvidersPage = () => {
    const { providers, loading, fetchProviders, saveProvider, updateProvider } = useProviders();
    const [selectedProvider, setSelectedProvider] = useState<Provider | undefined>();

    useEffect(() => {
        fetchProviders(true);
    }, []);

    const handleSave = (provider: Provider) => {
        if (provider.id !== null) {
            updateProvider(provider, handleSaveSuccess, () => {});
        } else {
            saveProvider(provider, handleSaveSuccess, () => {});
        }
    };

    const handleSaveSuccess = () => {
        fetchProviders(true);
        setSelectedProvider(undefined);
    };
    return (
        <div className={styles.manageProvidersPage}>
            <Loading loading={loading}>
                <Header title="Manage providers" showHome />
                <div className={styles.content}>
                    <div className={styles.providerList}>
                        <table>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>Active</th>
                                    <th />
                                </tr>
                            </thead>
                            <tbody>
                                {providers.map((p) => (
                                    <tr key={p.id} className={p === selectedProvider ? styles.selectedRow : ''}>
                                        <td>{p.id}</td>
                                        <td>{p.name}</td>
                                        <td>{p.active ? 'True' : 'False'}</td>
                                        <td>
                                            <button onClick={() => setSelectedProvider(p)}>Edit</button>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                    <ProviderForm
                        provider={selectedProvider}
                        onCancel={() => setSelectedProvider(undefined)}
                        onSave={handleSave}
                    />
                </div>
            </Loading>
        </div>
    );
};
