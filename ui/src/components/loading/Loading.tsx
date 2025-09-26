import { type ReactNode } from 'react';

type Props = {
    loading: boolean;
    children: ReactNode | ReactNode[];
};
export const Loading = ({ loading, children }: Props) => {
    return <>{loading ? <div>Loading...</div> : <>{children} </>}</>;
};
