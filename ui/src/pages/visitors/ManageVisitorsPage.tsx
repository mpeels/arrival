import { Header } from 'src/components/header/Header';
import styles from './ManageVisitorsPage.module.scss';

export const ManageVisitorsPage = () => {
    return (
        <div className={styles.manageVisitorsPage}>
            <Header title="Manage Visitors" showHome />
        </div>
    );
};
