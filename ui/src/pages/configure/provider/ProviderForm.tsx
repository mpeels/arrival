import { useEffect } from 'react';
import { Controller, useForm, useFormState } from 'react-hook-form';
import styles from './ProviderForm.module.scss';
import type { Provider } from 'src/api/providers/model';

type Props = {
    provider?: Provider;
    onCancel: () => void;
    onSave: (provider: Provider) => void;
};
export const ProviderForm = ({ provider, onCancel, onSave }: Props) => {
    const form = useForm<Provider>({ defaultValues: { id: null, name: '', active: true }, mode: 'onBlur' });

    const { isValid } = useFormState(form);

    useEffect(() => {
        if (provider !== undefined) {
            form.reset({ id: provider.id, name: provider.name, active: provider.active }, { keepDefaultValues: false });
        } else {
            console.log('resetting ');
            form.reset({ id: null, name: '', active: true }, { keepDefaultValues: false });
        }
    }, [provider]);

    return (
        <div className={styles.providerForm}>
            <div>
                <div className={styles.cardTitle}>
                    {provider === undefined ? 'Create a new provider' : `Edit provider: ${provider.id}`}
                </div>
                <div>
                    <div className={styles.inputField}>
                        <Controller
                            control={form.control}
                            name="name"
                            rules={{
                                required: { value: true, message: 'Provider name is required.' },
                                minLength: { value: 3, message: 'Provider names must be at least 3 characters long.' }
                            }}
                            render={({ field: { onBlur, value, onChange }, fieldState: { error } }) => (
                                <>
                                    <div className={styles.errorMessage}>{error?.message}</div>
                                    <label htmlFor="nameInput">Name</label>
                                    <input
                                        id="nameInput"
                                        type="text"
                                        onBlur={onBlur}
                                        value={value}
                                        onChange={(e) => {
                                            onChange(e);
                                            if (e.target.value?.length >= 3) {
                                                onBlur();
                                            }
                                        }}
                                    />
                                </>
                            )}
                        />
                    </div>
                    <div className={styles.inputField}>
                        <label htmlFor="nameInput">Active</label>
                        <Controller
                            control={form.control}
                            name="active"
                            render={({ field: { onBlur, value, onChange } }) => (
                                <input
                                    id="activeInput"
                                    type="checkbox"
                                    onBlur={onBlur}
                                    checked={value}
                                    onChange={onChange}
                                    disabled={provider === undefined}
                                />
                            )}
                        />
                    </div>
                </div>
            </div>
            <div className={styles.buttonBar}>
                {provider !== undefined && <button onClick={onCancel}>Cancel</button>}
                <button
                    disabled={!isValid}
                    onClick={() => {
                        if (isValid) onSave(form.getValues());
                    }}>
                    Save
                </button>
            </div>
        </div>
    );
};
