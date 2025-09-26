import { PAGES } from '../../pages/AppRoutes';
import { useNavigate } from 'react-router';
import styles from './LandingPage.module.scss';
import { Header } from 'src/components/header/Header';

export const LandingPage = () => {
    const nav = useNavigate();

    return (
        <div className={styles.landingPage}>
            <Header title="Arrival" />
            <div className={styles.buttons}>
                <button onClick={() => nav(PAGES.QUEUE)}>View Queue</button>
                <button onClick={() => nav(PAGES.VISITORS)}>Manage Visitors</button>
                <button onClick={() => nav(PAGES.PROVIDERS)}>Manage providers</button>
            </div>
        </div>
    );
};
